package uk.co.shockwaveinteractive.objects.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ShockmetalNetherOreBlock extends DropExperienceBlock {

    public ShockmetalNetherOreBlock() {
        super(DropExperienceBlock.Properties.of(Material.METAL)
                .strength(8, 10)
                .sound(SoundType.STONE)
                .lightLevel(value -> 15)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader level, RandomSource randomSource, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return 2 * fortuneLevel;
    }
}
