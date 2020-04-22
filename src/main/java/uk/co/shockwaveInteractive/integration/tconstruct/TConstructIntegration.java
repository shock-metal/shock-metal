package uk.co.shockwaveInteractive.integration.tconstruct;

import java.awt.Color;
import java.util.Locale;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.client.MaterialRenderInfo;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.BowMaterialStats;
import slimeknights.tconstruct.library.materials.ExtraMaterialStats;
import slimeknights.tconstruct.library.materials.HandleMaterialStats;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.block.BlockMolten;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.integration.tconstruct.TConstructIntegration.FluidStateMapper;
import uk.co.shockwaveInteractive.integration.tconstruct.traits.TraitShockMetal;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

/*
 * Used for Tinkers' Construct integration
 * 
 * @author HarmanU / vNemesis_HD
 * 
 */
public class TConstructIntegration
{	
	private static StringBuilder sb = new StringBuilder();
	
	//Mats
	public static Material matShockmetal;
	
	public static void runPreInit()
	{		
		// set up stuff
		setUp();
		
		// register material integrations
		registerMI();
		
		//add to Smeltery
		//addSmelteryRecipe(shockMetal.getName(), "ore", "Shockmetal", 2, true);
		
	}
	
	public static void runInit() {	}
	
	
	private static void setUp()
	{
		sb.append("------------------------------ Start ------------------------------");
		
		// Register fluids
		Fluid fluidShockMetal = addMoltenFluid("fluidshockmetal", new Color(101, 93, 158), 1600, EnumRarity.EPIC);
		sb.append("\n\t\t\t\t| Added " + fluidShockMetal.getName() + " to the fluid registry");
		
		// Create Materials
		matShockmetal = createMaterial("ShockMetal", fluidShockMetal, "ShockMetal", 1600);
		matShockmetal.addItem("ingotShockmetal", 1, Material.VALUE_Ingot);
		matShockmetal.addTrait(TraitShockMetal.traitShockMetal);
		matShockmetal.setCraftable(false).setCastable(true);
		matShockmetal.setRepresentativeItem("ingotShockmetal");
		matShockmetal.setFluid(fluidShockMetal);
		matShockmetal.setRenderInfo(new MaterialRenderInfo.Metal(5064311, 0.5f, 1.0f, 0.2f));
		sb.append("\n\t\t\t\t| Created " + matShockmetal.getLocalizedName() + " material");
		
		
		// Register materials
		TinkerRegistry.addMaterialStats(matShockmetal, new HeadMaterialStats(1000, 10.0f, (float) ShockMetalConfiguration.materialDamage, HarvestLevels.COBALT),
													   new HandleMaterialStats(1.1f, 350),
													   new ExtraMaterialStats(250),
													   new BowMaterialStats(0.3f, 1.60f, 5f));
		
		// Register fluids
		if (ShockMetalMain.preIntEvent.getSide().isClient()) 
		{
			registerFluidModels(fluidShockMetal);
		}
		
		sb.append("\n\t\t\t\t------------------------------  End  ------------------------------");
		ShockMetalMain.logger.info(sb.toString());
	}
	
	// Register Material Integrations
	private static void registerMI()
	{
		MaterialIntegration matIntergration = new MaterialIntegration(matShockmetal, matShockmetal.getFluid(), "Shockmetal").toolforge();
		
		TinkerRegistry.integrate(matIntergration).preInit();
	}
	
	// ---------------------------------------- Helper Methods ----------------------------------------
	
	/**
	 * Adds a new Molten fluid
	 * @param name Name of fluid
	 * @param colour Colour of the fluid
	 * @param temp Temperature of the fluid
	 * @param rarity Rarity of the fluid
	 * @return Returns the new fluid object
	 */
	public static Fluid addMoltenFluid(String name, Color colour, int temp, EnumRarity rarity)
	{
		FluidMolten newFluid = new FluidMolten(name, temp, new ResourceLocation("tconstruct:blocks/fluids/molten_metal"), new ResourceLocation("tconstruct:blocks/fluids/molten_metal_flow"));
		newFluid.setColor(colour);
		newFluid.setTemperature(temp);
		newFluid.setRarity(rarity);
		newFluid.setUnlocalizedName(newFluid.getName().toLowerCase(Locale.UK));
		FluidRegistry.registerFluid(newFluid); // fluid has to be registered
		FluidRegistry.addBucketForFluid(newFluid);
		
		initFluidMetal(newFluid);
		return newFluid;
	}
	
	/**
	 * Creates the Material to be added to the Smeltery
	 * 
	 * @param name Name for material
	 * @param fluid Fluid for material
	 * @param oreName Name of the ore dictionary
	 * @param temp temperature for the material
	 * @return Returns a new Material object
	 */
	private static Material createMaterial(String name, Fluid fluid, String oreName, int temp) 
	{
		Material material = new Material(name, TextFormatting.LIGHT_PURPLE);
		
		return material;
	}
	
	// -------------------------------- Helper Methods: Fluid Setup --------------------------------
	
	public static void initFluidMetal(Fluid fluid) 
	{
		setUpMoltenBlock(fluid);
		registerFluidModels(fluid);
	}
	
	private static Block setUpMoltenBlock(Fluid fluid)
	{
		Block block = new BlockMolten(fluid);
		return setUpBlock(block, "molten_" + fluid.getName());
	}
	
	public static <T extends Block> T setUpBlock(T block, String name) 
	{
		block.setTranslationKey(name);
		block.setRegistryName(name);
		
		Item IB = new ItemBlock(block).setRegistryName(block.getRegistryName());
		
		ForgeRegistries.BLOCKS.register(block);
		ForgeRegistries.ITEMS.register(IB);
		return block;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerFluidModels(Fluid fluid) {
		if (fluid == null) {
			return;
		}

		Block block = fluid.getBlock();
		if (block != null) {
			Item item = Item.getItemFromBlock(block);
			FluidStateMapper mapper = new FluidStateMapper(fluid);

			// item-model
			if (item != null) {
				ModelBakery.registerItemVariants(item);
				ModelLoader.setCustomMeshDefinition(item, mapper);
			}
			// block-model
			ModelLoader.setCustomStateMapper(block, mapper);
		}
	}

	@SideOnly(Side.CLIENT)
	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

		public final Fluid fluid;
		public final ModelResourceLocation location;

		public FluidStateMapper(Fluid fluid) {
			this.fluid = fluid;

			// have each block hold its fluid per nbt? hm
			this.location = new ModelResourceLocation(new ResourceLocation("shockm", "fluid_block"),
					fluid.getName());
		}

		@Nonnull
		@Override
		protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
			return location;
		}

		@Nonnull
		@Override
		public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
			return location;
		}
	}
	
	
	
	// ---------------------------------------- Tinkers' IMC ----------------------------------------
	
	/**
	 * Adds a smeltery recipe to the Tinkers' smeltery via IMC
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
