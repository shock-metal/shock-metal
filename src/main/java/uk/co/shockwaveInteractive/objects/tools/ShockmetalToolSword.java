package uk.co.shockwaveinteractive.objects.tools;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.CreatureAttribute;

import net.minecraft.entity.LivingEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
//import uk.co.shockwaveinteractive.util.config.ShockMetalConfiguration;


public class ShockmetalToolSword extends SwordItem
{
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

		if(Screen.hasShiftDown())
		{
			tooltip.add(new TranslationTextComponent("info.shockwave.shockmetal.sword").mergeStyle(TextFormatting.WHITE));
		}
		else tooltip.add(new TranslationTextComponent("info.shockwave.gui.shift-info").mergeStyle(TextFormatting.GRAY));

		super.addInformation(stack, worldIn, tooltip, flagIn);
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
		
		return super.hitEntity(stack, target, attacker);
	}
	
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
//	 {
//		// Get the sword in his hand
//		 ItemStack itemStack = playerIn.getHeldItem(handIn);
//		 
//		if (!playerIn.world.isRemote && ShockMetalConfiguration.swordLightning) 
//		{
//					
//			Vec3d posVec = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
//	        Vec3d lookVec = playerIn.getLookVec();
//	        
//	        RayTraceResult trace = worldIn.rayTraceBlocks(posVec, posVec.add(lookVec));
//	        
//	        if(trace != null)
//	        {
//				itemStack.damageItem(80, playerIn);
//		        BlockPos pos = trace.getBlockPos();
//		        
//		        double distance = posVec.distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
//		        
//				EntityLightningBolt lightning = new EntityLightningBolt(playerIn.getEntityWorld(), pos.getX(), pos.getY(), pos.getZ(), false);
//				playerIn.getEntityWorld().addWeatherEffect(lightning);
//	        }
//		}
//		  
//		 return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
//	}

}
