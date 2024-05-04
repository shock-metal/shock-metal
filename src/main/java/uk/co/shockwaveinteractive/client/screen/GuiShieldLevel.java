package uk.co.shockwaveinteractive.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import uk.co.shockwaveinteractive.objects.items.energytools.ItemShieldModule;
import uk.co.shockwaveinteractive.util.InventoryUtilities;

import static uk.co.shockwaveinteractive.util.reference.MainReference.MODID;

public class GuiShieldLevel extends GuiComponent {
    public static final IGuiOverlay OVERLAY = GuiShieldLevel::renderOverlay;

    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void renderOverlay(ForgeGui gui, PoseStack ms, float pt, int width, int height) {
        ItemStack stack = InventoryUtilities.getActiveShieldInInventory(minecraft.player);

        if(stack.isEmpty())
            return;

        long currentTime = minecraft.player.level.getGameTime();

        ItemShieldModule shieldModule = (ItemShieldModule) stack.getItem();
//        if(shieldModule.isShieldDepleted(stack)
//                || (shieldModule.getTotalDamageTaken() == 0
//                    && currentTime - shieldModule.getLastDamageTime() > (ItemShieldModule.SHIELD_RECHARGE_DELAY + 5) * 20))
//            return;

        int maxShieldBuffer = ItemShieldModule.DAMAGE_THRESHOLD;
        int rechargeDelay = ItemShieldModule.SHIELD_RECHARGE_DELAY;
        int yOffset = 20;
        int offsetX = Math.round((float) minecraft.getWindow().getGuiScaledWidth() / 2) - 54;
        int barWidth = 108;
        int barHeight = 18;
        int shieldLength = 96;
        int rechargeLength = 96;

        shieldLength *= ((maxShieldBuffer - shieldModule.getTotalDamageTaken()) / (double) maxShieldBuffer);

        rechargeLength *=
            (
                (
                        (rechargeDelay * 20) - Math.round(currentTime - shieldModule.getLastDamageTime())
                )
                / ((double) rechargeDelay * 20)
            );

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_border.png"));
        // Stack xOffset, yOffset, textureXStart, textureYStart, textureXEnd, textureYEnd, textureSizeX, textureSizeY
        blit(ms, offsetX, yOffset - 18, 0, 0, barWidth, barHeight, 256, 256);
        int shieldOffset = (int) (((0 + pt) / 3 % (33))) * 6;

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset - 9, 0, shieldOffset, shieldLength, 6, 256, 256);

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset + 2, 0, shieldOffset, rechargeLength, 2, 256, 256);


        String text = (maxShieldBuffer - shieldModule.getTotalDamageTaken()) + "  /  " + maxShieldBuffer;
        int maxWidth = minecraft.font.width(maxShieldBuffer + "  /  " + maxShieldBuffer);
        int offset = offsetX + 140 - maxWidth / 2 + (maxWidth - minecraft.font.width(text));

        drawString(ms, minecraft.font, text, offset, yOffset - 10, 0xFFFFFF);
    }
}
