package vnemesis.shockmetal.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import vnemesis.shockmetal.entity.transport.VacuumMinecartEntity;

import javax.annotation.Nonnull;

public class ItemVacuumMinecart extends Item {

    private static final DispenseItemBehavior MINECART_DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {
        private final DefaultDispenseItemBehavior fallback = new DefaultDispenseItemBehavior();

        @Override
        public @Nonnull ItemStack execute(BlockSource source, @Nonnull ItemStack stack) {
            Direction facing = source.state().getValue(DispenserBlock.FACING);
            Level level = source.level();
            BlockPos dispenserPos = source.pos();
            double d0 = dispenserPos.getX() + 0.5 + facing.getStepX() * 1.125;
            double d1 = dispenserPos.getY() + facing.getStepY();
            double d2 = dispenserPos.getZ() + 0.5 + facing.getStepZ() * 1.125;
            BlockPos pos = source.pos().relative(facing);
            BlockState state = level.getBlockState(pos);
            RailShape shape = state.getBlock() instanceof BaseRailBlock rail
                ? rail.getRailDirection(state, level, pos, null) : RailShape.NORTH_SOUTH;
            double d3;
            if (state.is(BlockTags.RAILS)) {
                d3 = shape.isAscending() ? 0.6 : 0.1;
            } else {
                if (!state.isAir() || !level.getBlockState(pos.below()).is(BlockTags.RAILS)) {
                    return fallback.dispense(source, stack);
                }
                BlockState below = level.getBlockState(pos.below());
                RailShape belowShape = below.getBlock() instanceof BaseRailBlock rb
                    ? below.getValue(rb.getShapeProperty()) : RailShape.NORTH_SOUTH;
                d3 = (facing != Direction.DOWN && belowShape.isAscending()) ? -0.4 : -0.9;
            }
            VacuumMinecartEntity cart = new VacuumMinecartEntity(d0, d1 + d3, d2, level);
            if (stack.has(DataComponents.CUSTOM_NAME)) cart.setCustomName(stack.getHoverName());
            level.addFreshEntity(cart);
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {
            source.level().levelEvent(1000, source.pos(), 0);
        }
    };

    public ItemVacuumMinecart() {
        super(new Item.Properties());
        DispenserBlock.registerBehavior(this, MINECART_DISPENSER_BEHAVIOR);
    }

    @Override
    public @Nonnull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(BlockTags.RAILS)) return InteractionResult.FAIL;

        ItemStack stack = context.getItemInHand();
        if (!level.isClientSide) {
            RailShape shape = state.getBlock() instanceof BaseRailBlock rail
                ? rail.getRailDirection(state, level, pos, null) : RailShape.NORTH_SOUTH;
            double offset = shape.isAscending() ? 0.5 : 0.0;
            VacuumMinecartEntity cart = new VacuumMinecartEntity(
                pos.getX() + 0.5, pos.getY() + 0.0625 + offset, pos.getZ() + 0.5, level);
            if (stack.has(DataComponents.CUSTOM_NAME)) cart.setCustomName(stack.getHoverName());
            level.addFreshEntity(cart);
        }
        stack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

}