package vnemesis.shockmetal.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Random;

import static net.minecraft.ChatFormatting.DARK_PURPLE;

public class ShockmetalToolSword extends SwordItem
{
    private int charge = 0;
    private static final int MAX_CHARGE = 20;
    private static final Random RND = new Random();

    public ShockmetalToolSword() {
        super(
            ShockmetalItemTier.SHOCKMETAL,
            new Item.Properties()
                .fireResistant()
                .attributes(SwordItem.createAttributes(ShockmetalItemTier.SHOCKMETAL, 3, -2.4f))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal(
            String.format("Charge: %s/%s (%sx)", charge, MAX_CHARGE, getMultiplier()))
            .withStyle(DARK_PURPLE));

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("info.shockmetal.gui.shockmetal.sword").withStyle(ChatFormatting.WHITE));
        } else {
            tooltip.add(Component.translatable("info.shockmetal.gui.shift-info").withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 20% chance to set undead on fire and give attacker regeneration
        if (target.getType().is(EntityTypeTags.UNDEAD) && RND.nextInt(100) < 19) {
            target.igniteForSeconds(5);
            attacker.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
        }
        if (target.isAlive() && charge < MAX_CHARGE) {
            charge++;
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide() && player.isCrouching() && charge >= 5) {
            int baseRadius = 2;
            if (charge == 20) {
                charge = 0;
            } else if (charge >= 10) {
                charge -= 10;
            } else {
                charge -= 5;
            }
            int radius = baseRadius * getMultiplier();
            ServerLevel serverLevel = (ServerLevel) world;
            AABB aabb = player.getBoundingBox().inflate(radius, radius, radius);
            List<LivingEntity> list = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb);
            if (!list.isEmpty()) {
                list.forEach(entity -> {
                    if (entity.getType().is(EntityTypeTags.UNDEAD) && !entity.fireImmune()) {
                        entity.igniteForSeconds(5);
                    }
                    entity.hurt(world.damageSources().playerAttack(player), 6.0f * getMultiplier());
                });
            }
            serverLevel.sendParticles(ParticleTypes.EXPLOSION,
                player.getX(), player.getY(), player.getZ(), 1, 0, 0, 0, 0);
            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS,
                0.5f, (1.0f + (world.random.nextFloat() - world.random.nextFloat()) * 0.2f) * 0.7f);
        }
        return super.use(world, player, hand);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.isEnchanted() || charge == MAX_CHARGE;
    }

    private int getMultiplier() {
        if (charge == 20) return 3;
        if (charge >= 10)  return 2;
        return 1;
    }
}





