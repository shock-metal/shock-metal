package vnemesis.shockmetal.item.shield;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import vnemesis.shockmetal.Config;
import vnemesis.shockmetal.client.sounds.ShieldCooldownLoopSound;
import vnemesis.shockmetal.item.ItemEnergyBase;
import vnemesis.shockmetal.sounds.ShockMetalSoundsRegistry;
import vnemesis.shockmetal.util.InventoryUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static vnemesis.shockmetal.util.EnergyUtilities.hasEnoughEnergy;
import static vnemesis.shockmetal.util.EnergyUtilities.useEnergy;

public class ItemShieldModule extends ItemEnergyBase {

    public static final int BASE_DAMAGE_THRESHOLD = 20;
    public static final int BASE_SHIELD_RECHARGE_DELAY = 8;
    public static final int MAX_DAMAGE_VALUE = 500;
    public static final int ENERGY_HEART_COST = 1000;

    // Keys for CUSTOM_DATA storage
    private static final String KEY_TOTAL_DAMAGE     = "shockmetal.total_damage_taken";
    private static final String KEY_LAST_DAMAGE_TIME = "shockmetal.last_damage_time";
    private static final String KEY_PLAYED_RECHARGE  = "shockmetal.played_recharge";
    private static final String KEY_ACTIVE           = "active";

    @OnlyIn(Dist.CLIENT)
    private ShieldCooldownLoopSound cooldownLoopSound;

    public ItemShieldModule() {
        super(new Item.Properties()
                .stacksTo(1)
                .fireResistant()
        );
    }

    // -------------------------------------------------------------------------
    // CUSTOM_DATA helpers (replaces getOrCreateTag() in MC 1.21.x)
    // -------------------------------------------------------------------------

    private static CompoundTag getShieldTag(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
    }

    private static void saveShieldTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    // -------------------------------------------------------------------------
    // ItemEnergyBase overrides
    // -------------------------------------------------------------------------

    @Override
    public int getEnergyMax() {
        return Config.SHIELD_MAX_ENERGY.get();
    }

