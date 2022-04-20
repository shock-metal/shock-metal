package uk.co.shockwaveinteractive.tabs;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import uk.co.shockwaveinteractive.init.Items;

public class ShockMetalCreativeTab extends CreativeModeTab
{
	public ShockMetalCreativeTab(String label) {
		super("shockmetaltab");		
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(Items.SHOCKMETAL_INGOT.get());
	}
	

}
