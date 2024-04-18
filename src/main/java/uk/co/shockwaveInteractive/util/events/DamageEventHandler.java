package uk.co.shockwaveinteractive.util.events;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import uk.co.shockwaveinteractive.integration.curios.CuriosProxy;
import uk.co.shockwaveinteractive.objects.items.ItemShieldModule;

import java.util.function.Predicate;

public class DamageEventHandler {

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            boolean hasShieldModule = false;

            Predicate<ItemStack> itemPredicate = stack -> {
                if (stack.getItem() instanceof ItemShieldModule) {
                    ItemShieldModule shieldModule = (ItemShieldModule) stack.getItem();
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
                            if (equip.getItem() instanceof ItemShieldModule) {
                                ItemShieldModule shieldModule = (ItemShieldModule) equip.getItem();
                                if(shieldModule.isActive(equip)) {
                                    activeItemStack = equip;
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            if (!activeItemStack.isEmpty()) {
                ItemShieldModule shieldItem = (ItemShieldModule) activeItemStack.getItem();

                if(player.getCooldowns().isOnCooldown(shieldItem)) {
                    shieldItem.ApplyCooldown(player);
                } else {
                    // Calculate shield damage and damage reduction
                    int shieldDurability = shieldItem.getMaxDamage(activeItemStack) - shieldItem.getDamage(activeItemStack);
                    float damageAmount = event.getAmount();

                    // Determine the effective damage to be applied
                    int effectiveDamage = Math.round(damageAmount);

                    // Calculate how much damage the shield can absorb
                    int shieldDamage = Math.min(shieldDurability, effectiveDamage);

                    // Damage the shield and reduce incoming damage
                    shieldItem.hurtActiveShield(activeItemStack, shieldDamage, player);
                    event.setAmount(damageAmount - shieldDamage);

                    // Display shield integrity message to the player
                    int remainingDurability = shieldItem.getMaxDamage(activeItemStack) - shieldItem.getDamage(activeItemStack);
                    //player.displayClientMessage(Component.literal(String.format("Shield Integrity: %s/%s", remainingDurability, shieldItem.getMaxDamage())), false);
                }
            }
        }
    }
}

