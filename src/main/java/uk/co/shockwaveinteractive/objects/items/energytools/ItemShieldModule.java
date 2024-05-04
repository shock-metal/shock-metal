package uk.co.shockwaveinteractive.objects.items.energytools;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.common.sounds.ShieldCooldownLoopSound;
import uk.co.shockwaveinteractive.init.ModSounds;
import uk.co.shockwaveinteractive.objects.items.ItemEnergyBase;

import javax.annotation.Nullable;
import java.util.List;

import static uk.co.shockwaveinteractive.util.EnergyUtilities.hasEnoughEnergy;
import static uk.co.shockwaveinteractive.util.EnergyUtilities.useEnergy;
import static uk.co.shockwaveinteractive.util.Utilities.getClientPlayer;

//TODO NBT damage?
public class ItemShieldModule extends ItemEnergyBase {
    public static final int DAMAGE_THRESHOLD = 20; // Maximum damage threshold within the cooldown window
    public static final int SHIELD_RECHARGE_DELAY = 8; // Recharge Delay in seconds
    public static final int MAX_DAMAGE_VALUE = 500; // This * 1000FE = Max Energy storage
    public static final int ENERGY_HEART_COST = 1000; // This * 1000FE = Max Energy storage

    private long lastDamageTime = 0;
    private long lastCooldownSoundLoopTime = 0;
    private int totalDamageTaken = 0;
    private boolean playedRecharge = false;
    private ShieldCooldownLoopSound cooldownLoopSound;
    public ItemShieldModule() {
        super(new Item.Properties()
                .tab(ShockMetalMain.SHOCKMETALTAB)
                .durability(500)
                .fireResistant()
        );
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
        if (player.isShiftKeyDown()) {
            switchActive(stack);
            if(world.isClientSide) {
                player.level.playSeededSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f, 0);
            }
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int p_41407_, boolean p_41408_) {
        if (totalDamageTaken > 0) {
            long currentTime = world.getGameTime();
            if(currentTime - lastDamageTime > SHIELD_RECHARGE_DELAY * 20) {
                int newDamageTaken = totalDamageTaken - 1;
                totalDamageTaken = Math.max(0, newDamageTaken);
                if(entity instanceof Player player && !playedRecharge) {
                    if(world.isClientSide) {
                        this.PlayRechargeSound(player);
                    }
                    playedRecharge = true;
                }
            }

            if(world.isClientSide() && entity instanceof Player player
                    && totalDamageTaken >= DAMAGE_THRESHOLD
                    && currentTime - lastCooldownSoundLoopTime > 8 * 20) {
                    this.PlayCooldownLoopSound(player);
            }
        }

        if(totalDamageTaken == 0 && playedRecharge) {
            playedRecharge = false;
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
        stack.getOrCreateTag().putBoolean("active", active);
    }

    public boolean isShieldDepleted(ItemStack stack) {
        return getEnergyStored(stack) == 0;
    }

    public void hurtActiveShield(ItemStack stack, int amount, Player player) {
        if (!hasEnoughEnergy(stack, amount)) {
            player.displayClientMessage(Component.translatable("shockmetal.msg.no_power"), true);
            setActive(stack, false);
            setEnergy(stack, 0);
            return;
        }
        useEnergy(stack, amount);
        trackDamage(amount, player.level);
        applyCooldownIfNeeded(player);
    }

    private void trackDamage(int amount, Level world) {
        resetLastDamageTimeToCurrent(world);
        totalDamageTaken = Math.min(totalDamageTaken + amount, DAMAGE_THRESHOLD);
    }

    private void applyCooldownIfNeeded(Player player) {
        if (totalDamageTaken >= DAMAGE_THRESHOLD) {
            var clientPlayer = getClientPlayer();
            if(clientPlayer.level.isClientSide()) {
                PlayBreakSound(player);
                player.displayClientMessage(Component.translatable("shockmetal.msg.shield_overload", SHIELD_RECHARGE_DELAY), false);
            }
            ApplyCooldown(player);
        }
    }

    public void ApplyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, SHIELD_RECHARGE_DELAY * 20);
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayCooldownLoopSound(Player player) {
        Player myPlayer = Minecraft.getInstance().player;
        if(myPlayer.equals(player)) {
            if(cooldownLoopSound == null) {
                System.out.println("Play Cooldown");
                cooldownLoopSound = new ShieldCooldownLoopSound(player, 1f, player.level.random);
                Minecraft.getInstance().getSoundManager().play(cooldownLoopSound);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayBreakSound(Player player) {
        System.out.println("Play Break");
        Minecraft.getInstance().getSoundManager().stop(ModSounds.SHIELD_RECHARGE.get().getLocation(), SoundSource.PLAYERS);
        player.playSound(ModSounds.SHIELD_BREAK.get(), 1.0f, 1.0f);
    }

    @OnlyIn(Dist.CLIENT)
    private void PlayRechargeSound(Player player) {
        System.out.println("Play Recharge");
        if (cooldownLoopSound != null) {
            if (!cooldownLoopSound.isStopped()) {
                player.playSound(ModSounds.SHIELD_RECHARGE.get(), 1f, 1f);
            }
            cooldownLoopSound = null;
        }
    }

    public int getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public long getLastDamageTime() {
        return lastDamageTime;
    }

    public void resetLastDamageTimeToCurrent(Level world) {
        this.lastDamageTime = world.getGameTime();
    }

    public int getDamageCost(int amount)
    {
        return ENERGY_HEART_COST * amount;
    }

    public int getProtectableDamage(ItemStack stack)
    {
        return getEnergyStored(stack) / getEnergyCost();
    }
}
