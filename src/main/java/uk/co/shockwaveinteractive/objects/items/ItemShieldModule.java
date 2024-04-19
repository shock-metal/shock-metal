package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Sounds;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

import javax.annotation.Nullable;
import java.util.List;

public class ItemShieldModule extends ItemTieredBase {
    public static final int DAMAGE_THRESHOLD = 20; // Maximum damage threshold within the cooldown window
    public static final int SHIELD_RECHARGE_DELAY = 8; // Recharge Delay in seconds

    private long lastDamageTime = 0;
    private long lastCooldownSoundLoopTime = 0;
    private int totalDamageTaken = 0;
    private boolean playedRecharge = false;
    public ItemShieldModule() {
        super(ShockmetalItemTier.SHOCKMETAL,
                new Item.Properties()
                .tab(ShockMetalMain.SHOCKMETALTAB)
                .durability(500)
                .fireResistant()
        );
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isActive(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int damage = stack.getMaxDamage() - stack.getDamageValue();
        tooltip.add(
                Component.literal(String.format("%s/%s", damage, stack.getMaxDamage()))
                        .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown()) {
            switchActive(stack, player);
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
                    this.PlayRechargeSound(player);
                    playedRecharge = true;
                }
            }

            if(entity instanceof Player player
                    && totalDamageTaken >= DAMAGE_THRESHOLD
                    && currentTime - lastCooldownSoundLoopTime > 8 * 20) {
                System.out.println("Loop Sound");
                this.PlayCooldownSound(player);
            }
        }

        if(totalDamageTaken == 0 && playedRecharge) {
            playedRecharge = false;
        }

        super.inventoryTick(stack, world, entity, p_41407_, p_41408_);
    }

    private void switchActive(ItemStack stack, Player player) {
        if(!isShieldDepleted(stack)) {
            setActive(stack, !isActive(stack));
            player.level.playSeededSound(null, player, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0f, 1.0f, 0);
        }
    }

    public boolean isActive(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("active");
    }

    private void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("active", active);
    }

    public boolean isShieldDepleted(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage();
    }

    public void hurtActiveShield(ItemStack stack, int amount, LivingEntity entity) {
        int shieldDamage = Math.min(stack.getMaxDamage() - stack.getDamageValue(), amount);
        stack.setDamageValue(stack.getDamageValue() + shieldDamage);

        // Check if shield durability is depleted
        if (isShieldDepleted(stack)) {
            setActive(stack, false);
        }

        trackDamage(amount, entity.level);
        if(entity instanceof Player player) {
            applyCooldownIfNeeded(player);
        }
    }

    private void trackDamage(int amount, Level world) {
        resetLastDamageTimeToCurrent(world);
        totalDamageTaken = Math.min(totalDamageTaken + amount, DAMAGE_THRESHOLD);
    }

    private void applyCooldownIfNeeded(Player player) {
        if (totalDamageTaken >= DAMAGE_THRESHOLD) {
            PlayBreakSound(player);
            ApplyCooldown(player);
            player.displayClientMessage(Component.literal(String.format("Shield Overload! Reset in %s Seconds", SHIELD_RECHARGE_DELAY)), false);
        }
    }

    public void ApplyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, SHIELD_RECHARGE_DELAY * 20);
        this.PlayCooldownSound(player);
    }

    private void PlayCooldownSound(Player player) {
        Minecraft.getInstance().getSoundManager().stop(Sounds.SHIELD_COOLDOWN_TONE.get().getLocation(), SoundSource.PLAYERS);
        Level world = player.level;
        world.playSeededSound(null, player, Sounds.SHIELD_COOLDOWN_TONE.get(), SoundSource.PLAYERS, 1.0f, 1.0f, 0);
        lastCooldownSoundLoopTime = world.getGameTime();
    }

    private void PlayBreakSound(Player player) {
        Minecraft.getInstance().getSoundManager().stop(Sounds.SHIELD_RECHARGE.get().getLocation(), SoundSource.PLAYERS);
        Level world = player.level;
        world.playSeededSound(null, player, Sounds.SHIELD_BREAK.get(), SoundSource.PLAYERS, 1.0f, 1.0f, 0);
    }

    private void PlayRechargeSound(Player player) {
        Minecraft.getInstance().getSoundManager().stop(Sounds.SHIELD_COOLDOWN_TONE.get().getLocation(), SoundSource.PLAYERS);
        Level world = player.level;
        world.playSeededSound(null, player, Sounds.SHIELD_RECHARGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f, 0);
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
}
