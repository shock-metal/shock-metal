package uk.co.shockwaveinteractive.objects.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

public class ShockmetalToolShovel extends ShovelItem
{

	public ShockmetalToolShovel()
	{
		super(
				ShockmetalItemTier.SHOCKMETAL,
				0,
				-2.0f,
				new Item.Properties()
						.group(ShockMetalMain.SHOCKMETALTAB)
						.isImmuneToFire()
		);
	}
}
