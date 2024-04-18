package uk.co.shockwaveinteractive.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // region VARIABLES
    private static final boolean defaultDisableAtomRipper = false;
    private static final float defaultVacuumMinecartRange = 5F;
    private static final int defaultOreGrinderUses = 50;
    private static final boolean defaultDsableAtomRipperTrait = false;


    public static final ForgeConfigSpec.BooleanValue DISABLED_ATOM_RIPPER;
    public static final ForgeConfigSpec.BooleanValue DISABLE_ATOM_RIPPER_TRAIT;
    public static final ForgeConfigSpec.DoubleValue VACUUM_MINECART_RANGE;
    public static final ForgeConfigSpec.IntValue ORE_GRINDER_USES;
    // endregion
    
    static {
        BUILDER.push("Enchantments");

        DISABLED_ATOM_RIPPER = BUILDER
                .comment("If TRUE, Atom Ripper enchantment will be unavailable via normal means.")
                .define("Disable Atom Ripper Enchantment", defaultDisableAtomRipper);


        BUILDER.pop();

        BUILDER.push("Vehicles");

        VACUUM_MINECART_RANGE = BUILDER
                .comment("This option sets the range of the vacuum minecart")
                .defineInRange("Vacuum Minecraft Range", defaultVacuumMinecartRange, 2.0F, 8.0F);

        BUILDER.pop();

        BUILDER.push("Crafting");

        ORE_GRINDER_USES = BUILDER
                .comment("This option sets the number of uses for the ore grinder")
                .defineInRange("Ore Grinder Uses", defaultOreGrinderUses, 1, 100);

        BUILDER.pop();

        BUILDER.push("Compat");

        DISABLE_ATOM_RIPPER_TRAIT = BUILDER
                .comment("Enable shockmetal trait on tinker tools")
                .define("Disable Atom Ripper Trait", defaultDsableAtomRipperTrait);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
