package vnemesis.shockmetal.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemRecipeTool extends ItemBase {

    public ItemRecipeTool(int maxUses) {
        super(new Item.Properties()
                .durability(maxUses)
                .setNoRepair()
        );
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        ItemStack damagedItem = new ItemStack(itemStack.getItemHolder(), itemStack.getMaxStackSize(), null);
        damagedItem.setDamageValue(itemStack.getDamageValue() + 1);
        return damagedItem.getDamageValue() >= itemStack.getMaxDamage() ? ItemStack.EMPTY : damagedItem;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        int damage = stack.getMaxDamage() - stack.getDamageValue();
        tooltipComponents.add(
                Component.literal(String.format("%s/%s", damage, stack.getMaxDamage()))
                        .withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
