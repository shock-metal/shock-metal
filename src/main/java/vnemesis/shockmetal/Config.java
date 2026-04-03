package vnemesis.shockmetal;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {

    // region VARIABLES
    private static final boolean defaultDisableAtomRipper = false;
    private static final float defaultVacuumMinecartRange = 5F;
    private static final int defaultOreGrinderUses = 50;
    private static final boolean defaultDisableAtomRipperTrait = false;
    private static final int defaultShieldDamageThreshold = 20; // Maximum damage threshold within the cooldown window
    private static final int defaultShieldRechargeDelay = 8; // Recharge Delay in seconds
    private static final int defaultEnergyStored = 500; // This * 1000FE = Max Energy storage
    private static final int defaultEnergyCostPerHeart = 1000;
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
            .comment("How many hearts of damage can the shield take before it enters cooldown")
            .define("shieldDamageThreshold", defaultShieldRechargeDelay);

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
