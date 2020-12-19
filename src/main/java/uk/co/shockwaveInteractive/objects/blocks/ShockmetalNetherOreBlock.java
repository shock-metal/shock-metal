package uk.co.shockwaveinteractive.objects.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class ShockmetalNetherOreBlock extends OreBlock {

    public ShockmetalNetherOreBlock() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(8, 10)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE)
                .setLightLevel(value -> 3)
                .setRequiresTool()
        );
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return 1;
    }
}
