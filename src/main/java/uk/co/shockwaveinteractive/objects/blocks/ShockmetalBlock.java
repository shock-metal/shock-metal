package uk.co.shockwaveinteractive.objects.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class ShockmetalBlock extends Block {

    public ShockmetalBlock() {
        super(Block.Properties.of(Material.METAL)
                .strength(10, 12)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }

}
