package uk.co.shockwaveinteractive.entity.projectile;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Items;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;

import java.lang.reflect.Field;

import static uk.co.shockwaveinteractive.init.Entities.SHOCK_GRENADE_ENTITY;

public class ShockGrenadeEntity extends AbstractGrenadeEntity {

    public ShockGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public ShockGrenadeEntity(World worldIn, double x, double y, double z) {

        super(SHOCK_GRENADE_ENTITY.get(), x, y, z, worldIn);
    }

    public ShockGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(SHOCK_GRENADE_ENTITY.get(), livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return Items.SHOCK_GRENADE_ITEM.get();
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (Utility.isServerWorld(world)) {

            BlockPos pos = this.getPosition();
            @Nullable Entity source = func_234616_v_();
            ServerWorld worldAsServer = (ServerWorld) world;

            AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
            world.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE)
                    .forEach(livingEntity -> {
                        if(livingEntity.getCreatureAttribute() == CreatureAttribute.UNDEAD && !livingEntity.isImmuneToFire())
                        {
                            livingEntity.setFire(5);
                        }

                        if(livingEntity.getEntity() instanceof CreeperEntity) {
                            CreeperEntity creeper =  (CreeperEntity) livingEntity.getEntity();

                            if(!creeper.isCharged() && ShockMetalMain.rnd.nextInt(100) < 19) {
                                try
                                {
                                    Field field;
                                    field = creeper.getClass().getDeclaredField("POWERED");
                                    field.setAccessible(true);
                                    DataParameter<Boolean> powered = (DataParameter<Boolean>) field.get(creeper);
                                    creeper.getDataManager().set(powered, true);
                                } catch (NoSuchFieldException | IllegalAccessException ignored) { }
                            }

                        } else if (livingEntity.getEntity() instanceof PigEntity) {
                            PigEntity piggu =  (PigEntity) livingEntity.getEntity();
                            if (worldAsServer.getDifficulty() != Difficulty.PEACEFUL) {
                                ZombifiedPiglinEntity zombifiedpiglinentity = EntityType.ZOMBIFIED_PIGLIN.create(worldAsServer);
                                zombifiedpiglinentity.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(net.minecraft.item.Items.GOLDEN_SWORD));
                                zombifiedpiglinentity.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, this.rotationPitch);
                                zombifiedpiglinentity.setNoAI(piggu.isAIDisabled());
                                zombifiedpiglinentity.setChild(piggu.isChild());
                                if (this.hasCustomName()) {
                                    zombifiedpiglinentity.setCustomName(this.getCustomName());
                                    zombifiedpiglinentity.setCustomNameVisible(this.isCustomNameVisible());
                                }

                                zombifiedpiglinentity.enablePersistence();
                                worldAsServer.addEntity(zombifiedpiglinentity);
                                piggu.remove();
                            }
                        }
                        livingEntity.attackEntityFrom(DamageSource.causeExplosionDamage(source instanceof LivingEntity ? (LivingEntity) source : null), 15f);
                    });

            world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), 1.8f, false, Explosion.Mode.NONE);
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }
}
