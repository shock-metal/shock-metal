package uk.co.shockwaveinteractive.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // region VARIABLES
    private static final boolean defaultDisableAtomRipper = false;
    private static final float defaultVacuumMinecartRange = 5F;
    private static final int defaultOreGrinderUses = 50;
    private static final boolean defaultDisableAtomRipperTrait = false;
    private static final int defaultShieldDamageThreshold = 20; // Maximum damage threshold within the cooldown window
    private static final int defaultShieldRechargeDelay = 8; // Recharge Delay in seconds
    private static final int defaultEnergyStored = 500; // This * 1000FE = Max Energy storage
    private static final int defaultEnergyCostPerHeart = 1000;


    public static final ForgeConfigSpec.BooleanValue DISABLED_ATOM_RIPPER;
    public static final ForgeConfigSpec.BooleanValue DISABLE_ATOM_RIPPER_TRAIT;
    public static final ForgeConfigSpec.DoubleValue VACUUM_MINECART_RANGE;
    public static final ForgeConfigSpec.IntValue ORE_GRINDER_USES;
//    public static final ForgeConfigSpec.IntValue BASE_DAMAGE_THRESHOLD;
//    public static final ForgeConfigSpec.IntValue BASE_SHIELD_RECHARGE_DELAY;
//    public static final ForgeConfigSpec.IntValue MAX_DAMAGE_VALUE;
//    public static final ForgeConfigSpec.IntValue ENERGY_HEART_COST;
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
                .define("Disable Atom Ripper Trait", defaultDisableAtomRipperTrait);

        BUILDER.pop();

//        BUILDER.push("Tools");

//        BASE_DAMAGE_THRESHOLD = BUILDER
//                .comment("How many hearts of damage can the shield take before it enters cooldown")
//                .define("Shield Damage Threshold", defaultShieldDamageThreshold);
//
//        BASE_SHIELD_RECHARGE_DELAY = BUILDER
//                .comment("How many hearts of damage can the shield take before it enters cooldown")
//                .define("Shield Damage Threshold", defaultShieldRechargeDelay);



//        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
