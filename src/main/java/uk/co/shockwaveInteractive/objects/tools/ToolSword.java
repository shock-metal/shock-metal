package uk.co.shockwaveInteractive.objects.tools;

import java.util.List;

import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ToolSword extends ItemSword implements IHasModel
{

	public ToolSword(String name, ToolMaterial material) 
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		if(GuiScreen.isShiftKeyDown())
		{
			tooltip.add(ChatFormatting.WHITE + "The sword seems to rip the atoms of undead enenmies causing them to combust. It also seems to absorb the energy to help heal your wounds.");
		}
		else tooltip.add(ChatFormatting.DARK_PURPLE + "Press <<SHIFT>> for more Info");
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) 
	{	
		if(target.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) 
		{
			target.setFire(5);
			attacker.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 100));
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
	
	@Override
	public void registerModels() {
		
		ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
	// used to customise attributes
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);
        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ((float) ShockMetalConfiguration.materialDamage + 4.0f), 0));
        }
        return multimap;
    }

}
