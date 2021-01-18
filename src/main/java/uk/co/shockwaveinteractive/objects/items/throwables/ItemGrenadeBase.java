package uk.co.shockwaveinteractive.objects.items.throwables;

import net.minecraft.block.DispenserBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(infoString != null) {
            if(Screen.hasShiftDown())
            {
                tooltip.add(new TranslationTextComponent(infoString).mergeStyle(TextFormatting.WHITE));
            }
            else tooltip.add(new TranslationTextComponent(TRANSLATION_SHIFT_INFO).mergeStyle(TextFormatting.GRAY));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, cooldown);
        if (!worldIn.isRemote) {
            createGrenade(stack, worldIn, playerIn);
        }
        playerIn.addStat(Stats.ITEM_USED.get(this));
        if (!playerIn.abilities.isCreativeMode) {
            stack.shrink(1);
        }
        return ActionResult.resultSuccess(stack);
    }

    protected void createGrenade(ItemStack stack, World world, PlayerEntity player) {

        AbstractGrenadeEntity grenade = factory.createGrenade(world, player);
        ItemStack throwStack = Helpers.cloneStack(stack, 1);
        throwStack.setDamage(1);
        grenade.setItem(throwStack);
        grenade.setRadius(1 + radius);
        grenade.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 0.5F);
        world.addEntity(grenade);
    }

    // region FACTORY
    public interface IGrenadeFactory<T extends AbstractGrenadeEntity> {

        T createGrenade(World world, LivingEntity living);

        T createGrenade(World world, double posX, double posY, double posZ);

    }
    // endregion

    // region DISPENSER BEHAVIOR
    private static final ProjectileDispenseBehavior DISPENSER_BEHAVIOR = new ProjectileDispenseBehavior() {

        @Override
        protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {

            ItemGrenadeBase grenadeItem = ((ItemGrenadeBase) stackIn.getItem());
            AbstractGrenadeEntity grenade = grenadeItem.factory.createGrenade(worldIn, position.getX(), position.getY(), position.getZ());
            ItemStack throwStack = Helpers.cloneStack(stackIn, 1);
            throwStack.setDamage(1);
            grenade.setItem(throwStack);
            grenade.setRadius(1 + grenadeItem.radius);
            return grenade;
        }

        @Override
        protected float getProjectileInaccuracy() {

            return 3.0F;
        }
    };
    // endregion
}
