package uk.co.shockwaveInteractive.objects.tools;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
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
			tooltip.add(ChatFormatting.WHITE + "The sword seems to rip the atoms of undead enenmies causing them to combust. It also seems to absorb the energy to hep heal your wounds.");
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
