package vnemesis.shockmetal.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import vnemesis.shockmetal.Config;
import vnemesis.shockmetal.item.shield.ItemShieldModule;
import vnemesis.shockmetal.util.InventoryUtilities;

import static vnemesis.shockmetal.reference.ModIdReference.SHOCKMETAL_MOD_ID;

@OnlyIn(Dist.CLIENT)
public class GuiShieldLevel {

    public static final LayeredDraw.Layer OVERLAY = (guiGraphics, deltaTracker) -> renderOverlay(guiGraphics, deltaTracker);

    private static final ResourceLocation TEX_BORDER =
            ResourceLocation.fromNamespaceAndPath(SHOCKMETAL_MOD_ID, "textures/gui/shield_gui_border.png");
    private static final ResourceLocation TEX_SHIELD =
            ResourceLocation.fromNamespaceAndPath(SHOCKMETAL_MOD_ID, "textures/gui/shield_gui_shield.png");

    private static void renderOverlay(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        ItemStack stack = InventoryUtilities.getActiveShieldInInventory(mc.player);
        if (stack.isEmpty()) return;

        ItemShieldModule shieldModule = (ItemShieldModule) stack.getItem();
        long currentTime = mc.player.level().getGameTime();

        // Hide overlay when shield is depleted and not recently damaged (unless holding it)
        if (shieldModule.isShieldDepleted(stack)
                || (!(mc.player.getMainHandItem().getItem() instanceof ItemShieldModule)
                    && shieldModule.getTotalDamageTaken(stack) == 0
                    && currentTime - shieldModule.getLastDamageTime(stack) > (ItemShieldModule.BASE_SHIELD_RECHARGE_DELAY + 5) * 20L)) {
            return;
        }

        int guiWidth  = mc.getWindow().getGuiScaledWidth();
        int guiHeight = mc.getWindow().getGuiScaledHeight();
        int maxShieldBuffer = ItemShieldModule.getDamageThreshold();
        int rechargeDelay   = ItemShieldModule.getRechargeDelay();

        // ── Position from config ──────────────────────────────────────────────
        int offsetX;
        int yOffset;
        Config.ShieldGuiPosition pos = Config.SHIELD_GUI_POSITION.get();
        switch (pos) {
            case TOP_CENTER -> {
                offsetX = guiWidth / 2 - 54;
                yOffset = 20;
            }
            case BOTTOM_RIGHT -> {
                offsetX = guiWidth - 118;
                yOffset = guiHeight - 48;
            }
            default -> { // BOTTOM_CENTER
                offsetX = guiWidth / 2 - 54;
                yOffset = guiHeight - 48;
            }
        }

        // ── Bar lengths ───────────────────────────────────────────────────────
        int totalDamageTaken = shieldModule.getTotalDamageTaken(stack);
        int shieldLength   = (int) (96 * (maxShieldBuffer - totalDamageTaken) / (double) maxShieldBuffer);

        long timeSinceDamage    = currentTime - shieldModule.getLastDamageTime(stack);
        long timeRemainingTicks = Math.max(0, rechargeDelay * 20L - timeSinceDamage);
        int rechargeLength      = (int) (96 * timeRemainingTicks / ((double) rechargeDelay * 20));

        // ── Horizontal battery icon (left of main bar) ───────────────────────
        float energyRatio = getEnergyRatio(stack);
        int battW  = 38;                       // body width  — wide enough for "100%"
        int battH  = 10;                       // body height — MC font (8px) + 1px pad top/bottom
        int termW  = 3;                        // terminal nub width  (right side, like a real battery)
        int termH  = 5;                        // terminal nub height (centred on right wall)
        int battX  = offsetX - battW - 6;      // ~4 px gap before the main bar border
        int battY  = yOffset - 14;             // vertically centred around the main bar
        int termX  = battX + battW;
        int termY  = battY + (battH - termH) / 2;

        int battColor = energyRatio > 0.6f ? 0xFF33FF33
                      : energyRatio > 0.3f ? 0xFFFFFF22
                      :                      0xFFFF4444;
        int fillW = Math.round((battW - 2) * energyRatio);  // inner width after 1 px border each side

        // Border outline
        guiGraphics.fill(battX - 1, battY - 1, battX + battW + 1, battY + battH + 1, 0xFF888888);
        // Body background
        guiGraphics.fill(battX,     battY,     battX + battW,     battY + battH,     0xFF111111);
        // Terminal nub border + background (right side)
        guiGraphics.fill(termX,     termY - 1, termX + termW + 1, termY + termH + 1, 0xFF888888);
        guiGraphics.fill(termX,     termY,     termX + termW,     termY + termH,     0xFF111111);
        // Energy fill (grows left → right)
        if (fillW > 0) {
            guiGraphics.fill(battX + 1, battY + 1, battX + 1 + fillW, battY + battH - 1, battColor);
        }
        // Percentage text centred inside the battery (drawn last so it sits on top of the fill)
        String pctText = String.format("%d%%", Math.round(energyRatio * 100));
        int pctW = mc.font.width(pctText);
        guiGraphics.drawString(mc.font, pctText,
                battX + (battW - pctW) / 2,
                battY + 1,
                0xFFFFFFFF, true);

        // ── Recharge-timer text (centred in bar) ─────────────────────────────
        if (totalDamageTaken > 0) {
            String timerText;
            if (timeRemainingTicks > 0) {
                timerText = String.format("%.1fs", timeRemainingTicks / 20.0f);
            } else {
                timerText = Component.translatable("shockmetal.hud.recharging").getString();
            }
            int timerW = mc.font.width(timerText);
            int timerX = offsetX + 54 - timerW / 2;
            guiGraphics.drawString(mc.font, timerText, timerX, yOffset + 7, 0xFFFFFF00, false);
        }

        // ── Main bar ─────────────────────────────────────────────────────────
        guiGraphics.blit(TEX_BORDER, offsetX, yOffset - 18, 0, 0, 108, 18);

        int shieldVOffset = (int) ((currentTime / 3L % 33L)) * 6;
        guiGraphics.blit(TEX_SHIELD, offsetX + 9, yOffset - 9,  0, shieldVOffset, shieldLength,   6);
        guiGraphics.blit(TEX_SHIELD, offsetX + 9, yOffset + 2,  0, shieldVOffset, rechargeLength, 2);
    }

    /** Returns the FE ratio (0–1) for the shield stack. */
    private static float getEnergyRatio(ItemStack stack) {
        var cap = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (cap == null || cap.getMaxEnergyStored() == 0) return 0f;
        return Math.min(1f, (float) cap.getEnergyStored() / cap.getMaxEnergyStored());
    }
}
