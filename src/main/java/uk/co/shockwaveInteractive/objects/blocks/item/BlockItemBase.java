package uk.co.shockwaveinteractive.objects.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class BlockItemBase extends BlockItem
{

	public BlockItemBase(Block block) {
		super(block, new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB).isImmuneToFire());
	}
}
