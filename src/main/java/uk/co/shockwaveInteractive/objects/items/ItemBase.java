package uk.co.shockwaveInteractive.objects.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public boolean hasEffect(ItemStack stack) {
		
		if(isRare)
		{
			return true;
		}
		
		return super.hasEffect(stack);
	}
	
}
