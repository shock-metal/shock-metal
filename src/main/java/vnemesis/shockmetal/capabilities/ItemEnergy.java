package vnemesis.shockmetal.capabilities;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.energy.EnergyStorage;

public class ItemEnergy extends EnergyStorage
{
    private static final String KEY_ENERGY = "shockmetal.energy";

    private final ItemStack stack;

    public ItemEnergy(ItemStack stack, int capacity) {
        super(capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.stack = stack;
        // Load previously-persisted energy from the item's custom data component
        this.energy = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
                .copyTag().getInt(KEY_ENERGY);
    }

    @Override
    public int extractEnergy(int extract, boolean simulate) {
        int amount = super.extractEnergy(extract, simulate);
        if (!simulate) persist();
        return amount;
    }

    @Override
    public int receiveEnergy(int receive, boolean simulate) {
        int amount = super.receiveEnergy(receive, simulate);
        if (!simulate) persist();
        return amount;
    }

    /** Write the current energy value back into the item stack's CUSTOM_DATA component. */
    private void persist() {
        CompoundTag tag = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
        tag.putInt(KEY_ENERGY, this.energy);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
}
