package uk.co.shockwaveInteractive.objects.items;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ItemBase extends Item implements IHasModel
{
	public boolean isRare;
	
	public ItemBase(String name, boolean isRare)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		this.isRare = isRare;
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		
		ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
		if(this.getUnlocalizedName().equals("item.ingot_shockmetal"))
		{
			tooltip.add(ChatFormatting.DARK_PURPLE + "Shocking!");
		}
		
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		
		if(isRare)
		{
			return true;
		}
		
		return super.hasEffect(stack);
	}
	
}
