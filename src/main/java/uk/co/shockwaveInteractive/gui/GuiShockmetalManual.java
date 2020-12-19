//package uk.co.shockwaveinteractive.gui;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.renderer.texture.DynamicTexture;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.Color;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.StringTextComponent;
//import net.minecraft.util.text.TextComponent;
//
//import java.io.IOException;
//
//import org.lwjgl.opengl.GL11;
//import uk.co.shockwaveinteractive.init.ItemInit;
//import uk.co.shockwaveinteractive.objects.blocks.machines.molecularmanipulator.TileEntityMolecularManipulator;
//import uk.co.shockwaveinteractive.util.Reference;
//
//public class GuiShockmetalManual extends Screen
//{
//	private static final ResourceLocation[] TEXTURES = new ResourceLocation[4];
//
//	private static final ResourceLocation bookButtons = new ResourceLocation(Reference.MODID, "textures/gui/BookButtons.png");
//	private static final ResourceLocation cover = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_cover.png");
//	private static final ResourceLocation page1 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p1.png");
//	private static final ResourceLocation page2 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p2.png");
//	private static final ResourceLocation page3 = new ResourceLocation(Reference.MODID, "textures/gui/shockmetalmanual_p3.png");
//
//	private static int index = 0;
//
//	private Button buttonNext;
//	private Button buttonPrev;
//	private Button buttonClose;
//    private Minecraft mc = Minecraft.getInstance();
//
//    protected GuiShockmetalManual(ITextComponent titleIn) {
//        super(titleIn);
//    }
//
//    @Override
//    public void init()
//    {
//        super.init();
//
//        this.buttons.add(buttonNext = new Button(0, 0, this.width / 2 + 100, this.height - (this.height / 4) + 25, new StringTextComponent("Next"), Button.));
//        this.buttons.add(buttonPrev = new Button(0, 0, this.width / 2 - 200, this.height - (this.height / 4) + 25, "Previous"));
//        this.buttons.add(buttonClose = new Button(0, 0, this.width / 2 - 25, this.height - (this.height / 4) + 40, "Close"));
//
//        buttonClose.setWidth(50);
//        buttonNext.setWidth(100);
//        buttonPrev.setWidth(100);
//
//        TEXTURES[0] = cover;
//        TEXTURES[1] = page1;
//        TEXTURES[2] = page2;
//        TEXTURES[3] = page3;
//
//    }
//
//    @Override
//    protected void actionPerformed(GuiButton button) throws IOException {
//        if (button == buttonClose) {
//            mc.player.closeScreen();
//        }
//        else if (button == buttonNext)
//        {
//        	if(!((index + 1) == TEXTURES.length))
//        	{
//        		index++;
//        	}
//
//        	//mc.player.sendChatMessage("Page " + (index + 1) + " of " + TEXTURES.length);
//        }
//        else if (button == buttonPrev)
//        {
//        	if(!((index - 1) == -1))
//        	{
//        		index--;
//        	}
//
//        	//mc.player.sendChatMessage("Page " + (index + 1) + " of " + TEXTURES.length);
//        }
//    }
//
//    @Override
//    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//    	this.renderDirtBackground(0);
//        renderBookImages(this);
//
//        super.drawScreen(mouseX, mouseY, partialTicks);
//    }
//
//    @Override
//    public boolean isPauseScreen() {
//        return true;
//    }
//
//    public void renderBookImages(Screen screen) {
//        if (TEXTURES != null) {
//            Minecraft.getInstance().getTextureManager().bindTexture(TEXTURES[index]);
//            Screen.fill(new MatrixStack(),screen.width / 2 - 256, screen.height / 2 - 150, 0, 0, 255);
//        }
//    }
//
//}
