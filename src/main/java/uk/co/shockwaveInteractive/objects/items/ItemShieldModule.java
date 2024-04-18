package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Sounds;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import javax.annotation.Nullable;
import java.util.List;

public class ItemShieldModule extends ItemTieredBase {
    private static final int DAMAGE_THRESHOLD = 20; // Maximum damage threshold within the cooldown window
    private static final int COOLDOWN_TICKS = 200;
    private static final int DAMAGE_TIME_FRAME = 15;

    private long lastDamageTime = 0;
    private int totalDamageTaken = 0;
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
            switchActive(stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(world, player, hand);
    }

    private void switchActive(ItemStack stack) {
        if(!isSheildDepleted(stack)) {
            setActive(stack, !isActive(stack));
        }
    }

    public boolean isActive(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("active");
    }

    private void setActive(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("active", active);
    }

    private boolean isSheildDepleted(ItemStack stack) {
        return stack.getDamageValue() >= stack.getMaxDamage();
    }

    public void hurtActiveShield(ItemStack stack, int amount, LivingEntity entity) {
        int shieldDamage = Math.min(stack.getMaxDamage() - stack.getDamageValue(), amount);
        stack.setDamageValue(stack.getDamageValue() + shieldDamage);

        // Check if shield durability is depleted
        if (isSheildDepleted(stack)) {
            setActive(stack, false);
        }

        trackDamage(amount);
        if(entity instanceof Player) {
            Player player = (Player) entity;
            applyCooldownIfNeeded(player);
            player.displayClientMessage(Component.literal(String.format("Shield Buffer %s/%s ", totalDamageTaken, DAMAGE_THRESHOLD)), false);
        }
    }

    private void trackDamage(int amount) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDamageTime > DAMAGE_TIME_FRAME * 1000) {
            lastDamageTime = currentTime;
            totalDamageTaken = amount;
        } else {
            totalDamageTaken += amount;
        }
    }

    private void applyCooldownIfNeeded(Player player) {
        if (totalDamageTaken > DAMAGE_THRESHOLD) {
            ApplyCooldown(player);
            player.displayClientMessage(Component.literal(String.format("Shield Overload! Reset in %s Seconds", COOLDOWN_TICKS / 20)), false);
            totalDamageTaken = 0;
        }
    }

    public void ApplyCooldown(Player player) {
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        PlayCooldownSound(player);
    }


    private void PlayCooldownSound(Player player) {
        Minecraft.getInstance().getSoundManager().stop(new ResourceLocation(MainReference.MODID, "shield_cooldown_applied"), SoundSource.PLAYERS);
        Level world = player.level;
        world.playSeededSound(null, player,
                Sounds.SHIELD_COOLDOWN_APPLIED.get(), SoundSource.PLAYERS, 1.0f, 1.0f, 0);
    }
}
