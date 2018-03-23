package uk.co.shockwaveInteractive.util.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import uk.co.shockwaveInteractive.gui.GuiMolecularManipulator;
import uk.co.shockwaveInteractive.gui.GuiShockmetalManual;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.ContainerMolecularManipulator;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;
import uk.co.shockwaveInteractive.util.Reference;

public class GUIHandler implements IGuiHandler{
	
		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
			
			if(ID == Reference.GUI_MOLECULAR_MANIPULATOR)
			{
				return new ContainerMolecularManipulator(player.inventory, (TileEntityMolecularManipulator)world.getTileEntity(new BlockPos(x,y,z)));
			}
			else if(ID == Reference.GUI_SHOCK_METAL_MANUAL)
			{
				return new GuiShockmetalManual();
			}
			
			return null;
		}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
			
			if(ID == Reference.GUI_MOLECULAR_MANIPULATOR)
			{
				return new GuiMolecularManipulator(player.inventory, (TileEntityMolecularManipulator)world.getTileEntity(new BlockPos(x,y,z)));
			}
			else if(ID == Reference.GUI_SHOCK_METAL_MANUAL)
			{
				return new GuiShockmetalManual();
			}
			return null;
		}

}
