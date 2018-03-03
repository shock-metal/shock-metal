package uk.co.shockwaveInteractive.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import uk.co.shockwaveInteractive.objects.blocks.BlockBase;

public class BlockInit 
{
	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block BLOCK_SHOCKMETAL = new BlockBase("block_shockmetal", Material.IRON, "Pickaxe", 3, 10);
	
	public static final Block ORE_NETHER = new BlockOres("ore_nether", "nether", "Pickaxe", 3);
	
}
