package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.item.Item;
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
    public ItemRecipeTool(int maxUses) {
        super(new Item.Properties()
                .tab(ShockMetalMain.SHOCKMETALTAB)
                .durability(maxUses)
                .setNoRepair()
        );
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack damagedItem = new ItemStack(itemStack.getItem(), itemStack.getMaxStackSize(), null);
        damagedItem.hurt(itemStack.getDamageValue() + 1, new Random(), null);
        return damagedItem.getDamageValue() >= itemStack.getMaxDamage() ? ItemStack.EMPTY : damagedItem;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int damage = stack.getMaxDamage() - stack.getDamageValue();
        tooltip.add(
                new StringTextComponent(String.format("%s/%s", damage, stack.getMaxDamage()))
                .withStyle(TextFormatting.GRAY));
    }
}
