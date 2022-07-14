//package uk.co.shockwaveinteractive;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import uk.co.shockwaveinteractive.config.MainConfig;
import uk.co.shockwaveinteractive.objects.armour.ShockMetalArmour;

import java.util.List;

//public class PlayerTickHandler {
//    @SubscribeEvent
//    public void onLivingHurt(LivingHurtEvent event) {
//        LivingEntity entity = event.getEntityLiving();
//        if (entity instanceof PlayerEntity) {
//
//            PlayerEntity player = (PlayerEntity) entity;
//
//            if(MainConfig.armourBurstAbility && ShockMetalArmour.isWearingFullShockMentalSuit(player))
//            {
//                ShockMetalArmour chestplate = (ShockMetalArmour)player.inventory.armorItemInSlot(2).getItem();
//
//                if(event.getSource().damageType.equals("mob"))
//                {
//                    chestplate.increasePowerLevelByOne();
//                }
//
//                if(chestplate.canUseArmourBurst() && player.isSneaking())
//                {
//                    int baseRadius = 2;
//                    int radius = baseRadius * chestplate.getMultiplier();
//
//                    BlockPos pos =  player.getPosition();
//                    ServerWorld worldAsServer = (ServerWorld) player.getEntityWorld();
//                    AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
//
//                    List<LivingEntity> livingEntities = player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE);
//
//                    for (LivingEntity livingEntity : livingEntities) {
//                            if(livingEntity.getEntity() == player)
//                            {
//                                continue;
//                            }
//                            if(livingEntity.getCreatureAttribute() == CreatureAttribute.UNDEAD)
//                            {
//                                livingEntity.setFire(3);
//                            }
//                            livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 3));
//                            livingEntity.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 3));
//                            livingEntity.attackEntityFrom(DamageSource.causeThornsDamage(player), (2.0f * chestplate.getMultiplier()));
//                            livingEntity.applyKnockback(1.0f * chestplate.getMultiplier(), 1, 1);
//                    }
//
//                    worldAsServer.addParticle(ParticleTypes.EXPLOSION_EMITTER, player.getPosX(), player.getPosY(), player.getPosZ(), 0.0D, -0.5D, 0.0D);
//                    worldAsServer.playSound(player.getPosX(), player.getPosY(), player.getPosZ(),
//                            SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT,
//                            SoundCategory.BLOCKS,
//                            0.5F,
//                            (1.0F + (player.getEntityWorld().rand.nextFloat() - player.getEntityWorld().rand.nextFloat()) * 0.2F) * 0.7F,
//                            false);
//
//                    chestplate.resetPowerLevel();
//                }
//            }
//        }
//    }
//}
