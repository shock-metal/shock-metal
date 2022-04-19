package uk.co.shockwaveinteractive.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> SHOCKMETAL_ORE_PLACED = PlacementUtils.register("shockmatel_ore_placed",
            ModConfiguredFeatures.SHOCKMETAL_ORE, ModOrePlacement.rareOrePlacement(2,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0), VerticalAnchor.aboveBottom(15))));
}
