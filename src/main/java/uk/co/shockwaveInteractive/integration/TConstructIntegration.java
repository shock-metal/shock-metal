package uk.co.shockwaveInteractive.integration;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.util.interfaces.CanIntegrate;

/*
 * Used for Tinkers' Construct integration
 * 
 * @author HarmanU / vNemesis_HD
 */
public class TConstructIntegration
{
	
	public static void runIntegration()
	{
		if(!Loader.isModLoaded("tconstruct"))
		{
			ShockMetalMain.logger.info("Tinkers' Construct is not loaded, Integration not applied");
			return;
		}
		
		ShockMetalMain.logger.info("Tinkers' Construct is loaded, Applying Intergration");
		
		// create fluid.
		// You don't need to add textures for the fluid, just create a Fluid Class that overwrites getColor
		// and pass the following as still and flowing ResourceLocation:
		// still:  "tconstruct:blocks/fluids/molten_metal"
		// flowing: "tconstruct:blocks/fluids/molten_metal_flow"
		Fluid myFluid = new Fluid("fluid_shockmetal", new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
		myFluid.setColor(new Color(47, 43, 73));
		FluidRegistry.registerFluid(myFluid); // fluid has to be registered
		FluidRegistry.addBucketForFluid(myFluid); // add a bucket for the fluid

		// add block for fluid (if desired)
		Block fluidBlock = new BlockFluidClassic(myFluid, Material.LAVA);
		// <register block regularly>

		// create NBT for the IMC
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("fluid", myFluid.getName()); // name of the fluid
		tag.setString("ore", "Shockmetal"); // ore-suffix: ingotFoo, blockFoo, oreFoo,...
		tag.setString("ingot", "Shockmetal");
		tag.setString("block", "Shockmetal");
		tag.setInteger("time", 30);
		tag.setInteger("Amount", 2);
		tag.setBoolean("toolforge", true); // if set to true, blockFoo can be used to build a toolforge
		//tag.setTag("alloy", alloysTagList); // you can also send an alloy with the registration (see below)

		// send the NBT to TCon
		FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tag);
	}

}
