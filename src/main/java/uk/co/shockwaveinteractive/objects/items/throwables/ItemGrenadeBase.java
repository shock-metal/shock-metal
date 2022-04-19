package uk.co.shockwaveinteractive.objects.items.throwables;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.entity.projectile.AbstractGrenadeEntity;
import uk.co.shockwaveinteractive.objects.items.ItemBase;
import uk.co.shockwaveinteractive.util.Helpers;

import javax.annotation.Nullable;
import java.util.List;

import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_SHIFT_INFO;

/*
* Base Implementation by CoFH
* */

public class ItemGrenadeBase extends ItemBase {

    protected final IGrenadeFactory<? extends AbstractGrenadeEntity> factory;
    protected int radius = 4;
    protected int cooldown = 20;
    private String infoString = null;

    public ItemGrenadeBase(IGrenadeFactory<? extends AbstractGrenadeEntity> factory, Properties builder, @Nullable String infoString) {

        super(builder);
        this.factory = factory;
        this.infoString = infoString;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        if(infoString != null) {
            if(Screen.hasShiftDown())
            {
                tooltip.add(new TranslatableComponent(infoString).withStyle(ChatFormatting.WHITE));
            }
            else tooltip.add(new TranslatableComponent(TRANSLATION_SHIFT_INFO).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

        ItemStack stack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (ShockMetalMain.rnd.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, cooldown);
        if (!worldIn.isClientSide) {
            createGrenade(stack, worldIn, playerIn);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        if (!playerIn.getAbilities().instabuild) {
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
        grenade.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0.5F);
        level.addFreshEntity(grenade);
    }

    // region FACTORY
    public interface IGrenadeFactory<T extends AbstractGrenadeEntity> {

        T createGrenade(Level level, LivingEntity living);

        T createGrenade(Level level, double posX, double posY, double posZ);

    }
    // endregion

    // region DISPENSER BEHAVIOR
    private static final AbstractProjectileDispenseBehavior DISPENSER_BEHAVIOR = new AbstractProjectileDispenseBehavior() {

        @Override
        protected ThrowableItemProjectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {

            ItemGrenadeBase grenadeItem = ((ItemGrenadeBase) stackIn.getItem());
            AbstractGrenadeEntity grenade = grenadeItem.factory.createGrenade(worldIn, position.x(), position.y(), position.z());
            ItemStack throwStack = Helpers.cloneStack(stackIn, 1);
            throwStack.setDamageValue(1);
            grenade.setItem(throwStack);
            grenade.setRadius(1 + grenadeItem.radius);
            return grenade;
        }

        @Override
        protected float getUncertainty() {

            return 3.0F;
        }
    };
    // endregion
}
