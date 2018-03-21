package uk.co.shockwaveInteractive.integration.thermalexpansion;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import scala.reflect.internal.Trees.This;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;


/*
 * Used for integration with Thermal Expansion
 * 
 * @author HarmanU / vNemesis_HD
 * 
 * Thanks to King Lemming for initial file structure
 */
public class ThermalExpansionIntegration
{
	static final String MOD_ID = "thermalexpansion";
	
	/* IMC STRINGS */
	static final String ENERGY = "energy";
	static final String FLUID = "fluid";
	static final String FLUID_NAME = "fluidName";

	static final String INPUT = "input";
	static final String OUTPUT = "output";
	static final String PRIMARY_INPUT = "primaryInput";
	static final String SECONDARY_INPUT = "secondaryInput";
	static final String PRIMARY_OUTPUT = "primaryOutput";
	static final String SECONDARY_OUTPUT = "secondaryOutput";
	static final String SECONDARY_CHANCE = "secondaryChance";

	public static final String ADD_FURNACE_RECIPE = "addfurnacerecipe";
	
	
	public static void runInit()
	{
		addFurnaceRecipe(15000, new ItemStack(BlockInit.ORE_NETHER, 1, 0), 	new ItemStack(ItemInit.INGOT_SHOCKMETAL));
	}
	
	/* FURNACE */
	public static void addFurnaceRecipe(int energy, ItemStack input, ItemStack output) {

		if (input.isEmpty() || output.isEmpty()) {
			return;
		}
		NBTTagCompound toSend = new NBTTagCompound();

		toSend.setInteger(ENERGY, energy);
		toSend.setTag(INPUT, new NBTTagCompound());
		toSend.setTag(OUTPUT, new NBTTagCompound());

		input.writeToNBT(toSend.getCompoundTag(INPUT));
		output.writeToNBT(toSend.getCompoundTag(OUTPUT));
		FMLInterModComms.sendMessage(MOD_ID, ADD_FURNACE_RECIPE, toSend);
	}
	
}
