package uk.co.shockwaveInteractive.objects.tools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class ToolPickaxe extends ItemPickaxe implements IHasModel 
{
	
	public ToolPickaxe(String name, ToolMaterial material) 
	{
		super(material);
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		
		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		
		ShockMetalMain.proxy.registerItemRenderer(this, 0, "inventory");
		
	}
}
