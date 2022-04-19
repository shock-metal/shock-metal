package uk.co.shockwaveinteractive.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import uk.co.shockwaveinteractive.init.Blocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final List<OreConfiguration.TargetBlockState> NETHER_SHOCKMETAL_ORE = List.of(
            OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, Blocks.SHOCKMETAL_NETHER_ORE_BLOCK.get().defaultBlockState())
    );

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> SHOCKMETAL_ORE =
            FeatureUtils.register("shockmetal_nether_ore",
                    Feature.ORE, new OreConfiguration(NETHER_SHOCKMETAL_ORE, 4));
}