    @Override
    public int getEnergyCost() {
        return ENERGY_HEART_COST;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isActive(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        CompoundTag tag = new CompoundTag();
        tag.putInt(KEY_TOTAL_DAMAGE, 0);
        tag.putLong(KEY_LAST_DAMAGE_TIME, 0L);
        tag.putBoolean(KEY_ACTIVE, false);
        saveShieldTag(stack, tag);
        super.onCraftedBy(stack, level, player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("shockmetal.tooltip.shield.active", isActive(stack))
                .withStyle(ChatFormatting.GREEN));
        if (isActive(stack)) {
            tooltip.add(Component.translatable("shockmetal.tooltip.shield.buffer",
                    getDamageThreshold() - getTotalDamageTaken(stack), getDamageThreshold())
                    .withStyle(ChatFormatting.AQUA));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        var activeShields = ItemShieldModule.getShieldsInInventory(player, true);

        if (player.isShiftKeyDown()
                && !player.getCooldowns().isOnCooldown(stack.getItem())
                && getTotalDamageTaken(stack) == 0) {

            if (!isActive(stack) && activeShields.isEmpty()) {
                setActive(stack, true);
                if (world.isClientSide()) {
                    player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            } else if (isActive(stack)) {
                setActive(stack, false);
            }

            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slotId, boolean isSelected) {
        int totalDamageTaken = getTotalDamageTaken(stack);

        if (isActive(stack) && entity instanceof Player player) {
            var activeShields = ItemShieldModule.getShieldsInInventory(player, true);

            // Enforce single active shield
            if (activeShields.size() > 1) {
                ItemStack lowestEnergy = Collections.min(activeShields,
                        Comparator.comparingInt(ItemEnergyBase::getEnergyStored));
                setActive(lowestEnergy, false);
                player.sendSystemMessage(Component.translatable("shockmetal.msg.one_shield_active"));
            }

            if (totalDamageTaken > 0) {
                long currentTime = world.getGameTime();
                if (currentTime - getLastDamageTime(stack) > getRechargeDelay() * 20L) {
                    // Play sound on the very FIRST tick recovery starts (flag re-armed by resetLastDamageTimeToCurrent)
                    if (!getPlayedRecharge(stack)) {
                        if (!world.isClientSide() && player instanceof ServerPlayer sp) {
                            sp.playNotifySound(ShockMetalSoundsRegistry.SHIELD_RECHARGE.get(),
                                    SoundSource.PLAYERS, 1f, 1f);
                        }
                        setPlayedRecharge(true, stack);
                    }
                    totalDamageTaken = Math.max(0, totalDamageTaken - 1);
                    setTotalDamageTaken(totalDamageTaken, stack);
                }

                // Client: keep the cooldown-tone loop alive while overloaded
                if (world.isClientSide() && totalDamageTaken >= getDamageThreshold()) {
                    playCooldownLoopSound(player);
                }
            }
        }

        super.inventoryTick(stack, world, entity, slotId, isSelected);
    }

    // -------------------------------------------------------------------------
    // Activation / State
    // -------------------------------------------------------------------------

    public boolean isActive(ItemStack stack) {
        return getShieldTag(stack).getBoolean(KEY_ACTIVE);
    }

    public void setActive(ItemStack stack, boolean active) {
        CompoundTag tag = getShieldTag(stack);
        tag.putBoolean(KEY_ACTIVE, active);
        saveShieldTag(stack, tag);
    }

    public boolean isShieldDepleted(ItemStack stack) {
        return getEnergyStored(stack) == 0;
    }

    // -------------------------------------------------------------------------
    // Damage handling
    // -------------------------------------------------------------------------

    public void doShieldDamageUpdate(ItemStack stack, int amount, Player player) {
        if (!hasEnoughEnergy(stack, amount)) {
            player.sendSystemMessage(Component.translatable("shockmetal.msg.no_power"));
            setActive(stack, false);
            setEnergy(stack, 0);
            return;
        }
        useEnergy(stack, amount);
        resetLastDamageTimeToCurrent(player.level(), stack);
        setTotalDamageTaken(Math.min(getTotalDamageTaken(stack) + amount, getDamageThreshold()), stack);

        // Shield-absorb feedback (server-side → only owning player hears it)
        if (player instanceof ServerPlayer sp) {
            sp.playNotifySound(SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS,
                    0.8f, 0.9f + player.getRandom().nextFloat() * 0.2f);
        }

        if (getTotalDamageTaken(stack) >= getDamageThreshold()) {
            applyCooldown(player);
            // Overload / break sound
            if (player instanceof ServerPlayer sp) {
                sp.playNotifySound(ShockMetalSoundsRegistry.SHIELD_BREAK.get(),
                        SoundSource.PLAYERS, 1f, 1f);
            }
            player.sendSystemMessage(Component.translatable("shockmetal.msg.shield_overload", getRechargeDelay()));
        }
    }

    public void applyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, getRechargeDelay() * 20);
    }

    /**
     * Calculates and returns the ratio of damage absorbed (0–1).
     * Modifies the shield state on the server.
     */
    public static float getDamageAbsorbed(Player player, DamageSource source, float amount) {
        if (amount <= 0) return 0;

        ItemStack shieldStack = InventoryUtilities.getActiveShieldInInventory(player);
        if (shieldStack.isEmpty() || !(shieldStack.getItem() instanceof ItemShieldModule shieldItem)) {
            return 0;
        }

        if (player.getCooldowns().isOnCooldown(shieldItem)) {
            shieldItem.applyCooldown(player);
            shieldItem.resetLastDamageTimeToCurrent(player.level(), shieldStack);
            return 0;
        }

        int effectiveDamage = Math.round(amount);
        int shieldBuffer = shieldItem.getProtectableDamage(shieldStack);
        if (shieldBuffer <= 0) return 0;

        int shieldDamage = Math.min(shieldBuffer, effectiveDamage);
        float ratioAbsorbed = Math.min(1.0f, (float) shieldDamage / effectiveDamage);
        shieldItem.doShieldDamageUpdate(shieldStack, shieldDamage, player);
        return ratioAbsorbed;
    }

    // -------------------------------------------------------------------------
    // State accessors
    // -------------------------------------------------------------------------

    public int getTotalDamageTaken(ItemStack stack) {
        return getShieldTag(stack).getInt(KEY_TOTAL_DAMAGE);
    }

    public void setTotalDamageTaken(int value, ItemStack stack) {
        CompoundTag tag = getShieldTag(stack);
        tag.putInt(KEY_TOTAL_DAMAGE, value);
        saveShieldTag(stack, tag);
    }

    public long getLastDamageTime(ItemStack stack) {
        return getShieldTag(stack).getLong(KEY_LAST_DAMAGE_TIME);
    }

    public void resetLastDamageTimeToCurrent(Level world, ItemStack stack) {
        CompoundTag tag = getShieldTag(stack);
        tag.putLong(KEY_LAST_DAMAGE_TIME, world.getGameTime());
        tag.putBoolean(KEY_PLAYED_RECHARGE, false); // re-arm: next recovery cycle plays the sound
        saveShieldTag(stack, tag);
    }

    public boolean getPlayedRecharge(ItemStack stack) {
        return getShieldTag(stack).getBoolean(KEY_PLAYED_RECHARGE);
    }

    public void setPlayedRecharge(boolean value, ItemStack stack) {
        CompoundTag tag = getShieldTag(stack);
        tag.putBoolean(KEY_PLAYED_RECHARGE, value);
        saveShieldTag(stack, tag);
    }

    public int getProtectableDamage(ItemStack stack) {
        return getDamageThreshold() - getTotalDamageTaken(stack);
    }

    public float getTotalProtectableDamage(ItemStack stack) {
        return (float) getEnergyStored(stack) / getEnergyCost();
    }

    // -------------------------------------------------------------------------
    // Static helpers / constants
    // -------------------------------------------------------------------------

    public static int getDamageThreshold() {
        return Config.BASE_DAMAGE_THRESHOLD.get();
    }

    public static int getRechargeDelay() {
        return Config.BASE_SHIELD_RECHARGE_DELAY.get();
    }

    public static float getTotalMaxEnergy() {
        return (float) (MAX_DAMAGE_VALUE * ENERGY_HEART_COST);
    }

    public static ArrayList<ItemStack> getShieldsInInventory(Player player, boolean onlyActive) {
        Predicate<ItemStack> pred = stack -> {
            if (stack.getItem() instanceof ItemShieldModule shieldModule) {
                return !onlyActive || shieldModule.isActive(stack);
            }
            return false;
        };

        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        // Search main inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (pred.test(slot)) {
                itemStacks.add(slot);
            }
        }

        // Search Curios slots (optional dependency)
        if (ModList.get().isLoaded("curios")) {
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                var curios = handler.getEquippedCurios();
                for (int i = 0; i < curios.getSlots(); i++) {
                    ItemStack slot = curios.getStackInSlot(i);
                    if (pred.test(slot)) {
                        itemStacks.add(slot);
                    }
                }
            });
        }

        return itemStacks;
    }

    // -------------------------------------------------------------------------
    // Client-side sounds
    // -------------------------------------------------------------------------

    /** Starts (or restarts) the looping cooldown-tone sound on the client while the shield is overloaded. */
    @OnlyIn(Dist.CLIENT)
    private void playCooldownLoopSound(Player player) {
        Player localPlayer = Minecraft.getInstance().player;
        if (localPlayer != null && localPlayer.equals(player)) {
            // Use isActive() so the sound restarts after each overload cycle
            if (cooldownLoopSound == null
                    || !Minecraft.getInstance().getSoundManager().isActive(cooldownLoopSound)) {
                cooldownLoopSound = new ShieldCooldownLoopSound(player, 1f, player.level().random);
                Minecraft.getInstance().getSoundManager().play(cooldownLoopSound);
            }
        }
    }
}

