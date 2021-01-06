package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import uk.co.shockwaveinteractive.ShockMetalMain;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRecipeTool extends ItemBase {
    public ItemRecipeTool() {
        super(new Item.Properties()
                .group(ShockMetalMain.SHOCKMETALTAB)
                .maxDamage(100)
        );
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        itemStack.setDamage(itemStack.getDamage() - 1);
        return itemStack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(
                new StringTextComponent(String.format("%s/%s", stack.getDamage(), stack.getMaxDamage()))
                .mergeStyle(TextFormatting.GRAY));
    }
}
