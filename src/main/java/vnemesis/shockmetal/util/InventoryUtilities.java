package vnemesis.shockmetal.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import top.theillusivec4.curios.api.CuriosApi;
import vnemesis.shockmetal.item.shield.ItemShieldModule;

import javax.annotation.Nonnull;

public class InventoryUtilities {

    public static boolean canInsertStack(IItemHandler handler, @Nonnull ItemStack stack) {
        final ItemStack toInsert = ItemHandlerHelper.insertItemStacked(handler, stack, true);
        return toInsert.getCount() < stack.getCount();
    }

    /**
     * Returns the first active {@link ItemShieldModule} stack found in the player's main inventory
     * or, if Curios is loaded, their curios slots. Returns {@link ItemStack#EMPTY} if none found.
     */
    public static ItemStack getActiveShieldInInventory(Player player) {
        // Search main inventory first
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot.getItem() instanceof ItemShieldModule shieldModule && shieldModule.isActive(slot)) {
                return slot;
            }
        }

        // Search Curios slots (soft dependency)
        if (ModList.get().isLoaded("curios")) {
            ItemStack[] found = {ItemStack.EMPTY};
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                var curios = handler.getEquippedCurios();
                for (int i = 0; i < curios.getSlots(); i++) {
                    ItemStack slot = curios.getStackInSlot(i);
                    if (slot.getItem() instanceof ItemShieldModule shieldModule && shieldModule.isActive(slot)) {
                        found[0] = slot;
                        break;
                    }
                }
            });
            if (!found[0].isEmpty()) return found[0];
        }

        return ItemStack.EMPTY;
    }
}
