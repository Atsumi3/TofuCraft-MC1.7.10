package tsuteto.tofu.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

import java.util.function.Supplier;

/**
 * Enum defining all tofu material types in TofuCraft.
 * Each variant has an internal name, a hardness rating, and
 * lazy references to its corresponding block and food item.
 */
public enum TofuMaterial {

    KINU("kinu", 0.3f,
            () -> TcBlocks.TOFU_KINU.get(),
            () -> TcItems.TOFU_KINU_FOOD.get()),

    MOMEN("momen", 0.5f,
            () -> TcBlocks.TOFU_MOMEN.get(),
            () -> TcItems.TOFU_MOMEN_FOOD.get()),

    ISHI("ishi", 1.0f,
            () -> TcBlocks.TOFU_ISHI.get(),
            () -> TcItems.TOFU_ISHI_FOOD.get()),

    METAL("metal", 3.0f,
            () -> TcBlocks.TOFU_METAL.get(),
            null),

    GRILLED("grilled", 0.6f,
            () -> TcBlocks.TOFU_GRILLED.get(),
            () -> TcItems.TOFU_GRILLED_FOOD.get()),

    DRIED("dried", 1.5f,
            () -> TcBlocks.TOFU_DRIED.get(),
            null),

    FRIED_POUCH("fried_pouch", 0.6f,
            () -> TcBlocks.TOFU_FRIED_POUCH.get(),
            () -> TcItems.TOFU_FRIED_POUCH_FOOD.get()),

    FRIED("fried", 0.6f,
            () -> TcBlocks.TOFU_FRIED.get(),
            () -> TcItems.TOFU_FRIED_FOOD.get()),

    EGG("egg", 0.6f,
            () -> TcBlocks.TOFU_EGG.get(),
            () -> TcItems.TOFU_EGG_FOOD.get()),

    ANNIN("annin", 0.4f,
            () -> TcBlocks.TOFU_ANNIN.get(),
            () -> TcItems.TOFU_ANNIN_FOOD.get()),

    SESAME("sesame", 0.6f,
            () -> TcBlocks.TOFU_SESAME.get(),
            () -> TcItems.TOFU_SESAME_FOOD.get()),

    ZUNDA("zunda", 0.6f,
            () -> TcBlocks.TOFU_ZUNDA.get(),
            () -> TcItems.TOFU_ZUNDA_FOOD.get()),

    STRAWBERRY("strawberry", 0.4f,
            () -> TcBlocks.TOFU_STRAWBERRY.get(),
            () -> TcItems.TOFU_STRAWBERRY_FOOD.get()),

    MISO("miso", 1.0f,
            () -> TcBlocks.TOFU_MISO.get(),
            () -> TcItems.TOFU_MISO_FOOD.get()),

    HELL("hell", 1.0f,
            () -> TcBlocks.TOFU_HELL.get(),
            () -> TcItems.TOFU_HELL_FOOD.get()),

    GLOW("glow", 0.5f,
            () -> TcBlocks.TOFU_GLOW.get(),
            () -> TcItems.TOFU_GLOW_FOOD.get()),

    DIAMOND("diamond", 5.0f,
            () -> TcBlocks.TOFU_DIAMOND.get(),
            null);

    private final String name;
    private final float hardness;
    private final Supplier<Block> blockSupplier;
    private final Supplier<Item> foodSupplier;

    TofuMaterial(String name, float hardness,
                 Supplier<Block> blockSupplier,
                 Supplier<Item> foodSupplier) {
        this.name = name;
        this.hardness = hardness;
        this.blockSupplier = blockSupplier;
        this.foodSupplier = foodSupplier;
    }

    /**
     * Returns the internal name of this tofu material (e.g. "kinu", "momen").
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hardness rating of this tofu material.
     * This corresponds to the block destroy time.
     */
    public float getHardness() {
        return hardness;
    }

    /**
     * Returns the block associated with this tofu material.
     *
     * @return the tofu block
     */
    public Block getBlock() {
        return blockSupplier.get();
    }

    /**
     * Returns the food item associated with this tofu material,
     * or {@code null} if this material has no food form
     * (e.g. METAL, DRIED, DIAMOND).
     *
     * @return the food item, or null
     */
    public Item getFoodItem() {
        return foodSupplier != null ? foodSupplier.get() : null;
    }

    /**
     * Returns whether this tofu material has an edible food item.
     */
    public boolean hasFood() {
        return foodSupplier != null;
    }

    /**
     * Looks up a tofu material by its internal name.
     *
     * @param name the name to search for (case-insensitive)
     * @return the matching material, or null if not found
     */
    public static TofuMaterial byName(String name) {
        for (TofuMaterial material : values()) {
            if (material.name.equalsIgnoreCase(name)) {
                return material;
            }
        }
        return null;
    }
}
