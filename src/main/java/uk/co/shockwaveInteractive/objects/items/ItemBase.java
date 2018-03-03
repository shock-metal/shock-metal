package uk.co.shockwaveInteractive.objects.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		
		ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
	
}
