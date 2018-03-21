package uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.ItemInit;

public class MolecularManipulatorRecipes 
{
	private static final MolecularManipulatorRecipes INSTANCE = new MolecularManipulatorRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static MolecularManipulatorRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private MolecularManipulatorRecipes() 
	{
		addMolecularManipulatorRecipe(new ItemStack(ItemInit.FRAME_DIAMOND), new ItemStack(ItemInit.INGOT_SHOCKMETAL, 2), new ItemStack(Items.DIAMOND, 2), 4);
	}

	
	public void addMolecularManipulatorRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) 
	{
		if(getMolecularManipulatorResult(input1, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getMolecularManipulatorResult(ItemStack input1, ItemStack input2) 
	{
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) 
		{
			if(this.compareItemStacks(input1, (ItemStack)entry.getKey())) 
			{
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
				{
					if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) 
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() 
	{
		return this.smeltingList;
	}
	
	public float getMolecularManipulatorExperience(ItemStack stack)
	{
		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}
