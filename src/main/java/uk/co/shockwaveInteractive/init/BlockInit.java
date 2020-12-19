package uk.co.shockwaveinteractive.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.shockwaveinteractive.objects.blocks.ShockmetalBlock;
import uk.co.shockwaveinteractive.objects.blocks.ShockmetalNetherOreBlock;
import uk.co.shockwaveinteractive.util.Reference;

 /*
  * Initialises Blocks
  *
  * @author HarmanU / vNemesis_HD
  */
 public class BlockInit
 {
     public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);


     public static final RegistryObject<Block> SHOCKMETAL_BLOCK = BLOCKS.register("shockmetal_block", ShockmetalBlock::new);
     public static final RegistryObject<Block> SHOCKMETAL_NETHER_ORE_BLOCK = BLOCKS.register("shockmetal_nether_ore_block", ShockmetalNetherOreBlock::new);

//     public static final Block MOLECULAR_MANIPULATOR = new BlockMolecularManipulator("molecular_manipulator", Material.IRON, "pickaxe");

 }
