package vnemesis.shockmetal.item.resources;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import vnemesis.shockmetal.item.ItemBase;

import java.util.List;

public class ItemIngotShockmetal extends ItemBase
{

    public ItemIngotShockmetal() {
        super(new Item.Properties().fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        tooltipComponents.add(Component.translatable("info.shockmetal.gui.shockmetal.ingot").withStyle(ChatFormatting.DARK_PURPLE));
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

}
