package uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;

public class SlotMolecularManipulatorFuel extends Slot
{
	public SlotMolecularManipulatorFuel(IInventory inventory, int index, int x, int y) 
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return TileEntityMolecularManipulator.isItemFuel(stack);
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		return super.getItemStackLimit(stack);
	}
}
