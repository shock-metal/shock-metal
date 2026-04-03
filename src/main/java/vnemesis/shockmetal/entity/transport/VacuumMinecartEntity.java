package vnemesis.shockmetal.entity.transport;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.phys.Vec3;
import vnemesis.shockmetal.Config;
import vnemesis.shockmetal.entity.ShockMetalEntitiesRegistry;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;

import java.util.List;
import java.util.function.Predicate;

public class VacuumMinecartEntity extends MinecartChest
{
    private int tickCounter = 0;

    private final Predicate<Entity> ITEM_SELECTOR =
        e -> e.isAlive() && e instanceof ItemEntity;

    public VacuumMinecartEntity(EntityType<? extends MinecartChest> type, Level world) {
        super(type, world);
    }

    public VacuumMinecartEntity(double x, double y, double z, Level world) {
        super(ShockMetalEntitiesRegistry.VACUUM_MINECART_ENTITY.get(), world);
        this.setPos(x, y, z);
    }

    @Override
    public EntityType<?> getType() {
        return ShockMetalEntitiesRegistry.VACUUM_MINECART_ENTITY.get();
    }

    @Override
    public void tick() {
        super.tick();

        double range = Config.VACUUM_MINECART_RANGE.get();
        List<Entity> nearbyItems = level().getEntitiesOfClass(Entity.class,
            getBoundingBox().inflate(range), ITEM_SELECTOR);

        for (Entity entity : nearbyItems) {
            double dx = (this.getX() + 0.5 - entity.getX());
            double dy = (this.getY() + 0.5 - entity.getY());
            double dz = (this.getZ() + 0.5 - entity.getZ());
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (dist < 1.1) {
                if (!level().isClientSide() && entity instanceof ItemEntity itemEntity) {
                    HopperBlockEntity.addItem(this, itemEntity);
                }
            } else {
                double pull = 1.0 - dist / 15.0;
                if (pull > 0.0) {
                    pull *= pull;
                    entity.setDeltaMovement(new Vec3(
                        dx / dist * pull * 0.05,
                        dy / dist * pull * 0.2,
                        dz / dist * pull * 0.05
                    ));
                    if (level().isClientSide() && tickCounter > 10) {
                        level().addParticle(ParticleTypes.PORTAL,
                            entity.getX(), entity.getY() - 0.2, entity.getZ(), 0, 0, 0);
                    }
                }
            }
        }

        tickCounter = (tickCounter > 20) ? 0 : tickCounter + 1;
    }

    @Override
    public void destroy(net.minecraft.world.damagesource.DamageSource source) {
        super.destroy(source);
        if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.spawnAtLocation(Blocks.CHEST);
            this.spawnAtLocation(Blocks.HOPPER);
            this.spawnAtLocation(ShockMetalItemsRegistry.SHOCKMETAL_DUST.get());
        }
    }

    @Override
    protected Item getDropItem() {
        return ShockMetalItemsRegistry.VACUUM_MINECART_ITEM.get();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ShockMetalItemsRegistry.VACUUM_MINECART_ITEM.get());
    }
}

