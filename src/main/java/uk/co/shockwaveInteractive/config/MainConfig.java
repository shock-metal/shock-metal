package uk.co.shockwaveinteractive.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Modified Version of CoreConfig by CoFH Team found at https://github.com/KingLemming/1.16/blob/27f0122129e6d3dea7751e98ae6b7d6f526954e9/CoFHCore/src/main/java/cofh/core/CoFHCore.java
 */
public class MainConfig {

    private static boolean registered = false;

    public static void register() {

        if (registered) {
            return;
        }
        FMLJavaModLoadingContext.get().getModEventBus().register(MainConfig.class);
        registered = true;

        genServerConfig();
        genClientConfig();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientSpec);
    }

    private MainConfig() {

    }

    // region CONFIG SPEC
    private static final ForgeConfigSpec.Builder SERVER_CONFIG = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec serverSpec;

    private static final ForgeConfigSpec.Builder CLIENT_CONFIG = new ForgeConfigSpec.Builder();
    private static ForgeConfigSpec clientSpec;

    private static void genServerConfig() {

        SERVER_CONFIG.push("Enchantments");

        serverDisableAtomRipper = SERVER_CONFIG
                .comment("If TRUE, Atom Ripper enchantment will be unavailable via normal means.")
                .define("Disable Atom Ripper Enchantment", disableAtomRipper);


        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Vehicles");

        serverVacuumMinecartRange = SERVER_CONFIG
                .comment("This option sets the range of the vacuum minecart")
                .defineInRange("Vacuum Minecraft Range", vacuumMinecartRange, 2.0F, 8.0F);

        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Crafting");

        serverOreGrinderUses = SERVER_CONFIG
                .comment("This option sets the number of uses for the ore grinder")
                .defineInRange("Ore Grinder Uses", oreGrinderUses, 1, 100);

        SERVER_CONFIG.pop();

        SERVER_CONFIG.push("Compat");

        serverDisableAtomRipperTrait = SERVER_CONFIG
                .comment("Enable shockmetal trait on tinker tools")
                .define("Disable Atom Ripper Trait", disableAtomRipperTrait);

        SERVER_CONFIG.pop();

        serverSpec = SERVER_CONFIG.build();

        refreshServerConfig();
    }

    private static void genClientConfig() {

        clientSpec = CLIENT_CONFIG.build();

        refreshClientConfig();
    }

    private static void refreshServerConfig() {
        disableAtomRipper = serverDisableAtomRipper.get();
        oreGrinderUses = serverOreGrinderUses.get();
        vacuumMinecartRange = serverVacuumMinecartRange.get().floatValue();
        disableAtomRipperTrait = serverDisableAtomRipperTrait.get();
    }

    private static void refreshClientConfig() {

    }
    // endregion

    // region VARIABLES
    public static boolean disableAtomRipper = false;
    public static float vacuumMinecartRange = 5F;
    public static int oreGrinderUses = 50;
    public static boolean disableAtomRipperTrait = false;


    private static BooleanValue serverDisableAtomRipper;
    private static BooleanValue serverDisableAtomRipperTrait;
    private static DoubleValue serverVacuumMinecartRange;
    private static IntValue serverOreGrinderUses;
    // endregion

    // region CONFIGURATION
    @SubscribeEvent
    public static void configLoading(final ModConfig.Loading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }

    @SubscribeEvent
    public static void configReloading(final ModConfig.Reloading event) {

        switch (event.getConfig().getType()) {
            case CLIENT:
                refreshClientConfig();
                break;
            case SERVER:
                refreshServerConfig();
        }
    }
    // endregion
}