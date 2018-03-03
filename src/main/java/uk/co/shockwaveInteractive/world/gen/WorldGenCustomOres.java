package uk.co.shockwaveInteractive.world.gen;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.init.BlockOres;
import uk.co.shockwaveInteractive.util.handlers.EnumHandler;

public class WorldGenCustomOres implements IWorldGenerator
{
	private WorldGenerator ore_nether_shockmetal;
	
	private int ore_nether_shockmetal_chance = 10;
	
	public WorldGenCustomOres()
	{
		ore_nether_shockmetal = new WorldGenMinable(BlockInit.ORE_NETHER.getDefaultState().withProperty(BlockOres.VARIANT, EnumHandler.EnumType.SHOCKMETAL), 3, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}

	public void runGenerator(WorldGenerator gen, World world, Random random, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256)
		{
			throw new IllegalArgumentException("Ore generated out of bounds");
		}
		
		int heightDiff = maxHeight - minHeight + 1;
		
		for (int i = 0; i < chance; i++)
		{
			int x = chunkX * 16 + random.nextInt(16);
			int y = minHeight + random.nextInt(heightDiff);
			int z = chunkZ * 16 + random.nextInt(16);
			
			gen.generate(world, random, new BlockPos(x,y,z));
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case -1:
			
			runGenerator(ore_nether_shockmetal, world, random, chunkX, chunkZ, ore_nether_shockmetal_chance, 45, 80);
			break;
			
		case 0:
			break;
			
		case 1:
			break;
		}
	}
	
	
}
