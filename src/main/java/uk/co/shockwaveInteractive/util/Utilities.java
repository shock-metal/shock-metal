package uk.co.shockwaveinteractive.util;

import net.minecraft.world.level.Level;
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

}
