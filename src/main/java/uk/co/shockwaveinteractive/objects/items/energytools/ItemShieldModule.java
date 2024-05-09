package uk.co.shockwaveinteractive.objects.items.energytools;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.common.sounds.GenericSoundInstance;
import uk.co.shockwaveinteractive.common.sounds.ShieldCooldownLoopSound;
import uk.co.shockwaveinteractive.init.ModSounds;
import uk.co.shockwaveinteractive.integration.curios.CuriosProxy;
import uk.co.shockwaveinteractive.objects.items.ItemEnergyBase;
import uk.co.shockwaveinteractive.util.InventoryUtilities;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static uk.co.shockwaveinteractive.util.EnergyUtilities.hasEnoughEnergy;
import static uk.co.shockwaveinteractive.util.EnergyUtilities.useEnergy;

//TODO NBT damage?
public class ItemShieldModule extends ItemEnergyBase {
    public static final int BASE_DAMAGE_THRESHOLD = 20; // Maximum damage threshold within the cooldown window
    public static final int BASE_SHIELD_RECHARGE_DELAY = 8; // Recharge Delay in seconds
    public static final int MAX_DAMAGE_VALUE = 500; // This * 1000FE = Max Energy storage
    public static final int ENERGY_HEART_COST = 1000; // This * 1000FE = Max Energy storage

    private ShieldCooldownLoopSound cooldownLoopSound;
    public ItemShieldModule() {
        super(new Item.Properties()
                .tab(ShockMetalMain.SHOCKMETALTAB)
                .durability(500)
                .fireResistant()
        );
    }

    public void setupNBTTags(ItemStack stack) {
        CompoundTag nbtData = new CompoundTag();
        nbtData.putInt("shockmetal.total_damage_taken", 0);
        nbtData.putInt("shockmetal.last_damage_time", 0);
        nbtData.putBoolean("shockmetal.played_recharge", false);
        stack.setTag(nbtData);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level p_41448_, Player p_41449_) {
        setupNBTTags(stack);
        super.onCraftedBy(stack, p_41448_, p_41449_);
    }

