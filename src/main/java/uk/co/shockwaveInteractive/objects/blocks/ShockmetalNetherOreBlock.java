package uk.co.shockwaveinteractive.objects.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class ShockmetalNetherOreBlock extends OreBlock {

    public ShockmetalNetherOreBlock() {
        super(OreBlock.Properties.of(Material.METAL)
                .strength(8, 10)
                .sound(SoundType.STONE)
                .lightLevel(value -> 15)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public int getExpDrop(BlockState state, LevelReader reader, BlockPos pos, int fortune, int silktouch) {
        return 1;
    }
}
