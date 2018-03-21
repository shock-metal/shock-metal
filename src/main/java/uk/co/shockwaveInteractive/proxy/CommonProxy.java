package uk.co.shockwaveInteractive.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import uk.co.shockwaveInteractive.integration.tconstruct.TConstructIntegration;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;

public class CommonProxy 
{
	
	public void registerItemRenderer(Item item, int meta, String id) {}
	
	public void registerVarientRenderer(Item item, int meta,String filename, String id) {}
	
	public void registerFluidModels(Fluid fluid) {};
	
	public void preInit() {}
	

}
