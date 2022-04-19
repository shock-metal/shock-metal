package uk.co.shockwaveinteractive.objects.tools;


import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

public class ShockmetalToolHoe extends HoeItem {

	public ShockmetalToolHoe()
	{
		super(
				ShockmetalItemTier.SHOCKMETAL,
				0,
				-2.0f,
				new Item.Properties()
						.tab(ShockMetalMain.SHOCKMETALTAB)
						.fireResistant()
		);
	}
}
