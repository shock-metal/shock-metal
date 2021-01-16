package uk.co.shockwaveinteractive.util;

import net.minecraft.item.ItemStack;

public class Helpers {

    public static ItemStack cloneStack(ItemStack stack, int stackSize) {

        if (stack.isEmpty() || stackSize <= 0) {
            return ItemStack.EMPTY;
        }
        ItemStack retStack = stack.copy();
        retStack.setCount(stackSize);

        return retStack;
    }
}
