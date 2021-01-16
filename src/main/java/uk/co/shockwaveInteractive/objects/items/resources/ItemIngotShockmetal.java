package uk.co.shockwaveinteractive.objects.items.resources;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.objects.items.ItemBase;

import javax.annotation.Nullable;
import java.util.List;

public class ItemIngotShockmetal extends ItemBase {

    public ItemIngotShockmetal() {
        super();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent("info.shockwave.shockmetal.ingot").mergeStyle(TextFormatting.DARK_PURPLE));
    }

}
