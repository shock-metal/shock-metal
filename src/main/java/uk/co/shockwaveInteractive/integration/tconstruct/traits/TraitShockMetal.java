package uk.co.shockwaveInteractive.integration.tconstruct.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

public class TraitShockMetal extends AbstractTrait{
	
	public static final TraitShockMetal traitShockMetal = new TraitShockMetal();

	private TraitShockMetal() 
	{
		super("shockMetal", 0xFFFF4F);
	}
	
	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) 
	{
		if (!player.world.isRemote && wasHit && random.nextFloat() <= 0.1f && ShockMetalConfiguration.swordLightning) 
		{
			EntityLightningBolt lightning = new EntityLightningBolt(player.getEntityWorld(), target.getPosition().getX(), target.getPosition().getY(), target.getPosition().getZ(), false);
			player.getEntityWorld().addWeatherEffect(lightning);
		}
		
		if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) 
		{
			target.setFire(5);
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 100));
		}
	}

}
