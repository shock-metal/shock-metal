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
import java.util.Random;

public class ItemRecipeTool extends ItemBase {
    public ItemRecipeTool() {
        super(new Item.Properties()
                .group(ShockMetalMain.SHOCKMETALTAB)
                .maxDamage(100)
                .setNoRepair()
        );
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack damagedItem = new ItemStack(itemStack.getItem(), itemStack.getMaxStackSize(), null);
        damagedItem.attemptDamageItem(itemStack.getDamage() + 1, new Random(), null);
        return damagedItem.getDamage() >= itemStack.getMaxDamage() ? ItemStack.EMPTY : damagedItem;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int damage = 100 - stack.getDamage();
        tooltip.add(
                new StringTextComponent(String.format("%s/%s", damage, stack.getMaxDamage()))
                .mergeStyle(TextFormatting.GRAY));
    }
}
