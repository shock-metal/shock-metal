package uk.co.shockwaveInteractive.integration;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.EnumRarity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

/*
 * Used for Tinkers' Construct integration
 * 
 * @author HarmanU / vNemesis_HD
 */
public class TConstructIntegration
{
	
	public static void runIntegration()
	{
		if(!Loader.isModLoaded("tconstruct") || !ShockMetalConfiguration.tinkersIntegration)
		{
			ShockMetalMain.logger.info("Tinkers' Construct is not loaded, Integration not applied");
			ShockMetalMain.logger.trace("Tinkers' Construct is not loaded, Integration not applied");
			return;
		}
		
		ShockMetalMain.logger.info("Tinkers' Construct is loaded, Applying Intergration");
		ShockMetalMain.logger.trace("Tinkers' Construct is loaded, Applying Intergration");
		
		// create fluid.
		// You don't need to add textures for the fluid, just create a Fluid Class that overwrites getColor
		// and pass the following as still and flowing ResourceLocation:
		// still:  "tconstruct:blocks/fluids/molten_metal"
		// flowing: "tconstruct:blocks/fluids/molten_metal_flow"
		Fluid myFluid = new Fluid("fluid_shockmetal", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
		myFluid.setColor(new Color(47, 43, 73));
		myFluid.setTemperature(1600);
		myFluid.setRarity(EnumRarity.EPIC);
		FluidRegistry.registerFluid(myFluid); // fluid has to be registered
		FluidRegistry.addBucketForFluid(myFluid); // add a bucket for the fluid

		// add block for fluid (if desired)
		Block fluidBlock = new BlockFluidClassic(myFluid, Material.LAVA);
		
		// <register block regularly>
		addSmelteryRecipe(myFluid.getName(), "ore", "Shockmetal", 2, true);
		
	}
	
	
	/**
	 * Adds a smeltery recipe to the Tinkers' smeltery
	 * 
	 * @param fluid - fluid that is generated
	 * @param oreDictPrefix - ore, ingot, block
	 * @param oreDictItem - item. block. etc
	 * @param time - time to smelt in seconds
	 * @param amount - Amount the recipe produces in ingots
	 * @param toolForge - can the metal be used to build a tool forge
	 */
	public static void addSmelteryRecipe(String fluidName, String oreDictPrefix, String oreDictItem, int amount, boolean toolForge)
	{
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", fluidName); // name of the fluid
		tag.setString(oreDictPrefix, oreDictItem); // ore-suffix: ingotFoo, blockFoo, oreFoo,...
		tag.setInteger("Amount", amount);
		tag.setBoolean("toolforge", toolForge); // if set to true, blockFoo can be used to build a toolforge
		//tag.setTag("alloy", alloysTagList); // you can also send an alloy with the registration (see below)

		// send the NBT to TCon
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
	}

}
