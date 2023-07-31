package uk.co.shockwaveinteractive.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;

public class InventoryUtilities {
    public static boolean canInsertStack(IItemHandler handler, @Nonnull ItemStack stack) {
        final ItemStack toInsert = ItemHandlerHelper.insertItemStacked(handler, stack, true);
        return toInsert.getCount() < stack.getCount();

    }
}