    @Override
    public int getEnergyMax() {
        return MAX_DAMAGE_VALUE * ENERGY_HEART_COST;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("shockmetal.tooltip.energy.active", isActive(stack)).withStyle(ChatFormatting.GREEN));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && !player.getCooldowns().isOnCooldown(stack.getItem())) {
            switchActive(stack);
            if(world.isClientSide) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            }
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int p_41407_, boolean p_41408_) {
        int totalDamageTaken = getTotalDamageTaken(stack);
        if (isActive(stack) && entity instanceof Player player && totalDamageTaken > 0) {
            long currentTime = world.getGameTime();
            if(currentTime - getLastDamageTime(stack) > getRechargeDelay(player) * 20) {
                int newDamageTaken = totalDamageTaken - 1;
                totalDamageTaken = Math.max(0, newDamageTaken);
                setTotalDamageTaken(totalDamageTaken, stack);
                if(world.isClientSide && !getPlayedRecharge(stack)) {
                    this.PlayRechargeSound(player);
                    setPlayedRecharge(true, stack);
                }
            }

            if(world.isClientSide() && totalDamageTaken >= getDamageThreshold(player)) {
                    this.PlayCooldownLoopSound(player);
            }
        }

        if(totalDamageTaken == 0 && getPlayedRecharge(stack)) {
            setPlayedRecharge(false, stack);
        }

        super.inventoryTick(stack, world, entity, p_41407_, p_41408_);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    private void switchActive(ItemStack stack) {
        if(!isShieldDepleted(stack)) {
            setActive(stack, !isActive(stack));
        }
    }

    public boolean isActive(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("active");
    }

    private void setActive(ItemStack stack, boolean active) {
        CompoundTag nbtTag = stack.getOrCreateTag();
        nbtTag.putBoolean("active", active);
        stack.setTag(nbtTag);
    }

    public boolean isShieldDepleted(ItemStack stack) {
        return getEnergyStored(stack) == 0;
    }

    public void doShieldDamageUpdate(ItemStack stack, int amount, Player player) {
        if (!hasEnoughEnergy(stack, amount)) {
            player.sendSystemMessage(Component.translatable("shockmetal.msg.no_power", isActive(stack)));
            setActive(stack, false);
            setEnergy(stack, 0);
            return;
        }
        useEnergy(stack, amount);
        addShieldDamage(amount, player.level, stack, player);
        applyCooldownIfNeeded(player, stack);
    }

    private void addShieldDamage(int amount, Level world, ItemStack stack, Player player) {
        resetLastDamageTimeToCurrent(world, stack);
        setTotalDamageTaken(Math.min(getTotalDamageTaken(stack) + amount, getDamageThreshold(player)), stack);
    }

    private void applyCooldownIfNeeded(Player player, ItemStack stack) {
        if (getTotalDamageTaken(stack) >= getDamageThreshold(player)) {
            ApplyCooldown(player);
            player.sendSystemMessage(Component.translatable("shockmetal.msg.shield_overload", getRechargeDelay(player)));
        }
    }

    public void ApplyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, getRechargeDelay(player) * 20);
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayCooldownLoopSound(Player player) {
        Player myPlayer = Minecraft.getInstance().player;
        if(myPlayer.equals(player) && cooldownLoopSound == null) {
            this.PlayBreakSound(player);
            cooldownLoopSound = new ShieldCooldownLoopSound(player, 1f, player.level.random);
            Minecraft.getInstance().getSoundManager().play(cooldownLoopSound);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayBreakSound(Player player) {
        Player myPlayer = Minecraft.getInstance().player;
        if (myPlayer.equals(player)) {
            Minecraft.getInstance().getSoundManager().stop(ModSounds.SHIELD_RECHARGE.get().getLocation(), SoundSource.PLAYERS);
            Minecraft.getInstance().getSoundManager().play(new GenericSoundInstance(player, player.level.random, ModSounds.SHIELD_BREAK.get()));
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayRechargeSound(Player player) {
        Player myPlayer = Minecraft.getInstance().player;
        if (myPlayer.equals(player) && cooldownLoopSound != null) {
            Minecraft.getInstance().getSoundManager().play(new GenericSoundInstance(player, player.level.random, ModSounds.SHIELD_RECHARGE.get()));
            cooldownLoopSound = null;
        }
    }

    public int getTotalDamageTaken(ItemStack stack) {
        return stack.getOrCreateTag().getInt("shockmetal.total_damage_taken");

    }

    public void setTotalDamageTaken(int value, ItemStack stack) {
        CompoundTag nbtTag = stack.getOrCreateTag();
        nbtTag.putInt("shockmetal.total_damage_taken", value);
        stack.setTag(nbtTag);
    }

    public long getLastDamageTime(ItemStack stack) {
        return stack.getOrCreateTag().getLong("shockmetal.last_damage_time");
    }

    public void resetLastDamageTimeToCurrent(Level world, ItemStack stack) {
        CompoundTag nbtTag = stack.getOrCreateTag();
        nbtTag.putLong("shockmetal.last_damage_time", world.getGameTime());
        stack.setTag(nbtTag);
    }

    public boolean getPlayedRecharge(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("shockmetal.played_recharge");
    }

    public void setPlayedRecharge(boolean value, ItemStack stack) {
        CompoundTag nbtTag = stack.getOrCreateTag();
        nbtTag.putBoolean("shockmetal.played_recharge", value);
        stack.setTag(nbtTag);
    }

    public int getDamageCost(int amount)
    {
        return ENERGY_HEART_COST * amount;
    }

    public int getProtectableDamage(ItemStack stack, Player player)
    {
        return  getDamageThreshold(player) - getTotalDamageTaken(stack);
    }

    public float getTotalProtectableDamage(ItemStack stack)
    {
        return (float) getEnergyStored(stack) / getEnergyCost();
    }

    public static float getDamageAbsorbed(Player player, DamageSource source, float amount) {
        return getDamageAbsorbed(player, source, amount, null);
    }

    public static float getDamageAbsorbed(Player player, DamageSource source, float amount, @Nullable List<Runnable> energyUseCallback) {
        if (amount <= 0) {
            return 0;
        }

        var shieldItemStack = InventoryUtilities.getActiveShieldInInventory(player);
        float ratioAbsorbed = 0;

        if(!shieldItemStack.isEmpty() && shieldItemStack.getItem() instanceof ItemShieldModule shieldItem)
        {
            if(player.getCooldowns().isOnCooldown(shieldItem)) {
                shieldItem.ApplyCooldown(player);
                shieldItem.resetLastDamageTimeToCurrent(player.level, shieldItemStack);
            } else {
                int effectiveDamage = Math.round(amount); // 3

                // how much of the buffer is left
                int shieldDurability = shieldItem.getProtectableDamage(shieldItemStack, player); // 2

                // Calculate how much damage the shield can absorb
                int shieldDamage = Math.min(shieldDurability, effectiveDamage);
                ratioAbsorbed =  (float) (shieldDurability / effectiveDamage);
                shieldItem.doShieldDamageUpdate(shieldItemStack, Math.round(shieldDamage), player);

            }
        }

        return Math.min(ratioAbsorbed, 1);
    }

    public static ArrayList<ItemStack> getShieldsInInventory(Player player, boolean onlyActive) {

        Predicate<ItemStack> itemPredicate = stack -> {
            if (stack.getItem() instanceof ItemShieldModule shieldModule) {
                return !onlyActive || shieldModule.isActive(stack);
            }
            return false;
        };

        // Search for the active shield in the player's inventory
        ArrayList<ItemStack> itemStacks = new ArrayList<>();

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stackInSlot = player.getInventory().getItem(i);
            if (itemPredicate.test(stackInSlot)) {
                itemStacks.add(stackInSlot);
            }
        }

        LazyOptional<IItemHandlerModifiable> wornItems = CuriosProxy.getAllWorn(player);
        if (wornItems.isPresent()) {
            IItemHandlerModifiable curiosHandler = wornItems.orElse(null);
            if (curiosHandler != null) {
                for (int i = 0; i < curiosHandler.getSlots(); i++) {
                    ItemStack equip = curiosHandler.getStackInSlot(i);
                    if (equip.getItem() instanceof ItemShieldModule shieldModule) {
                        if(!onlyActive || shieldModule.isActive(equip)) {
                            itemStacks.add(equip);
                            break;
                        }
                    }
                }
            }
        }

        return itemStacks;
    }

    public static int getDamageThreshold(ArrayList<ItemStack> shields) {
        return shields.size() * BASE_DAMAGE_THRESHOLD;
    }

    public static int getDamageThreshold(Player player) {
        return getDamageThreshold(getShieldsInInventory(player, true));
    }

    public static float getTotalEnergyRemaining(ArrayList<ItemStack> shields) {
        float energy = 0f;
        for (var element : shields) {
            energy += ItemShieldModule.getEnergyStored(element);
        }

        return energy;
    }

    public static float getTotalEnergyRemaining(Player player) {
        return getTotalEnergyRemaining(getShieldsInInventory(player, true));
    }

    public static float getTotalMaxEnergy(ArrayList<ItemStack> shields) {
        return shields.size() * (MAX_DAMAGE_VALUE * ENERGY_HEART_COST);
    }

    public static float getTotalMaxEnergy(Player player) {
        return getTotalMaxEnergy(getShieldsInInventory(player, false));
    }

    public static int getRechargeDelay(ArrayList<ItemStack> shields) {
        return shields.size() * BASE_SHIELD_RECHARGE_DELAY;
    }

    public static int getRechargeDelay(Player player) {
        return getRechargeDelay(getShieldsInInventory(player, true));
    }
}
