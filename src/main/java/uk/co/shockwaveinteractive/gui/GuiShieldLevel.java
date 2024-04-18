package uk.co.shockwaveinteractive.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import uk.co.shockwaveinteractive.integration.curios.CuriosProxy;
import uk.co.shockwaveinteractive.objects.items.ItemShieldModule;
import uk.co.shockwaveinteractive.util.InventoryUtilities;

import java.util.function.Predicate;

import static uk.co.shockwaveinteractive.util.reference.MainReference.MODID;

public class GuiShieldLevel extends GuiComponent {
    public static final IGuiOverlay OVERLAY = GuiShieldLevel::renderOverlay;

    private static final Minecraft minecraft = Minecraft.getInstance();

    public static boolean shouldShowShieldBar() {
        ItemStack shieldModule = InventoryUtilities.getActiveShieldInInventory(minecraft.player);
        return !shieldModule.isEmpty();
    }

    public static void renderOverlay(ForgeGui gui, PoseStack ms, float pt, int width, int height) {
        if (!shouldShowShieldBar())
            return;

        ItemStack stack = InventoryUtilities.getActiveShieldInInventory(minecraft.player);
        ItemShieldModule shieldModule = (ItemShieldModule) stack.getItem();

        if(shieldModule.isShieldDepleted(stack))
            return;

        int maxShieldBuffer = ItemShieldModule.DAMAGE_THRESHOLD;
        int rechargeDelay = ItemShieldModule.SHIELD_RECHARGE_DELAY;

        int offsetX = Math.round((float) minecraft.getWindow().getGuiScaledWidth() / 2) - 54;
        int shieldLength = 96;
        int rechargeLength = 96;
        long currentTime = System.currentTimeMillis();

        shieldLength *= ((maxShieldBuffer - shieldModule.getTotalDamageTaken()) / (double) maxShieldBuffer);

        rechargeLength *= ((rechargeDelay - Math.round(currentTime - shieldModule.getLastDamageTime() * 1000)) / (double) rechargeDelay);

        // minecraft.getWindow().getGuiScaledHeight() -
        int yOffset = 30;

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_border.png"));
        // Stack xOffset, yOffset, textureXStart, textureYStart, textureXEnd, textureYEnd, textureSizeX, textureSizeY
        blit(ms, offsetX, yOffset - 18, 0, 0, 108, 18, 256, 256);
        int shieldOffset = (int) (((0 + pt) / 3 % (33))) * 6;

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset - 9, 0, shieldOffset, shieldLength, 6, 256, 256);

        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset + 20, 0, shieldOffset + 20, rechargeLength, 6, 256, 256);


        String text = (maxShieldBuffer - shieldModule.getTotalDamageTaken()) + "  /  " + maxShieldBuffer;
        int maxWidth = minecraft.font.width(maxShieldBuffer + "  /  " + maxShieldBuffer);
        int offset = offsetX + 54 - maxWidth / 2 + (maxWidth - minecraft.font.width(text));

        drawString(ms, minecraft.font, text, offset, yOffset - 10, 0xFFFFFF);
        drawString(ms, minecraft.font, String.valueOf((int) (0.15f * maxShieldBuffer)), offset + 69, yOffset - 20, 0xFFFFFF);
    }
}
