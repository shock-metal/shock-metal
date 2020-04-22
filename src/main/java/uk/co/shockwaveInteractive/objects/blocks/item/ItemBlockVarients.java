package uk.co.shockwaveInteractive.objects.blocks.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveInteractive.util.interfaces.IMetaName;

public class ItemBlockVarients extends ItemBlock
{

	public ItemBlockVarients(Block block) {
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getTranslationKey(ItemStack stack) {
		
		return super.getTranslationKey() + "_" + ((IMetaName)this.block).getSpecialName(stack);
	}
}
