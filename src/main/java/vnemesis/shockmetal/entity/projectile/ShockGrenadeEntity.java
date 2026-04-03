package vnemesis.shockmetal.entity.projectile;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import vnemesis.shockmetal.entity.ShockMetalEntitiesRegistry;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;

import java.util.List;
import java.util.Random;

public class ShockGrenadeEntity extends AbstractGrenadeEntity
{
    private static final Random RND = new Random();

    public ShockGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level world) {
        super(type, world);
    }

    public ShockGrenadeEntity(Level world, double x, double y, double z) {
        super(ShockMetalEntitiesRegistry.SHOCK_GRENADE_ENTITY.get(), x, y, z, world);
    }

    public ShockGrenadeEntity(Level world, LivingEntity thrower) {
        super(ShockMetalEntitiesRegistry.SHOCK_GRENADE_ENTITY.get(), thrower, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ShockMetalItemsRegistry.SHOCK_GRENADE_ITEM.get();
    }

    @Override
    protected void onHit(HitResult result) {
        Level level = level();
        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            AABB aabb = getBoundingBox().inflate(radius, radius, radius);
            List<LivingEntity> nearby = serverLevel.getEntitiesOfClass(LivingEntity.class, aabb);

            nearby.forEach(living -> {
                // Set undead on fire
                if (living.getType().is(EntityTypeTags.UNDEAD) && !living.fireImmune()) {
                    living.igniteForSeconds(5);
                }

                if (living instanceof Creeper creeper) {
                    if (!creeper.isPowered() && RND.nextInt(100) < 39) {
                        creeper.thunderHit(serverLevel, null);
                    }
                    applyDamage(this, living);
                } else if (living instanceof Pig pig) {
                    if (serverLevel.getDifficulty() != Difficulty.PEACEFUL) {
                        ZombifiedPiglin piglin = EntityType.ZOMBIFIED_PIGLIN.create(serverLevel);
                        if (piglin != null) {
                            piglin.setItemSlot(EquipmentSlot.MAINHAND,
                                new ItemStack(net.minecraft.world.item.Items.GOLDEN_SWORD));
                            piglin.moveTo(pig.getX(), pig.getY(), pig.getZ(),
                                this.getYRot(), this.getXRot());
                            piglin.setNoAi(pig.isNoAi());
                            piglin.setBaby(pig.isBaby());
                            if (this.hasCustomName()) {
                                piglin.setCustomName(this.getCustomName());
                                piglin.setCustomNameVisible(this.isCustomNameVisible());
                            }
                            piglin.setPersistenceRequired();
                            serverLevel.addFreshEntity(piglin);
                            pig.remove(RemovalReason.KILLED);
                        }
                    }
                } else {
                    applyDamage(this, living);
                }
            });

            level.explode(this, getX(), getY(), getZ(), 1.8f,
                Level.ExplosionInteraction.NONE);
            this.discard();
        }

        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) return;

        level().addParticle(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1.0, 0.0, 0.0);
        level().playLocalSound(getX(), getY(), getZ(),
            SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS,
            0.5f, (1.0f + (level().random.nextFloat() - level().random.nextFloat()) * 0.2f) * 0.7f, false);
    }

    private void applyDamage(Entity source, LivingEntity target) {
        target.hurt(level().damageSources().generic(), 20f);
    }
}




