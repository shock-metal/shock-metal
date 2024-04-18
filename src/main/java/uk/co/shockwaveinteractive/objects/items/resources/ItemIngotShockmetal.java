package uk.co.shockwaveinteractive.objects.items.resources;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import uk.co.shockwaveinteractive.ShockMetalMain;
import uk.co.shockwaveinteractive.objects.items.ItemBase;

import javax.annotation.Nullable;
import java.util.List;

import static uk.co.shockwaveinteractive.util.reference.MainReference.TRANSLATION_INFO_PREFIX;

public class ItemIngotShockmetal extends ItemBase {

    public ItemIngotShockmetal() {
        super(new Item.Properties().tab(ShockMetalMain.SHOCKMETALTAB).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        tooltip.add(Component.translatable(TRANSLATION_INFO_PREFIX + "shockmetal.ingot").withStyle(ChatFormatting.DARK_PURPLE));
    }

}
