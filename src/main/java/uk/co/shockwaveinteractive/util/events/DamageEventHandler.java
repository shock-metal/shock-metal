package uk.co.shockwaveinteractive.util.events;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.network.NetworkDirection;
import uk.co.shockwaveinteractive.common.network.PacketHandler;
import uk.co.shockwaveinteractive.common.network.packets.PacketShieldDamage;
import uk.co.shockwaveinteractive.integration.curios.CuriosProxy;
import uk.co.shockwaveinteractive.objects.items.energytools.ItemShieldModule;

import java.util.function.Predicate;

public class DamageEventHandler {

    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getAmount() <= 0 || !entity.isAlive()) {
            return;
        }

        if (event.getEntity() instanceof Player serverPlayer) {
            boolean hasShieldModule = false;

            Predicate<ItemStack> itemPredicate = stack -> {
                if (stack.getItem() instanceof ItemShieldModule shieldModule) {
                    return shieldModule.isActive(stack);
                }
                return false;
            };

            // Search for the active shield in the player's inventory
            ItemStack activeItemStack = ItemStack.EMPTY;

            for (int i = 0; i < serverPlayer.getInventory().getContainerSize(); i++) {
                ItemStack stackInSlot = serverPlayer.getInventory().getItem(i);
                if (itemPredicate.test(stackInSlot)) {
                    activeItemStack = stackInSlot;
                    hasShieldModule = true;
                    break; // Stop searching once the active item is found
                }
            }


            if (!hasShieldModule) {
                LazyOptional<IItemHandlerModifiable> wornItems = CuriosProxy.getAllWorn(serverPlayer);
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

                System.out.printf("Has Cooldown: %s%n", serverPlayer.getCooldowns().isOnCooldown(shieldItem));

                if(serverPlayer.getCooldowns().isOnCooldown(shieldItem)) {
                    System.out.println("Re-apply Cooldown");
                    shieldItem.ApplyCooldown(serverPlayer);
                    shieldItem.resetLastDamageTimeToCurrent(serverPlayer.level);
                } else {
                    System.out.println("Damage Shield");
                    float damageAmount = event.getAmount();
                    int effectiveDamage = Math.round(damageAmount);
                    int shieldDurability = shieldItem.getProtectableDamage(activeItemStack);

                    // Calculate how much damage the shield can absorb
                    int shieldDamage = Math.min(shieldDurability, effectiveDamage);

                    // Damage the shield and reduce incoming damage
                    PacketHandler.HANDLER.sendTo(new PacketShieldDamage(activeItemStack, shieldDamage), serverPlayer.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
                    event.setAmount(damageAmount - shieldDamage);
                }
            }
        }
    }
}

