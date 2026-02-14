package tsuteto.tofu.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.util.ModLog;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registry for Tofu Force (TF) energy material values.
 * Items registered here can be used as fuel in TF machines
 * (condenser, storage, etc.) to produce Tofu Force energy.
 *
 * <p>Call {@link #init()} during common setup to populate the
 * default registrations for built-in tofu items.</p>
 */
public final class TfMaterialRegistry {

    /** Map from item to its TF energy value. */
    private static final Map<Item, Integer> REGISTRY = new LinkedHashMap<>();

    private static boolean initialised = false;

    private TfMaterialRegistry() {
        // Utility class; no instantiation
    }

    /**
     * Registers an item with a TF energy value.
     *
     * @param item    the item to register
     * @param tfValue the TF energy value (must be positive)
     */
    public static void register(ItemLike item, int tfValue) {
        if (tfValue <= 0) {
            ModLog.warn("Attempted to register TF material with non-positive value: {} -> {}", item, tfValue);
            return;
        }
        REGISTRY.put(item.asItem(), tfValue);
    }

    /**
     * Registers an item supplied lazily (for use with deferred registries).
     *
     * @param itemSupplier supplier for the item
     * @param tfValue      the TF energy value
     */
    public static void register(Supplier<? extends ItemLike> itemSupplier, int tfValue) {
        register(itemSupplier.get(), tfValue);
    }

    /**
     * Returns the TF energy value for the given item, or {@code 0} if
     * the item is not a registered TF material.
     *
     * @param item the item to query
     * @return TF energy value, or 0
     */
    public static int getTfValue(ItemLike item) {
        return REGISTRY.getOrDefault(item.asItem(), 0);
    }

    /**
     * Returns the TF energy value for the given item stack.
     *
     * @param stack the item stack to query
     * @return TF energy value, or 0
     */
    public static int getTfValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        return getTfValue(stack.getItem());
    }

    /**
     * Returns whether the given item is a registered TF material.
     *
     * @param item the item to check
     * @return true if registered
     */
    public static boolean isTfMaterial(ItemLike item) {
        return REGISTRY.containsKey(item.asItem());
    }

    /**
     * Returns an unmodifiable view of all registered materials and their values.
     *
     * @return map of items to TF values
     */
    public static Map<Item, Integer> getAll() {
        return Map.copyOf(REGISTRY);
    }

    /**
     * Populates the registry with default TofuCraft material values.
     * Should be called once during {@code FMLCommonSetupEvent}.
     */
    public static void init() {
        if (initialised) return;
        initialised = true;

        ModLog.info("Initialising TF Material Registry");

        // Tofu food items (basic sources)
        register(TcItems.TOFU_KINU_FOOD, 10);
        register(TcItems.TOFU_MOMEN_FOOD, 14);
        register(TcItems.TOFU_ISHI_FOOD, 20);
        register(TcItems.TOFU_GRILLED_FOOD, 18);
        register(TcItems.TOFU_FRIED_POUCH_FOOD, 22);
        register(TcItems.TOFU_FRIED_FOOD, 22);
        register(TcItems.TOFU_EGG_FOOD, 26);
        register(TcItems.TOFU_ANNIN_FOOD, 24);
        register(TcItems.TOFU_SESAME_FOOD, 26);
        register(TcItems.TOFU_ZUNDA_FOOD, 28);
        register(TcItems.TOFU_STRAWBERRY_FOOD, 24);
        register(TcItems.TOFU_MISO_FOOD, 36);
        register(TcItems.TOFU_HELL_FOOD, 40);
        register(TcItems.TOFU_GLOW_FOOD, 30);

        // Tofu block items (higher value because they are 4x food)
        register(TcItems.TOFU_KINU_ITEM, 40);
        register(TcItems.TOFU_MOMEN_ITEM, 56);
        register(TcItems.TOFU_ISHI_ITEM, 80);
        register(TcItems.TOFU_METAL_ITEM, 160);
        register(TcItems.TOFU_GRILLED_ITEM, 72);
        register(TcItems.TOFU_DRIED_ITEM, 120);
        register(TcItems.TOFU_FRIED_POUCH_ITEM, 88);
        register(TcItems.TOFU_FRIED_ITEM, 88);
        register(TcItems.TOFU_EGG_ITEM, 104);
        register(TcItems.TOFU_ANNIN_ITEM, 96);
        register(TcItems.TOFU_SESAME_ITEM, 104);
        register(TcItems.TOFU_ZUNDA_ITEM, 112);
        register(TcItems.TOFU_STRAWBERRY_ITEM, 96);
        register(TcItems.TOFU_MISO_ITEM, 144);
        register(TcItems.TOFU_HELL_ITEM, 160);
        register(TcItems.TOFU_GLOW_ITEM, 120);
        register(TcItems.TOFU_DIAMOND_ITEM, 320);
        register(TcItems.TOFU_MINCED_ITEM, 30);

        // Nuggets
        register(TcItems.TOFU_DIAMOND_NUGGET, 36);
        register(TcItems.TOFU_METAL_NUGGET, 18);

        // Misc tofu-related materials
        register(TcItems.SOYBEANS, 4);
        register(TcItems.SOYBEANS_HELL, 8);
        register(TcItems.EDAMAME, 2);
        register(TcItems.OKARA, 6);

        ModLog.info("TF Material Registry initialised with {} entries", REGISTRY.size());
    }
}
