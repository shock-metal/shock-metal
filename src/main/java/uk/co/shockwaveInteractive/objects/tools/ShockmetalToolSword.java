package uk.co.shockwaveinteractive.objects.tools;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureAttribute;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;

import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.ZombifiedPiglinEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;
import uk.co.shockwaveinteractive.util.Utility;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_INFO_PREFIX;
import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_SHIFT_INFO;
//import uk.co.shockwaveinteractive.util.config.ShockMetalConfiguration;


public class ShockmetalToolSword extends SwordItem
{
	private int charge = 0;
	private static final int maxCharge = 20;

	public ShockmetalToolSword() {
		super(
				ShockmetalItemTier.SHOCKMETAL,
				3,
				-1.0f,
				new Item.Properties()
						.group(ShockMetalMain.SHOCKMETALTAB)
						.isImmuneToFire()
		);
	}

	@Override
	public int getItemEnchantability() {
		return super.getItemEnchantability() - 4;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new StringTextComponent(String.format("Charge: %s/%s (%sx)", charge, maxCharge, getMultiplier())).mergeStyle(TextFormatting.DARK_PURPLE));

		if(Screen.hasShiftDown())
		{
			tooltip.add(new TranslationTextComponent(TRANSLATION_INFO_PREFIX + "shockmetal.sword").mergeStyle(TextFormatting.WHITE));
		}
		else tooltip.add(new TranslationTextComponent(TRANSLATION_SHIFT_INFO).mergeStyle(TextFormatting.GRAY));
	}
	
	
	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		// If target is undead, has 20% chance to catch fire and apply regen effect
		if(target.getCreatureAttribute() == CreatureAttribute.UNDEAD && ShockMetalMain.rnd.nextInt(100) < 19)
		{
			target.setFire(5);
			attacker.addPotionEffect(new EffectInstance(Effect.get(10), 100));
		}

		if (target.isAlive() && charge < maxCharge) {
			charge++;
		}
		
		return super.hitEntity(stack, target, attacker);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

			if (Utility.isServerWorld(worldIn) && playerIn.isSneaking() && charge >= 5) {

				int baseRadius = 2;

				if(charge == 20) {
					charge = 0;
				} else if (charge >= 10) {
					charge -= 10;
				} else {
					charge -= 5;
				}

				int radius = baseRadius * getMultiplier();

				BlockPos pos =  playerIn.getPosition();
				ServerWorld worldAsServer = (ServerWorld) worldIn;
				AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));

				worldIn.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE)
						.forEach(livingEntity -> {
							if(livingEntity.getCreatureAttribute() == CreatureAttribute.UNDEAD && !livingEntity.isImmuneToFire())
							{
								livingEntity.setFire(5);
							}
							livingEntity.attackEntityFrom(DamageSource.causeExplosionDamage(playerIn), (6.0f * getMultiplier()));
						});

				worldAsServer.addParticle(ParticleTypes.EXPLOSION, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), 1.0D, 0.0D, 0.0D);
				worldAsServer.playSound(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(),
						SoundEvents.ENTITY_CREEPER_PRIMED,
						SoundCategory.BLOCKS,
						0.5F,
						(1.0F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F) * 0.7F,
						false);
			}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return charge == maxCharge;
	}

	private int getMultiplier() {
		if(charge == 20) {
			return 3;
		} else if (charge >= 10) {
			return 2;
		}
		return 1;
	}
}
