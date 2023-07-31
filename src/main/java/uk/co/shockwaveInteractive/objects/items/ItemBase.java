package uk.co.shockwaveinteractive.objects.items;


import net.minecraft.world.item.Item;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class ItemBase extends Item
{

	public ItemBase(Properties props) {
		super(props);
	}

	public ItemBase() {
		super(new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB));
	}
}
