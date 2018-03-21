package uk.co.shockwaveInteractive.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.ContainerMolecularManipulator;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;
import uk.co.shockwaveInteractive.util.Reference;

public class GuiMolecularManipulator extends GuiContainer
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/molecular_manipulator.png");
	private final InventoryPlayer player;
	private final TileEntityMolecularManipulator tileEntity;
	
	public GuiMolecularManipulator(InventoryPlayer player, TileEntityMolecularManipulator tileEntity) 
	{
		super(new ContainerMolecularManipulator(player, tileEntity));
		this.player = player;
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		
		String tileName = this.tileEntity.getDisplayName().getUnformattedComponentText();
		this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 5064311);
		this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 5064311);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(TEXTURES);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		if(TileEntityMolecularManipulator.isBurning(tileEntity))
		{
			int k = this.getBurnLeftScaled(44);
			this.drawTexturedModalRect(this.guiLeft + 45, this.guiTop + 51 + 12 - k, 177, 12 - k, 44, k + 1);
		}
		
		int l = this.getCraftProgressScaled(48);
		this.drawTexturedModalRect(this.guiLeft + 46, this.guiTop + 24, 177, 14, l + 1, 16);
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = this.tileEntity.getField(1);
		if(i == 0) i = 300;
		
		return this.tileEntity.getField(0) * pixels / i;
	}
	
	private int getCraftProgressScaled(int pixels)
	{
		int i = this.tileEntity.getField(2);
		int j = this .tileEntity.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
