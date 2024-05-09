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

        if(shieldModule.isShieldDepleted(stack)
            ||  ( !(minecraft.player.getMainHandItem().getItem() instanceof ItemShieldModule) && (shieldModule.getTotalDamageTaken(stack) == 0 && currentTime - shieldModule.getLastDamageTime(stack) > (ItemShieldModule.BASE_SHIELD_RECHARGE_DELAY + 5) * 20)))
            return;

//        var allShields = ItemShieldModule.getShieldsInInventory(minecraft.player, false);
//        var activeShields = ItemShieldModule.getShieldsInInventory(minecraft.player, true);

        int maxShieldBuffer = ItemShieldModule.getDamageThreshold();
        int rechargeDelay = ItemShieldModule.getRechargeDelay();
        int yOffset = 20;
        int offsetX = Math.round((float) minecraft.getWindow().getGuiScaledWidth() / 2) - 54;
        int barWidth = 108;
        int barHeight = 18;
        int shieldLength = 96;
        int rechargeLength = 96;

        shieldLength *= ((maxShieldBuffer - shieldModule.getTotalDamageTaken(stack)) / (double) maxShieldBuffer);

        rechargeLength *=
            (
                (
                        (rechargeDelay * 20) - Math.round(currentTime - shieldModule.getLastDamageTime(stack))
                )
                / ((double) rechargeDelay * 20)
            );

        // Energy Percentage
        float energyPercentage = Math.round((ItemShieldModule.getEnergyStored(stack) / ItemShieldModule.getTotalMaxEnergy()) * 100);
        String energyPercentageText = energyPercentage + "%";
        int maxenergyWidth = minecraft.font.width(energyPercentageText);
        int offsetEnergy = offsetX - 15 - maxenergyWidth / 2 + (maxenergyWidth - minecraft.font.width(energyPercentageText));
        drawString(ms, minecraft.font, energyPercentageText, offsetEnergy, yOffset - 10, 0xFFFFFF);

        // Active Shields
//        if(allShields.size() > 1) {
//            String textActive = Component.translatable("shockmetal.tooltip.energy.active", activeShields.size() + " / " + allShields.size()).getString();
//            int maxWidthActive = minecraft.font.width(maxShieldBuffer + " / " + maxShieldBuffer);
//            int offsetActive = offsetX + 155 - maxWidthActive / 2 + (maxWidthActive - minecraft.font.width(textActive));
//            drawString(ms, minecraft.font, textActive, offsetActive, yOffset - 10, 0xFFFFFF);
//        }

        // Energy Buffer
        String text = (maxShieldBuffer - shieldModule.getTotalDamageTaken(stack)) + " / " + maxShieldBuffer;
        int maxWidth = minecraft.font.width(maxShieldBuffer + " / " + maxShieldBuffer);
        int offset = offsetX + 54 - maxWidth / 2 + (maxWidth - minecraft.font.width(text));
        drawString(ms, minecraft.font, text, offset, yOffset + 7, 0xFFFFFF);

        // Shield Border
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_border.png"));
        // Stack xOffset, yOffset, textureXStart, textureYStart, textureXEnd, textureYEnd, textureSizeX, textureSizeY
        blit(ms, offsetX, yOffset - 18, 0, 0, barWidth, barHeight, 256, 256);
        int shieldOffset = (int) (((0 + pt) / 3 % (33))) * 6;

        // Shield Buffer
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset - 9, 0, shieldOffset, shieldLength, 6, 256, 256);

        // Recharge Timer
        RenderSystem.setShaderTexture(0, new ResourceLocation(MODID, "textures/gui/shield_gui_shield.png"));
        blit(ms, offsetX + 9, yOffset + 2, 0, shieldOffset, rechargeLength, 2, 256, 256);

    }
}
