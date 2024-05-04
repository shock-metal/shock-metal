package uk.co.shockwaveinteractive.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;

public class Utilities {

    public static boolean isModLoaded(String modid) {

        return ModList.get().isLoaded(modid);
    }

    public static boolean isClientLevel(Level level) {

        return level.isClientSide();
    }

    public static boolean isServerLevel(Level level) {

        return !level.isClientSide();
    }

    @OnlyIn(Dist.CLIENT)
    public static Player getClientPlayer() {
        return Minecraft.getInstance().player;
    }
}
