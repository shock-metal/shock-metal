package vnemesis.shockmetal.entity.projectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;

public abstract class AbstractGrenadeEntity extends ThrowableItemProjectile
{
    public int radius = 5;

    public AbstractGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public AbstractGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type,
                                  double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public AbstractGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type,
                                  LivingEntity thrower, Level world) {
        super(type, thrower, world);
    }

    public AbstractGrenadeEntity setRadius(int radius) {
        this.radius = radius;
        return this;
    }
}

