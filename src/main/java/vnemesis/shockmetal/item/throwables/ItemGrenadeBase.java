package vnemesis.shockmetal.item.throwables;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import vnemesis.shockmetal.entity.projectile.AbstractGrenadeEntity;
import vnemesis.shockmetal.item.ItemBase;
import vnemesis.shockmetal.util.Helpers;

import java.util.List;
import java.util.Random;

public class ItemGrenadeBase extends ItemBase
{
    protected final IGrenadeFactory<? extends AbstractGrenadeEntity> factory;
    protected int radius = 4;
    protected int cooldown = 20;
    private final String infoKey;
    private static final Random RND = new Random();

    public ItemGrenadeBase(IGrenadeFactory<? extends AbstractGrenadeEntity> factory,
                           Properties props, String infoTranslationKey) {
        super(props);
        this.factory = factory;
        this.infoKey = infoTranslationKey;
        DispenserBlock.registerBehavior(this, new DefaultDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Direction facing = source.state().getValue(DispenserBlock.FACING);
                double x = source.pos().getX() + 0.5 + facing.getStepX();
                double y = source.pos().getY() + 0.5 + facing.getStepY();
                double z = source.pos().getZ() + 0.5 + facing.getStepZ();
                AbstractGrenadeEntity grenade = factory.createGrenade(source.level(), x, y, z);
                ItemStack throwStack = Helpers.cloneStack(stack, 1);
                throwStack.setDamageValue(1);
                grenade.setItem(throwStack);
                grenade.setRadius(1 + radius);
                grenade.shoot(facing.getStepX(), facing.getStepY() + 0.1, facing.getStepZ(), 1.1f, 3.0f);
                source.level().addFreshEntity(grenade);
                stack.shrink(1);
                return stack;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        if (infoKey != null) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Component.translatable(infoKey).withStyle(ChatFormatting.WHITE));
            } else {
                tooltip.add(Component.translatable("info.shockmetal.gui.shift-info")
                    .withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        world.playSound(null, player.getX(), player.getY(), player.getZ(),
            SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL,
            0.5f, 0.4f / (RND.nextFloat() * 0.4f + 0.8f));
        player.getCooldowns().addCooldown(this, cooldown);
        if (!world.isClientSide()) {
            createGrenade(stack, world, player);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.success(stack);
    }

    protected void createGrenade(ItemStack stack, Level level, Player player) {
        AbstractGrenadeEntity grenade = factory.createGrenade(level, player);
        ItemStack throwStack = Helpers.cloneStack(stack, 1);
        throwStack.setDamageValue(1);
        grenade.setItem(throwStack);
        grenade.setRadius(1 + radius);
        grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5f, 0.5f);
        level.addFreshEntity(grenade);
    }

    public interface IGrenadeFactory<T extends AbstractGrenadeEntity> {
        T createGrenade(Level level, LivingEntity thrower);
        T createGrenade(Level level, double x, double y, double z);
    }
}




