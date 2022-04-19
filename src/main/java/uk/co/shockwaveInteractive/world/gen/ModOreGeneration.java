package uk.co.shockwaveinteractive.world.gen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import uk.co.shockwaveinteractive.world.feature.ModPlacedFeatures;

import java.util.List;

public class ModOreGeneration {
    public static void genOres(final BiomeLoadingEvent event) {
        final List<Holder<PlacedFeature>> features = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        features.add(ModPlacedFeatures.SHOCKMETAL_ORE_PLACED);
    }
}
