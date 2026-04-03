package vnemesis.shockmetal.blocks;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;

public class ShockMetalNetherOreBlock extends DropExperienceBlock
{
    public ShockMetalNetherOreBlock() {
        super(
            UniformInt.of(0, 2),
            DropExperienceBlock.Properties.of()
                .strength(8, 10)
                .sound(SoundType.STONE)
                .lightLevel(value -> 15)
                .requiresCorrectToolForDrops()
        );
    }
}

