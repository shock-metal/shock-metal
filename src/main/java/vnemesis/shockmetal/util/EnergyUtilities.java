package vnemesis.shockmetal.util;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import vnemesis.shockmetal.item.ItemEnergyBase;

public class EnergyUtilities {

    private static final IEnergyStorage EMPTY_ENERGY_STORAGE = new EnergyStorage(0);

    public static IEnergyStorage getEnergyStorage(ItemStack stack) {
        if (stack.getCapability(Capabilities.EnergyStorage.ITEM) == null)
            return EMPTY_ENERGY_STORAGE;

        return stack.getCapability(Capabilities.EnergyStorage.ITEM);
    }

    public static int getEnergyStored(ItemStack stack) {
        if (stack.getItem() instanceof ItemEnergyBase itemEnergyBase) {
            var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            return energy != null ? energy.getEnergyStored() : 0;
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
            var energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
            if (energy == null) return;
            int cost = itemEnergyBase.getEnergyCost() * modifier;
            energy.extractEnergy(cost, false);
        }
    }

    public static void useEnergy(ItemStack stack) {
        useEnergy(stack, 1);
    }
}
