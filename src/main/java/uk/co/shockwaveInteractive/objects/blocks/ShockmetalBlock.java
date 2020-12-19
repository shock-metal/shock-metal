package uk.co.shockwaveinteractive.objects.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ShockmetalBlock extends Block {

    public ShockmetalBlock() {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(10, 12)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.METAL)
                .setLightLevel(value -> 3)
                .setRequiresTool()
        );
    }

}
