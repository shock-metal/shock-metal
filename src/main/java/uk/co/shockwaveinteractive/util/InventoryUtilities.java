package uk.co.shockwaveinteractive.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import uk.co.shockwaveinteractive.integration.curios.CuriosProxy;
import uk.co.shockwaveinteractive.objects.items.ItemShieldModule;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class InventoryUtilities {
    public static boolean canInsertStack(IItemHandler handler, @Nonnull ItemStack stack) {
        final ItemStack toInsert = ItemHandlerHelper.insertItemStacked(handler, stack, true);
        return toInsert.getCount() < stack.getCount();

    }

    public static ItemStack getActiveShieldInInventory(Player player) {
        boolean hasShieldModule = false;

        Predicate<ItemStack> itemPredicate = stack -> {
            if (stack.getItem() instanceof ItemShieldModule shieldModule) {
                return shieldModule.isActive(stack);
            }
            return false;
        };

        // Search for the active shield in the player's inventory
        ItemStack activeItemStack = ItemStack.EMPTY;

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stackInSlot = player.getInventory().getItem(i);
            if (itemPredicate.test(stackInSlot)) {
                activeItemStack = stackInSlot;
                hasShieldModule = true;
                break; // Stop searching once the active item is found
            }
        }


        if (!hasShieldModule) {
            LazyOptional<IItemHandlerModifiable> wornItems = CuriosProxy.getAllWorn(player);
            if (wornItems.isPresent()) {
                IItemHandlerModifiable curiosHandler = wornItems.orElse(null);
                if (curiosHandler != null) {
                    for (int i = 0; i < curiosHandler.getSlots(); i++) {
                        ItemStack equip = curiosHandler.getStackInSlot(i);
                        if (equip.getItem() instanceof ItemShieldModule shieldModule) {
                            if(shieldModule.isActive(equip)) {
                                activeItemStack = equip;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return activeItemStack;
    }
}
