package uk.co.shockwaveInteractive.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTool;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;

public class BlockBase extends Block implements IHasModel 
{

	public BlockBase(String name, Material material, String effectiveTool, int miningLevel, int hardness) 
	{
		super(material);
		setHarvestLevel(effectiveTool, miningLevel);
		setHardness(hardness);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		
		
	}
	
	@Override
	public void registerModels() {
		
		ShockMetalMain.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		
	}

}
