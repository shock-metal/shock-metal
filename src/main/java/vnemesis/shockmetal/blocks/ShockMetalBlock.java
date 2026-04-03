package vnemesis.shockmetal.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

public class ShockMetalBlock extends Block
{
    public ShockMetalBlock() {
        super(Block.Properties.of()
                .strength(10, 12)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }
}
