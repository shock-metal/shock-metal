package vnemesis.shockmetal.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import vnemesis.shockmetal.item.ShockMetalItemsRegistry;
import vnemesis.shockmetal.reference.ModIdReference;

import java.util.function.Supplier;

import static vnemesis.shockmetal.reference.BlockIdReference.ID_SHOCKMETAL_BLOCK;
import static vnemesis.shockmetal.reference.BlockIdReference.ID_SHOCKMETAL_NETHER_ORE_BLOCK;

public class ShockMetalBlocksRegistry
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ModIdReference.SHOCKMETAL_MOD_ID);

    public static final DeferredBlock<Block> SHOCKMETAL_BLOCK = registerBlock(ID_SHOCKMETAL_BLOCK, ShockMetalBlock::new);
    public static final DeferredBlock<Block> SHOCKMETAL_NETHER_ORE_BLOCK = registerBlock(ID_SHOCKMETAL_NETHER_ORE_BLOCK, ShockMetalNetherOreBlock::new);


    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ShockMetalItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
