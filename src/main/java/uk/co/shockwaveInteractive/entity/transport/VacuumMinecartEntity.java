package uk.co.shockwaveinteractive.entity.transport;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity.Type;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraftforge.fml.network.NetworkHooks;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static uk.co.shockwaveinteractive.init.Entities.VACUUM_MINECART_ENTITY;
import static uk.co.shockwaveinteractive.init.Items.VACUUM_MINECART_ITEM;

public class VacuumMinecartEntity extends AbstractMinecartContainer implements Hopper {

    public VacuumMinecartEntity(EntityType<? extends VacuumMinecartEntity> type, Level world) {
        super(type, world);
    }

    public VacuumMinecartEntity(Level worldIn, double x, double y, double z) {
        super(VACUUM_MINECART_ENTITY.get(), x, y, z, worldIn);
    }

    public void onEntityCollidedWithCart(Entity entity) {
        if (!level.isClientSide) {
            if (entity instanceof ItemEntity && entity.isAlive()) {
                final ItemEntity item = (ItemEntity) entity;
                HopperTileEntity.addItem(this, item);
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
                    entity.setDeltaMovement(new Vector3d(dx / distance * var11 * 0.05, dy / distance * var11 * 0.2, dz / distance * var11 * 0.05));

                    if (!Utility.isServerWorld(level) && tickCounter > 10) {
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
    public World getLevel() {
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

    public Container createMenu(int id, PlayerInventory playerInventoryIn) {
        return ChestContainer.threeRows(id, playerInventoryIn, this);
    }

    @Override
    public ItemStack getCartItem() {

        return new ItemStack(VACUUM_MINECART_ITEM.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
