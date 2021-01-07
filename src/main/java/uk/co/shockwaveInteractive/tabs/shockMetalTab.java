package uk.co.shockwaveinteractive.tabs;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveinteractive.init.Items;

public class ShockMetalTab extends ItemGroup
{

	public ShockMetalTab(String label) {
		super("shockmetaltab");		
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Items.SHOCKMETAL_INGOT.get());
	}
	

}
