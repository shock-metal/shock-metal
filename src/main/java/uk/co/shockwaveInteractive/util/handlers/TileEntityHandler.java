package uk.co.shockwaveInteractive.util.handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;

public class TileEntityHandler 
{
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityMolecularManipulator.class, "molecular_manipulator");
	}

}
