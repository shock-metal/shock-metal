package uk.co.shockwaveinteractive.entity.transport;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static uk.co.shockwaveinteractive.init.Entities.VACUUM_MINECART_ENTITY;

public class VacuumMinecartEntity extends AbstractMinecartContainer implements Hopper {

    public VacuumMinecartEntity(EntityType<? extends VacuumMinecartEntity> type, Level world) {
        super(type, world);
    }

    public VacuumMinecartEntity(double x, double y, double z, Level worldIn) {
        super(VACUUM_MINECART_ENTITY.get(), x, y, z, worldIn);
    }

    public void onEntityCollidedWithCart(Entity entity) {
        if (!level.isClientSide) {
            if (entity instanceof ItemEntity && entity.isAlive()) {
                final ItemEntity item = (ItemEntity) entity;
                HopperBlockEntity.addItem(this, item);
            }
        }
    }

    private final Predicate<Entity> entitySelector = entity -> {
        if (!entity.isAlive()) return false;

        return entity instanceof ItemEntity;
    };

    private int tickCounter = 0;

    /**
     * Modified version of Open blocks vacuum hopper
     */
    @Override
    public void tick() {

        super.tick();

        BlockPos pos = this.blockPosition();
        float radius = MainConfig.vacuumMinecartRange;

        List<Entity> nearbyItems = level.getEntitiesOfClass(Entity.class, getBoundingBox().inflate(radius), entitySelector);

        for (Entity entity : nearbyItems) {
            double dx = (pos.getX() + 0.5D - entity.getX());
            double dy = (pos.getY() + 0.5D - entity.getY());
            double dz = (pos.getZ() + 0.5D - entity.getZ());

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance < 1.1) {
                onEntityCollidedWithCart(entity);
            } else {
                double var11 = 1.0 - distance / 15.0;

                if (var11 > 0.0D) {
                    var11 *= var11;
                    entity.setDeltaMovement(new Vec3(dx / distance * var11 * 0.05, dy / distance * var11 * 0.2, dz / distance * var11 * 0.05));

                    if (!Utility.isServerLevel(level) && tickCounter > 10) {
                        this.level.addParticle(ParticleTypes.PORTAL, entity.getX(), entity.getY() - 0.2, entity.getZ(), 0, 0, 0);
                    }
                }
            }
        }
        if (tickCounter > 20) {
            tickCounter = 0;
        } else {
            tickCounter++;
        }
    }

    @Nullable
    @Override
    public Level getLevel() {
        return this.level;
    }

    /**
     * Gets the world X position for this hopper entity.
     */
    @Override
    public double getLevelX() {
        return this.getX();
    }

    /**
     * Gets the world Y position for this hopper entity.
     */
    @Override
    public double getLevelY() {
        return this.getY();
    }

    /**
     * Gets the world Z position for this hopper entity.
     */
    @Override
    public double getLevelZ() {
        return this.getZ();
    }


    public void destroy(DamageSource source) {
        super.destroy(source);
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.spawnAtLocation(Blocks.CHEST);
            this.spawnAtLocation(Blocks.HOPPER);
            this.spawnAtLocation(Items.SHOCKMETAL_DUST.get());
        }

    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize() {
        return 27;
    }

    public Type getMinecartType() {
        return Type.CHEST;
    }

    public BlockState getDefaultDisplayBlockState() {
        return Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.NORTH);
    }

    public int getDefaultDisplayOffset() {
        return 8;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventoryIn) {
        return ChestMenu.threeRows(id, playerInventoryIn, this);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(Items.VACUUM_MINECART_ITEM.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
