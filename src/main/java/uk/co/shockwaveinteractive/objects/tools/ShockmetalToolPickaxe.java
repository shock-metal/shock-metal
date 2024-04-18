package uk.co.shockwaveinteractive.objects.tools;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.materials.ShockmetalItemTier;

public class ShockmetalToolPickaxe extends PickaxeItem
{
	public ShockmetalToolPickaxe() {
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
