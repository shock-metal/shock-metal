package uk.co.shockwaveinteractive.objects.tools;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.init.Enchantments;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;
import uk.co.shockwaveinteractive.util.Utilities;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.ChatFormatting.DARK_PURPLE;
import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_INFO_PREFIX;
import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_SHIFT_INFO;


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
						.tab(ShockMetalMain.SHOCKMETALTAB)
						.fireResistant()
		);

	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		return super.getItemEnchantability(stack) - 4;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		super.appendHoverText(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponent(String.format("Charge: %s/%s (%sx)", charge, maxCharge, getMultiplier())).withStyle(DARK_PURPLE));

		if(Screen.hasShiftDown())
		{
			tooltip.add(new TranslatableComponent(TRANSLATION_INFO_PREFIX + "shockmetal.sword").withStyle(ChatFormatting.WHITE));
		}
		else tooltip.add(new TranslatableComponent(TRANSLATION_SHIFT_INFO).withStyle(ChatFormatting.GRAY));
	}
	
	
	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker)
	{
		boolean hasAtomRipperEnchant = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.ATOM_RIPPER.get(), stack) > 0;

		// If target is undead, has 20% chance to catch fire and apply regen effect
		if( !hasAtomRipperEnchant && (target).getMobType() == MobType.UNDEAD && ShockMetalMain.rnd.nextInt(100) < 19)
		{
			target.setSecondsOnFire(5);
			attacker.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
		}

		if (target.isAlive() && charge < maxCharge) {
			charge++;
		}
		
		return super.hurtEnemy(stack, target, attacker);
	}


	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {

			if (Utilities.isServerLevel(worldIn) && playerIn.isCrouching() && charge >= 5) {

				int baseRadius = 2;

				if(charge == 20) {
					charge = 0;
				} else if (charge >= 10) {
					charge -= 10;
				} else {
					charge -= 5;
				}

				int radius = baseRadius * getMultiplier();

				ServerLevel levelAsServer = (ServerLevel) worldIn;
				AABB aabb = playerIn.getBoundingBox().inflate(radius, radius, radius);
				List<LivingEntity> list = levelAsServer.getEntitiesOfClass(LivingEntity.class, aabb);

				if(!list.isEmpty())
				{
					list.forEach(livingEntity -> {
						if(livingEntity.getMobType() == MobType.UNDEAD && !livingEntity.fireImmune())
						{
							livingEntity.setSecondsOnFire(5);
						}
						livingEntity.hurt(DamageSource.explosion(playerIn), (6.0f * getMultiplier()));
					});
				}

				levelAsServer.addParticle(ParticleTypes.EXPLOSION, playerIn.getX(), playerIn.getY(), playerIn.getZ(), 1.0D, 0.0D, 0.0D);
				levelAsServer.playSound(
						playerIn,
						playerIn.getX(),
						playerIn.getY(),
						playerIn.getZ(),
						SoundEvents.LIGHTNING_BOLT_IMPACT,
						SoundSource.BLOCKS,
						0.5f,
						(1.0F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F) * 0.7F);

			}

		return super.use(worldIn, playerIn, handIn);
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return stack.isEnchanted() || charge == maxCharge;
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
