package uk.co.shockwaveinteractive.entity.projectile;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.server.ServerWorld;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;

import java.lang.reflect.Field;

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

        if (Utility.isServerWorld(level)) {

            BlockPos pos = this.blockPosition();
            @Nullable Entity source = getEntity();
            ServerWorld worldAsServer = (ServerWorld) level;

            AxisAlignedBB area = new AxisAlignedBB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
            level.getEntitiesOfClass(LivingEntity.class, area, EntityPredicates.ENTITY_STILL_ALIVE)
                    .forEach(livingEntity -> {
                        if(livingEntity.getMobType() == CreatureAttribute.UNDEAD && !livingEntity.fireImmune())
                        {
                            livingEntity.setSecondsOnFire(5);
                        }

                        if(livingEntity.getEntity() instanceof CreeperEntity) {
                            CreeperEntity creeper =  (CreeperEntity) livingEntity.getEntity();

                            if(!creeper.isPowered() && ShockMetalMain.rnd.nextInt(100) < 39) {
                                try
                                {
                                    Field field;
                                    field = creeper.getClass().getDeclaredField("POWERED");
                                    field.setAccessible(true);
                                    DataParameter<Boolean> powered = (DataParameter<Boolean>) field.get(creeper);
                                    creeper.getEntityData().set(powered, true);
                                } catch (NoSuchFieldException | IllegalAccessException ignored) { }
                            }
                            applyDamage(source, livingEntity);

                        } else if (livingEntity.getEntity() instanceof PigEntity) {
                            PigEntity piggu =  (PigEntity) livingEntity.getEntity();
                            if (worldAsServer.getDifficulty() != Difficulty.PEACEFUL) {
                                ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(worldAsServer);
                                zombifiedpiglinentity.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(net.minecraft.item.Items.GOLDEN_SWORD));
                                zombifiedpiglinentity.moveTo(piggu.getX(), piggu.getY(), piggu.getZ(), this.yRot, this.xRot);
                                zombifiedpiglinentity.setNoAi(piggu.isNoAi());
                                zombifiedpiglinentity.setBaby(piggu.isBaby());
                                if (this.hasCustomName()) {
                                    zombifiedpiglinentity.setCustomName(this.getCustomName());
                                    zombifiedpiglinentity.setCustomNameVisible(this.isCustomNameVisible());
                                }

                                zombifiedpiglinentity.setPersistenceRequired();
                                worldAsServer.addFreshEntity(zombifiedpiglinentity);
                                piggu.remove();
                            }
                        }
                        else
                        {
                            applyDamage(source, livingEntity);
                        }

                    });

            level.explode(this, this.getX(), this.getY(), this.getZ(), 1.8f, false, Explosion.Mode.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public void applyDamage(Entity source, LivingEntity livingEntity) {
        livingEntity.hurt(DamageSource.explosion(source instanceof LivingEntity ? (LivingEntity) source : null), 20f);
    }
}
