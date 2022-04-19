package uk.co.shockwaveinteractive.objects.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        int damage = stack.getMaxDamage() - stack.getDamageValue();
        tooltip.add(
                new TextComponent(String.format("%s/%s", damage, stack.getMaxDamage()))
                .withStyle(ChatFormatting.GRAY));
    }
}
