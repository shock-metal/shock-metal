package uk.co.shockwaveInteractive.util.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import uk.co.shockwaveInteractive.gui.GuiShockmetalManual;

public class ShockMetalCommand extends CommandBase 
{

    private final String COMMAND_NAME = "shockmetalmanual";
    private final String COMMAND_HELP = "Display the Shockmetal Manual GUI";


	@Override
	public String getName() 
	{
		return COMMAND_NAME;
	}

	@Override
	public String getUsage(ICommandSender sender) {
		 return COMMAND_HELP;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		Minecraft.getMinecraft().displayGuiScreen(new GuiShockmetalManual());
	}
}
