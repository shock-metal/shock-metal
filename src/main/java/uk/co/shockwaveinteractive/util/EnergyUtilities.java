package uk.co.shockwaveinteractive.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import uk.co.shockwaveinteractive.objects.items.ItemEnergyBase;

public class EnergyUtilities {

    private static final IEnergyStorage EMPTY_ENERGY_STORAGE = new EnergyStorage(0);

    public static IEnergyStorage getEnergyStorage(ItemStack stack) {
        if (ForgeCapabilities.ENERGY == null)
            return EMPTY_ENERGY_STORAGE;

        return stack.getCapability(ForgeCapabilities.ENERGY).orElse(EMPTY_ENERGY_STORAGE);
    }

    public static int getEnergyStored(ItemStack stack) {
        if (stack.getItem() instanceof ItemEnergyBase itemEnergyBase) {
            var energy = stack.getCapability(ForgeCapabilities.ENERGY);
            return energy.isPresent() ? energy.map(IEnergyStorage::getEnergyStored).get() : 0;
        }
        return 0;
    }

    public static int getEnergyCost(ItemStack stack) {
        if (stack.getItem() instanceof ItemEnergyBase itemEnergyBase) {
            return itemEnergyBase.getEnergyCost();
        }
        return -1;
    }

    public static boolean hasEnoughEnergy(ItemStack stack, int modifier) {
        int energyStored = getEnergyStored(stack);
        int energyCost = getEnergyCost(stack) * modifier;
        return energyCost <= energyStored;
    }

    public static boolean hasEnoughEnergy(ItemStack stack) {
        return hasEnoughEnergy(stack, 1);
    }

    public static void useEnergy(ItemStack stack, int modifier) {
        if (stack.getItem() instanceof ItemEnergyBase itemEnergyBase) {
            var energy = stack.getCapability(ForgeCapabilities.ENERGY);
            if (!energy.isPresent()) return;
            int cost = itemEnergyBase.getEnergyCost() * modifier;
            energy.map(e -> e.extractEnergy(cost, false));
        }
    }

    public static void useEnergy(ItemStack stack) {
        useEnergy(stack, 1);
    }
}
