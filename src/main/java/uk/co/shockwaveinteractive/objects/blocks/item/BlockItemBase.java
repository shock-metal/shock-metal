package uk.co.shockwaveinteractive.objects.blocks.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class BlockItemBase extends BlockItem
{

	public BlockItemBase(Block block) {
		super(block, new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).fireResistant());
	}
}
