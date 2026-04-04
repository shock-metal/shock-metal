package vnemesis.shockmetal;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    /**
     * Where the Shield Module HUD overlay is rendered on screen.
     */
    public enum ShieldGuiPosition {
        /** Centred above the hotbar / health bars (default). */
        BOTTOM_CENTER,
        /** Bottom-right corner of the screen. */
        BOTTOM_RIGHT,
        /** Top-centre of the screen. */
        TOP_CENTER
    }

    // region VARIABLES
    private static final boolean defaultDisableAtomRipper = false;
    private static final float defaultVacuumMinecartRange = 5F;
    private static final int defaultOreGrinderUses = 50;
    private static final boolean defaultDisableAtomRipperTrait = false;
    private static final int defaultShieldDamageThreshold = 20; // Maximum damage threshold within the cooldown window
    private static final int defaultShieldRechargeDelay = 8; // Recharge Delay in seconds
    private static final int defaultShieldMaxEnergy = 500_000; // Max FE stored in the Shield Module (default 500 hearts)
    // endregion

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DISABLED_ATOM_RIPPER = BUILDER
            .comment("If TRUE, Atom Ripper enchantment will be unavailable via normal means.")
            .define("disableAtomRipperEnchant", defaultDisableAtomRipper);


    public static final ModConfigSpec.DoubleValue VACUUM_MINECART_RANGE = BUILDER
            .comment("This option sets the range of the vacuum minecart")
            .defineInRange("vacuumRange", defaultVacuumMinecartRange, 2.0F, 8.0F);

    public static final ModConfigSpec.IntValue ORE_GRINDER_USES = BUILDER
            .comment("This option sets the number of uses for the ore grinder")
            .defineInRange("oreGrinderUses", defaultOreGrinderUses, 1, 100);

    public static final ModConfigSpec.ConfigValue<Integer> BASE_DAMAGE_THRESHOLD = BUILDER
            .comment("How many hearts of damage can the shield take before it enters cooldown")
            .define("Shield Damage Threshold", defaultShieldDamageThreshold);

    public static final ModConfigSpec.ConfigValue<Integer> BASE_SHIELD_RECHARGE_DELAY = BUILDER
            .comment("How long (in seconds) the shield waits before starting to recharge after taking damage")
            .define("shieldRechargeDelay", defaultShieldRechargeDelay);

    public static final ModConfigSpec.IntValue SHIELD_MAX_ENERGY = BUILDER
            .comment("Maximum FE energy the Shield Module can store.\n"
                    + "Default is 500,000 FE (enough to absorb 500 hearts of damage at 1,000 FE/heart).\n"
                    + "Minimum: 1,000 FE")
            .defineInRange("shieldMaxEnergy", defaultShieldMaxEnergy, 1_000, Integer.MAX_VALUE);

    public static final ModConfigSpec.EnumValue<ShieldGuiPosition> SHIELD_GUI_POSITION = BUILDER
            .comment("Position of the Shield Module HUD overlay.\n"
                    + "BOTTOM_CENTER = centred above the hotbar and health bars (default)\n"
                    + "BOTTOM_RIGHT  = bottom-right corner of the screen\n"
                    + "TOP_CENTER    = top-centre of the screen")
            .defineEnum("shieldGuiPosition", ShieldGuiPosition.TOP_CENTER);

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
