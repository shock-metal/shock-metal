package uk.co.shockwaveinteractive.entity.transport;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.IHopper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

import static uk.co.shockwaveinteractive.init.Entities.VACUUM_MINECART_ENTITY;
import static uk.co.shockwaveinteractive.init.Items.VACUUM_MINECART_ITEM;

public class VacuumMinecartEntity extends ContainerMinecartEntity implements IHopper {

    public VacuumMinecartEntity(EntityType<? extends VacuumMinecartEntity> type, World world) {
        super(type, world);
    }

    public VacuumMinecartEntity(World worldIn, double x, double y, double z) {
        super(VACUUM_MINECART_ENTITY.get(), x, y, z, worldIn);
    }

    public void onEntityCollidedWithCart(Entity entity) {
        if (!world.isRemote) {
            if (entity instanceof ItemEntity && entity.isAlive()) {
                final ItemEntity item = (ItemEntity) entity;
                HopperTileEntity.captureItem(this, item);
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

        BlockPos pos = this.getPosition();
        float radius = MainConfig.vacuumMinecartRange;

        List<Entity> nearbyItems = world.getEntitiesWithinAABB(Entity.class, getBoundingBox().grow(radius), entitySelector);

        for (Entity entity : nearbyItems) {
            double dx = (pos.getX() + 0.5D - entity.getPosX());
            double dy = (pos.getY() + 0.5D - entity.getPosY());
            double dz = (pos.getZ() + 0.5D - entity.getPosZ());

            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (distance < 1.1) {
                onEntityCollidedWithCart(entity);
            } else {
                double var11 = 1.0 - distance / 15.0;

                if (var11 > 0.0D) {
                    var11 *= var11;
                    entity.setMotion(new Vector3d(dx / distance * var11 * 0.05, dy / distance * var11 * 0.2, dz / distance * var11 * 0.05));

                    if (!Utility.isServerWorld(world) && tickCounter > 10) {
                        this.world.addParticle(ParticleTypes.PORTAL, entity.getPosX(), entity.getPosY() - 0.2, entity.getPosZ(), 0, 0, 0);
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
    public World getWorld() {
        return this.world;
    }

    /**
     * Gets the world X position for this hopper entity.
     */
    @Override
    public double getXPos() {
        return this.getPosX();
    }

    /**
     * Gets the world Y position for this hopper entity.
     */
    @Override
    public double getYPos() {
        return this.getPosY();
    }

    /**
     * Gets the world Z position for this hopper entity.
     */
    @Override
    public double getZPos() {
        return this.getPosZ();
    }


    public void killMinecart(DamageSource source) {
        super.killMinecart(source);
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.entityDropItem(Blocks.CHEST);
            this.entityDropItem(Blocks.HOPPER);
            this.entityDropItem(Items.SHOCKMETAL_DUST.get());
        }

    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory() {
        return 27;
    }

    public Type getMinecartType() {
        return Type.CHEST;
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.CHEST.getDefaultState().with(ChestBlock.FACING, Direction.NORTH);
    }

    public int getDefaultDisplayTileOffset() {
        return 8;
    }

    public Container createContainer(int id, PlayerInventory playerInventoryIn) {
        return ChestContainer.createGeneric9X3(id, playerInventoryIn, this);
    }

    @Override
    public ItemStack getCartItem() {

        return new ItemStack(VACUUM_MINECART_ITEM.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {

        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
