package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.item.Item;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class ItemBase extends Item
{

	public ItemBase(Properties props) {
		super(props);
	}

	public ItemBase() {
		super(new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB));
	}
}
