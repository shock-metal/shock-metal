package vnemesis.shockmetal.capabilities;

import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.energy.EnergyStorage;

public class ItemEnergy extends EnergyStorage
{
    private final ItemStack stack;

    public ItemEnergy(ItemStack stack, int capacity) {
        super(capacity, Integer.MAX_VALUE, Integer.MAX_VALUE);

        this.stack = stack;
//        this.energy = stack.getTags().findAny().isPresent()
//                && stack.getTags().toList().contains("energy")
//                ? stack.is(TagsProvider.TagLookup("energy")) : 0;
    }
//
//    @Override
//    public int extractEnergy(int extract, boolean simulate) {
//        int amount = super.extractEnergy(extract, simulate);
//        if (!simulate)
//            stack.getOrCreateTag().putInt("energy", this.energy);
//
//        return amount;
//    }
//
//    @Override
//    public int receiveEnergy(int receive, boolean simulate) {
//        int amount = super.receiveEnergy(receive, simulate);
//        if (!simulate)
//            stack.getOrCreateTag().putInt("energy", this.energy);
//
//        return amount;
//    }
}
