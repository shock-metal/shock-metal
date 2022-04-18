package uk.co.shockwaveinteractive.world.gen;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import uk.co.shockwaveinteractive.init.Blocks;
import uk.co.shockwaveinteractive.util.reference.MainReference;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = MainReference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldGenCustomOres
{
//	public static OreFeatureConfig.FillerBlockType END_STONE = OreFeatureConfig.FillerBlockType.create("END_STONE", "end_stone", new BlockMatcher(Blocks.END_STONE));
	private static final ArrayList<ConfiguredFeature<?, ?>> overworldOres = new ArrayList<ConfiguredFeature<?, ?>>();
	private static final ArrayList<ConfiguredFeature<?, ?>> netherOres = new ArrayList<ConfiguredFeature<?, ?>>();
	private static final ArrayList<ConfiguredFeature<?, ?>> endOres = new ArrayList<ConfiguredFeature<?, ?>>();

	public static void initOres()
	{
		netherOres.add(register(
				"shockwave_nether_ore", Feature.ORE.configured(
						new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Blocks.SHOCKMETAL_NETHER_ORE_BLOCK.get().defaultBlockState(), 4))
				)
				.range(20)
				.count(1) // chunk spawn frequency
		);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void gen(BiomeLoadingEvent event) {
		BiomeGenerationSettingsBuilder generation = event.getGeneration();
		if(event.getCategory().equals(Biome.Category.NETHER)){
			for(ConfiguredFeature<?, ?> ore : netherOres){
				if (ore != null) generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
			}
		}
		if(event.getCategory().equals(Biome.Category.THEEND)){
			for(ConfiguredFeature<?, ?> ore : endOres){
				if (ore != null) generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
			}
		}
		for(ConfiguredFeature<?, ?> ore : overworldOres){
			if (ore != null) generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ore);
		}
	}

	private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, MainReference.MODID + ":" + name, configuredFeature);
	}

//	@SubscribeEvent
//	public static void runWorldGenOres(FMLLoadCompleteEvent event)
//	{
//		for (Biome biome : ForgeRegistries.BIOMES )
//		{
//			switch(biome.getCategory())
//			{
//				case Biome.Category.NETHER:
//
//					generateOre(biome, 5, 2, 5, 20, OreFeatureConfig.FillerBlockType.NETHERRACK, BlockInit.SHOCKMETAL_NETHER_ORE_BLOCK.get().getDefaultState(), 4);
//					break;
//
//				case Biome.Category.THEEND:
//					break;
//			}
//		}
//	}

//	private static void generateOre(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size)
//	{
//		CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
//		OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
//		ConfiguredPlacement config = Placement.COUNT_RANGE.configuration(range);
//		biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.Ore.WithConfiguration(feature).withPlacement(config));
//	}
}
