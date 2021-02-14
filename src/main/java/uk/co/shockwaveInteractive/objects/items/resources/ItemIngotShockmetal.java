package uk.co.shockwaveinteractive.objects.items.resources;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.items.ItemBase;

import javax.annotation.Nullable;
import java.util.List;

import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_INFO_PREFIX;

public class ItemIngotShockmetal extends ItemBase {

    public ItemIngotShockmetal() {
        super(new Item.Properties().group(ShockMetalMain.SHOCKMETALTAB).isImmuneToFire());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TranslationTextComponent(TRANSLATION_INFO_PREFIX + "shockmetal.ingot").mergeStyle(TextFormatting.DARK_PURPLE));
    }

}
