package uk.co.shockwaveInteractive.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import uk.co.shockwaveInteractive.init.ItemInit;
import uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;
import uk.co.shockwaveInteractive.util.Reference;

public class GuiShockmetalManual extends GuiScreen
{
	private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];
	
	private static final ResourceLocation bookButtons = new ResourceLocation(Reference.MODID, "textures/gui/BookButtons.png");
	private static final ResourceLocation cover = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_cover.png");
	private static final ResourceLocation page1 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p1.png");
	private static final ResourceLocation page2 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p2.png");
	private static final ResourceLocation page3 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p3.png");

	private static int index = 0;
	
	private GuiButton buttonNext;
	private GuiButton buttonPrev;
	private GuiButton buttonClose;
    private Minecraft mc = Minecraft.getMinecraft();
    
    @Override
    public void initGui() 
    {
        super.initGui();
        
        this.buttonList.add(buttonNext = new GuiButton(0, this.width / 2 + 100, this.height - (this.height / 4) + 25, "Next"));
        this.buttonList.add(buttonPrev = new GuiButton(1, this.width / 2 - 200, this.height - (this.height / 4) + 25, "Previous"));
        this.buttonList.add(buttonClose = new GuiButton(2, this.width / 2 - 25, this.height - (this.height / 4) + 40, "Close"));
        
        buttonClose.width = 50;
        buttonNext.width = 100;
        buttonPrev.width = 100;
        
        TEXTURES[0] = cover;
        TEXTURES[1] = page1;
        TEXTURES[2] = page2;
        TEXTURES[3] = page3;
        
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button == buttonClose) {
            mc.player.closeScreen();
        }
        else if (button == buttonNext)
        {
        	if(!((index + 1) == TEXTURES.length))
        	{
        		index++;
        	}
        	
        	//mc.player.sendChatMessage("Page " + (index + 1) + " of " + TEXTURES.length);
        }
        else if (button == buttonPrev)
        {
        	if(!((index - 1) == -1))
        	{
        		index--;
        	}
        	
        	//mc.player.sendChatMessage("Page " + (index + 1) + " of " + TEXTURES.length);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	this.drawDefaultBackground();
        renderBookImages(this);
        
        

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() 
    {
        return true;
    }

    public void renderBookImages(GuiScreen guiScreen) {
        if (TEXTURES != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES[index]);
            guiScreen.drawModalRectWithCustomSizedTexture(guiScreen.width / 2 - 256, guiScreen.height / 2 - 150, 0, 0, 512, 256, 512, 256);
        }
    }

}
