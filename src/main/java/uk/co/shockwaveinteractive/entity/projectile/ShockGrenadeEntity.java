package uk.co.shockwaveinteractive.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utilities;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;

import static uk.co.shockwaveinteractive.init.Entities.SHOCK_GRENADE_ENTITY;

public class ShockGrenadeEntity extends AbstractGrenadeEntity {

    public ShockGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
        super(type, worldIn);
    }

    public ShockGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(SHOCK_GRENADE_ENTITY.get(), x, y, z, worldIn);
    }

    public ShockGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(SHOCK_GRENADE_ENTITY.get(), livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SHOCK_GRENADE_ITEM.get();
    }


    @Override
    protected void onHit(HitResult result) {

        if (Utilities.isServerLevel(level)) {

            BlockPos pos = this.blockPosition();
            @Nullable Entity source = this;
            ServerLevel levelAsServer = (ServerLevel) level;

            AABB aabb = source.getBoundingBox().inflate(radius, radius, radius);
            List<LivingEntity> list = levelAsServer.getEntitiesOfClass(LivingEntity.class, aabb);
            list.forEach(livingEntity -> {
                if(livingEntity.getMobType() == MobType.UNDEAD && !livingEntity.fireImmune())
                {
                    livingEntity.setSecondsOnFire(5);
                }

                if(livingEntity instanceof Creeper) {
                    Creeper creeper =  (Creeper) livingEntity;

                    if(!creeper.isPowered() && ShockMetalMain.rnd.nextInt(100) < 39) {
                        try
                        {
                            Field field;
                            field = creeper.getClass().getDeclaredField("POWERED");
                            field.setAccessible(true);
                            EntityDataAccessor<Boolean> powered = (EntityDataAccessor<Boolean>) field.get(creeper);
                            creeper.getEntityData().set(powered, true);
                        } catch (NoSuchFieldException | IllegalAccessException ignored) { }
                    }
                    applyDamage(source, livingEntity);

                } else if (livingEntity instanceof Pig) {
                    Pig piggu = (Pig) livingEntity;
                    if (levelAsServer.getDifficulty() != Difficulty.PEACEFUL) {
                        ZombifiedPiglin zombifiedpiglin = EntityType.ZOMBIFIED_PIGLIN.create(levelAsServer);
                        zombifiedpiglin.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(net.minecraft.world.item.Items.GOLDEN_SWORD));
                        zombifiedpiglin.moveTo(piggu.getX(), piggu.getY(), piggu.getZ(), this.getYRot(), this.getXRot());
                        zombifiedpiglin.setNoAi(piggu.isNoAi());
                        zombifiedpiglin.setBaby(piggu.isBaby());
                        if (this.hasCustomName()) {
                            zombifiedpiglin.setCustomName(this.getCustomName());
                            zombifiedpiglin.setCustomNameVisible(this.isCustomNameVisible());
                        }

                        zombifiedpiglin.setPersistenceRequired();
                        levelAsServer.addFreshEntity(zombifiedpiglin);
                        piggu.remove(RemovalReason.KILLED);
                    }
                }
                else
                {
                    applyDamage(source, livingEntity);
                }

            });

            level.explode(this, this.getX(), this.getY(), this.getZ(), 1.8f, false, Explosion.BlockInteraction.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove(RemovalReason.KILLED);
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public void applyDamage(Entity source, LivingEntity livingEntity) {
        livingEntity.hurt(DamageSource.explosion(source instanceof LivingEntity ? (LivingEntity) source : null), 20f);
    }
}
