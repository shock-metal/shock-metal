 package uk.co.shockwaveInteractive.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import uk.co.shockwaveInteractive.objects.blocks.BlockBase;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.BlockMolecularManipulator;

/*
 * Initialises Blocks
 * 
 * @author HarmanU / vNemesis_HD
 */
public class BlockInit 
{
	public static List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block BLOCK_SHOCKMETAL = new BlockBase("block_shockmetal", Material.IRON, "pickaxe", 3, 10);
	
	// Ore, location, effective tool, harvest level ( 3 = diamond)
	public static final Block ORE_NETHER = new BlockOres("ore_nether", "nether", "pickaxe", 3);
	
	public static final Block MOLECULAR_MANIPULATOR = new BlockMolecularManipulator("molecular_manipulator", Material.IRON, "pickaxe");
	
}
