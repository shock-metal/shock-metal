package uk.co.shockwaveInteractive.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveInteractive.init.ItemInit;

public class shockMetalTab extends CreativeTabs
{

	public shockMetalTab(String label) {
		super("shockmetaltab");		
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ItemInit.INGOT_SHOCKMETAL);
	}
	

}
