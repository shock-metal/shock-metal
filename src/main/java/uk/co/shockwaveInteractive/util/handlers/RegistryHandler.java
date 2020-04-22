package uk.co.shockwaveInteractive.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.Reference;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;
import uk.co.shockwaveInteractive.world.gen.WorldGenCustomOres;

@EventBusSubscriber
public class RegistryHandler 
{
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
	}
	
	@SubscribeEvent
	public static void onModel(ModelRegistryEvent event)
	{
		for (Item item : ItemInit.ITEMS)
		{
			if (item instanceof IHasModel)
			{
				( (IHasModel)item ).registerModels();
			}
		}
		
		for (Block block : BlockInit.BLOCKS)
		{
			if (block instanceof IHasModel)
			{
				( (IHasModel)block ).registerModels();
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void initRegistries()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ShockMetalMain.instance, new GUIHandler());
	}
	
	public static void otherRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		
		GameRegistry.addSmelting(new ItemStack(BlockInit.ORE_NETHER ,1 ,0), new ItemStack(ItemInit.INGOT_SHOCKMETAL), 1.0f);
		GameRegistry.addSmelting(new ItemStack(ItemInit.DUST_SHOCKMETAL ,1 ,0), new ItemStack(ItemInit.INGOT_SHOCKMETAL), 1.0f);
	}
}
