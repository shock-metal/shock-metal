package uk.co.shockwaveinteractive.objects.tools;

import net.minecraft.entity.CreatureAttribute;

import net.minecraft.entity.LivingEntity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

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
		);
	}

//	@Override
//	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//
//		if(GuiScreen.isShiftKeyDown())
//		{
//			tooltip.add(ChatFormatting.WHITE + "The sword seems to rip the atoms of undead enenmies causing them to combust. It also seems to absorb the energy to help heal your wounds.");
//		}
//		else tooltip.add(ChatFormatting.DARK_PURPLE + "Press <<SHIFT>> for more Info");
//
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//	}
	
	
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
	
	// used to customise attributes
//	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
//    {
//        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
//        if (slot == EntityEquipmentSlot.MAINHAND)
//        {
//            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
//            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ((float) ShockMetalConfiguration.materialDamage + 4.0f), 0));
//        }
//        return multimap;
//    }

}
