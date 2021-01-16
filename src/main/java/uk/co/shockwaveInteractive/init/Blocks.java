package uk.co.shockwaveinteractive.init;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import uk.co.shockwaveinteractive.objects.blocks.ShockmetalBlock;
import uk.co.shockwaveinteractive.objects.blocks.ShockmetalNetherOreBlock;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import static uk.co.shockwaveinteractive.util.reference.IDReference.*;

/*
  * Initialises Blocks
  *
  * @author HarmanU / vNemesis_HD
  */
 public class Blocks
 {
     public static final DeferredRegister<Block> REGISTRY_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MainReference.MODID);

     public static final RegistryObject<Block> SHOCKMETAL_BLOCK = REGISTRY_BLOCKS.register(ID_SHOCKMETAL_BLOCK, ShockmetalBlock::new);
     public static final RegistryObject<Block> SHOCKMETAL_NETHER_ORE_BLOCK = REGISTRY_BLOCKS.register(ID_SHOCKMETAL_NETHER_ORE_BLOCK, ShockmetalNetherOreBlock::new);
 }
