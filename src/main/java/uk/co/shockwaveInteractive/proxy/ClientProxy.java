package uk.co.shockwaveInteractive.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Optional;
import uk.co.shockwaveInteractive.util.Reference;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

public class ClientProxy extends CommonProxy 
{
	@Override
	public void registerItemRenderer(Item item, int meta, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	@Override
	public void registerVarientRenderer(Item item, int meta,String filename, String id) {
		
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, filename), id));
	}
	
	public void preInit()
	{
		super.preInit();
		ShockMetalConfiguration.clientPreInit();  // used to set up an event handler for the GUI so that the altered values are saved back to disk.
	}
}
