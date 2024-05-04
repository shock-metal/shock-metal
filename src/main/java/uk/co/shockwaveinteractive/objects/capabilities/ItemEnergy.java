package uk.co.shockwaveinteractive.objects.capabilities;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class ItemEnergy extends EnergyStorage {
    private final ItemStack stack;

    public ItemEnergy(ItemStack stack, int capacity) {
        super(capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.stack = stack;
        this.energy = stack.hasTag() && stack.getTag().contains("energy") ? stack.getTag().getInt("energy") : 0;
    }

    @Override
    public int extractEnergy(int extract, boolean simulate) {
        int amount = super.extractEnergy(extract, simulate);
        if (!simulate)
            stack.getOrCreateTag().putInt("energy", this.energy);

        return amount;
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate) {
        int amount = super.receiveEnergy(receive, simulate);
        if (!simulate)
            stack.getOrCreateTag().putInt("energy", this.energy);

        return amount;
    }
}
