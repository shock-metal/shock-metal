package uk.co.shockwaveinteractive.util;

import net.minecraft.world.World;
import net.minecraftforge.fml.ModList;

public class Utilities {

    public static boolean isModLoaded(String modid) {

        return ModList.get().isLoaded(modid);
    }

    public static boolean isClientWorld(World world) {

        return world.isRemote;
    }

    public static boolean isServerWorld(World world) {

        return !world.isRemote;
    }

}
