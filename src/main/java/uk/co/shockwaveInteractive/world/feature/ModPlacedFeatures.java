package uk.co.shockwaveinteractive.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import uk.co.shockwaveinteractive.util.reference.MainReference;

public class ModPlacedFeatures {

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURE = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MainReference.MODID);
    public static final RegistryObject<PlacedFeature> SHOCKMETAL_ORE_PLACED = PLACED_FEATURE.register("shockmatel_ore_placed",
            () -> new PlacedFeature(
                    (Holder<ConfiguredFeature<?,?>>)(Holder<? extends ConfiguredFeature<?,?>>)
                    ModConfiguredFeatures.SHOCKMETAL_ORE,
                    ModOrePlacement.rareOrePlacement(2,
                    HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(0),
                    VerticalAnchor.aboveBottom(15)))));
}
