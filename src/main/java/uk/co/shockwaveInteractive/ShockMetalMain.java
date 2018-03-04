package uk.co.shockwaveInteractive;

import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.integration.TConstructIntegration;
import uk.co.shockwaveInteractive.integration.ThermalExpansionIntergration;
import uk.co.shockwaveInteractive.proxy.CommonProxy;
import uk.co.shockwaveInteractive.tabs.shockMetalTab;
import uk.co.shockwaveInteractive.util.Reference;
import uk.co.shockwaveInteractive.util.handlers.RegistryHandler;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ShockMetalMain 
{
	
	public static Logger logger;

	@Instance
	public static ShockMetalMain instance;
	
	public static final CreativeTabs shockmetaltab = new shockMetalTab("shockmetaltab");
	
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		RegistryHandler.otherRegistries();
		
		TConstructIntegration.runIntegration();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		OreDictionary.registerOre("oreShockmetal", new ItemStack(BlockInit.ORE_NETHER ,1 ,0));
		OreDictionary.registerOre("ingotShockmetal", ItemInit.INGOT_SHOCKMETAL);
		OreDictionary.registerOre("blockShockmetal", BlockInit.BLOCK_SHOCKMETAL);
		
		ThermalExpansionIntergration.runIntegration();
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
}
