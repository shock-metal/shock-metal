package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveinteractive.ShockMetalMain;

public class ItemBase extends Item
{
	public boolean isRare;

	public ItemBase(boolean isRare) {
		super(new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB));
		this.isRare = isRare;
	}

//	@Override
//	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//
//		if(this.getTranslationKey().equals("item.ingot_shockmetal"))
//		{
//			tooltip.add(ChatFormatting.DARK_PURPLE + "Shocking!");
//		}
//
//		super.addInformation(stack, worldIn, tooltip, flagIn);
//	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		
		if(isRare)
		{
			return true;
		}
		
		return super.hasEffect(stack);
	}
	
}
