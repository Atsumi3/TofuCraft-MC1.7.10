package tsuteto.tofu.init;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.item.*;

public class TcItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TofuCraftMod.MOD_ID);

    // === Block Items (auto-registered with blocks) ===
    // Tofu blocks
    public static final DeferredItem<BlockItem> TOFU_KINU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_KINU);
    public static final DeferredItem<BlockItem> TOFU_MOMEN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MOMEN);
    public static final DeferredItem<BlockItem> TOFU_ISHI_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ISHI);
    public static final DeferredItem<BlockItem> TOFU_METAL_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_METAL);
    public static final DeferredItem<BlockItem> TOFU_GRILLED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_GRILLED);
    public static final DeferredItem<BlockItem> TOFU_DRIED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_DRIED);
    public static final DeferredItem<BlockItem> TOFU_FRIED_POUCH_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_FRIED_POUCH);
    public static final DeferredItem<BlockItem> TOFU_FRIED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_FRIED);
    public static final DeferredItem<BlockItem> TOFU_EGG_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_EGG);
    public static final DeferredItem<BlockItem> TOFU_ANNIN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ANNIN);
    public static final DeferredItem<BlockItem> TOFU_SESAME_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SESAME);
    public static final DeferredItem<BlockItem> TOFU_ZUNDA_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ZUNDA);
    public static final DeferredItem<BlockItem> TOFU_STRAWBERRY_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STRAWBERRY);
    public static final DeferredItem<BlockItem> TOFU_MISO_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MISO);
    public static final DeferredItem<BlockItem> TOFU_HELL_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_HELL);
    public static final DeferredItem<BlockItem> TOFU_GLOW_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_GLOW);
    public static final DeferredItem<BlockItem> TOFU_DIAMOND_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_DIAMOND);
    public static final DeferredItem<BlockItem> TOFU_MINCED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MINCED);

    // Stairs block items
    public static final DeferredItem<BlockItem> TOFU_STAIRS_KINU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_KINU);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_MOMEN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_MOMEN);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_ISHI_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_ISHI);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_METAL_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_METAL);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_GRILLED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_GRILLED);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_DRIED_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_DRIED);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_DIAMOND_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_DIAMOND);
    public static final DeferredItem<BlockItem> TOFU_STAIRS_MISO_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_MISO);

    // Slab block items
    public static final DeferredItem<BlockItem> TOFU_SLAB_KINU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_KINU);
    public static final DeferredItem<BlockItem> TOFU_SLAB_MOMEN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_MOMEN);
    public static final DeferredItem<BlockItem> TOFU_SLAB_ISHI_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_ISHI);
    public static final DeferredItem<BlockItem> TOFU_SLAB_METAL_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_METAL);
    public static final DeferredItem<BlockItem> TOFU_SLAB_DIAMOND_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_DIAMOND);

    // Functional block items
    public static final DeferredItem<BlockItem> SALT_PAN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_PAN);
    public static final DeferredItem<BlockItem> SALT_FURNACE_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_FURNACE);
    public static final DeferredItem<BlockItem> MORIJIO_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.MORIJIO);
    public static final DeferredItem<BlockItem> BARREL_MISO_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_MISO);
    public static final DeferredItem<BlockItem> BARREL_MISO_TOFU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_MISO_TOFU);
    public static final DeferredItem<BlockItem> BARREL_GLOWTOFU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_GLOWTOFU);
    public static final DeferredItem<BlockItem> TF_MACHINE_CASE_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_MACHINE_CASE);
    public static final DeferredItem<BlockItem> TF_STORAGE_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_STORAGE);
    public static final DeferredItem<BlockItem> TF_CONDENSER_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_CONDENSER);
    public static final DeferredItem<BlockItem> TF_OVEN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_OVEN);
    public static final DeferredItem<BlockItem> TF_REFORMER_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_REFORMER);
    public static final DeferredItem<BlockItem> TF_SATURATOR_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_SATURATOR);
    public static final DeferredItem<BlockItem> TF_COLLECTOR_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_COLLECTOR);
    public static final DeferredItem<BlockItem> TF_ANTENNA_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TF_ANTENNA);

    // Other block items
    public static final DeferredItem<BlockItem> TOFU_TERRAIN_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_TERRAIN);
    public static final DeferredItem<BlockItem> ORE_TOFU_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.ORE_TOFU);
    public static final DeferredItem<BlockItem> ORE_TOFU_DIAMOND_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.ORE_TOFU_DIAMOND);
    public static final DeferredItem<BlockItem> TC_LOG_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TC_LOG);
    public static final DeferredItem<BlockItem> TC_LEAVES_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TC_LEAVES);
    public static final DeferredItem<BlockItem> TC_SAPLING_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.TC_SAPLING);
    public static final DeferredItem<BlockItem> ADV_TOFU_GEM_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.ADV_TOFU_GEM);
    public static final DeferredItem<BlockItem> SALT_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_BLOCK);

    // === Tofu Food Items ===
    public static final DeferredItem<Item> TOFU_KINU_FOOD = registerFood("tofu_kinu_food", 2, 0.1f, true);
    public static final DeferredItem<Item> TOFU_MOMEN_FOOD = registerFood("tofu_momen_food", 2, 0.1f, true);
    public static final DeferredItem<Item> TOFU_ISHI_FOOD = registerFood("tofu_ishi_food", 3, 0.4f, false);
    public static final DeferredItem<Item> TOFU_GRILLED_FOOD = registerFood("tofu_grilled_food", 3, 0.2f, true);
    public static final DeferredItem<Item> TOFU_FRIED_POUCH_FOOD = registerFood("tofu_fried_pouch_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_FRIED_FOOD = registerFood("tofu_fried_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_EGG_FOOD = registerFood("tofu_egg_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_ANNIN_FOOD = registerFood("tofu_annin_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_SESAME_FOOD = registerFood("tofu_sesame_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_ZUNDA_FOOD = registerFood("tofu_zunda_food", 4, 0.2f, true);
    public static final DeferredItem<Item> TOFU_STRAWBERRY_FOOD = registerFood("tofu_strawberry_food", 3, 0.2f, true);
    public static final DeferredItem<Item> TOFU_MISO_FOOD = registerFood("tofu_miso_food", 5, 0.8f, true);
    public static final DeferredItem<Item> TOFU_HELL_FOOD = ITEMS.register("tofu_hell_food",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible()
                            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0), 1.0f)
                            .build())));
    public static final DeferredItem<Item> TOFU_GLOW_FOOD = registerFood("tofu_glow_food", 2, 0.2f, true);

    // === Seeds & Crops ===
    public static final DeferredItem<Item> SOYBEANS = ITEMS.register("soybeans",
            () -> new ItemNameBlockItem(TcBlocks.SOYBEAN.get(), new Item.Properties()));
    public static final DeferredItem<Item> SOYBEANS_HELL = ITEMS.register("soybeans_hell",
            () -> new ItemNameBlockItem(TcBlocks.SOYBEAN_HELL.get(), new Item.Properties()));
    public static final DeferredItem<Item> SESAME_SEEDS = ITEMS.register("sesame_seeds",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> APRICOT_SEED = ITEMS.register("apricot_seed",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEEK = ITEMS.register("leek",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build())));
    public static final DeferredItem<Item> EDAMAME = ITEMS.register("edamame",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> EDAMAME_BOILED = registerFood("edamame_boiled", 1, 0.25f, true);

    // === Materials & Crafting ===
    public static final DeferredItem<Item> SALT = ITEMS.register("salt", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NIGARI = ITEMS.register("nigari", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> KOJI_BASE = ITEMS.register("koji_base", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> KOJI = ITEMS.register("koji", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MISO = ITEMS.register("miso", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> NATTO = ITEMS.register("natto", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZUNDA = ITEMS.register("zunda", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> KINAKO = ITEMS.register("kinako", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> OKARA = ITEMS.register("okara", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STARCH_RAW = ITEMS.register("starch_raw", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STARCH = ITEMS.register("starch", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FILTER_CLOTH = ITEMS.register("filter_cloth", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ZUNDAMA = ITEMS.register("zundama", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GELATIN = ITEMS.register("gelatin", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> KYONINSO = ITEMS.register("kyoninso", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MINCED_POTATO = ITEMS.register("minced_potato", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SOYBEANS_PARCHED = ITEMS.register("soybeans_parched", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BARREL_EMPTY = ITEMS.register("barrel_empty", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOFU_DIAMOND_NUGGET = ITEMS.register("tofu_diamond_nugget", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOFU_METAL_NUGGET = ITEMS.register("tofu_metal_nugget", () -> new Item(new Item.Properties()));

    // === Fluid Buckets ===
    public static final DeferredItem<Item> BUCKET_SOYMILK = ITEMS.register("bucket_soymilk",
            () -> new BucketItem(TcFluids.SOYMILK_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> BUCKET_SOYMILK_HELL = ITEMS.register("bucket_soymilk_hell",
            () -> new BucketItem(TcFluids.SOYMILK_HELL_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<Item> BUCKET_SOY_SAUCE = ITEMS.register("bucket_soy_sauce",
            () -> new BucketItem(TcFluids.SOY_SAUCE_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    // === Bottles ===
    public static final DeferredItem<Item> BOTTLE_SOYMILK = ITEMS.register("bottle_soymilk",
            () -> new Item(new Item.Properties().food(
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible().build())));
    public static final DeferredItem<Item> BOTTLE_SOY_SAUCE = ITEMS.register("bottle_soy_sauce",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DASHI = ITEMS.register("dashi",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> SOY_OIL = ITEMS.register("soy_oil",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DOUBANJIANG = ITEMS.register("doubanjiang",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> STRAWBERRY_JAM = ITEMS.register("strawberry_jam",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> DEFATTING_POTION = ITEMS.register("defatting_potion",
            () -> new Item(new Item.Properties().stacksTo(1)));

    // === Cooked Foods & Dishes ===
    public static final DeferredItem<Item> YUDOFU = registerFood("yudofu", 3, 0.3f, false);
    public static final DeferredItem<Item> TTT_BURGER = registerFood("ttt_burger", 8, 0.4f, false);
    public static final DeferredItem<Item> YUBA = registerFood("yuba", 1, 1.0f, true);
    public static final DeferredItem<Item> MISO_SOUP = registerFood("miso_soup", 4, 0.6f, false);
    public static final DeferredItem<Item> MISO_DENGAKU = registerFood("miso_dengaku", 5, 0.6f, false);
    public static final DeferredItem<Item> SALTY_MELON = registerFood("salty_melon", 3, 0.5f, false);
    public static final DeferredItem<Item> TASTY_STEW = registerFood("tasty_stew", 20, 1.0f, false);
    public static final DeferredItem<Item> TASTY_BEEF_STEW = registerFood("tasty_beef_stew", 20, 1.0f, false);
    public static final DeferredItem<Item> NIKUJAGA = registerFood("nikujaga", 10, 0.7f, false);
    public static final DeferredItem<Item> AGEDASHI_TOFU = registerFood("agedashi_tofu", 6, 0.6f, false);
    public static final DeferredItem<Item> KINAKO_MANJU = registerFood("kinako_manju", 4, 0.5f, false);
    public static final DeferredItem<Item> ZUNDA_MANJU = registerFood("zunda_manju", 6, 0.8f, false);
    public static final DeferredItem<Item> FUKUMENI = registerFood("fukumeni", 3, 1.0f, false);
    public static final DeferredItem<Item> KOYADOFU_STEW = registerFood("koyadofu_stew", 8, 0.8f, false);
    public static final DeferredItem<Item> APRICOT = registerFood("apricot", 3, 0.3f, false);
    public static final DeferredItem<Item> OKARA_STICK = registerFood("okara_stick", 5, 0.6f, false);
    public static final DeferredItem<Item> RICE_NATTO = registerFood("rice_natto", 8, 0.8f, false);
    public static final DeferredItem<Item> RICE_NATTO_LEEK = registerFood("rice_natto_leek", 9, 0.8f, false);
    public static final DeferredItem<Item> NATTO_HIYAYAKKO = registerFood("natto_hiyayakko", 8, 0.8f, false);
    public static final DeferredItem<Item> TOFU_CAKE_ITEM = ITEMS.register("tofu_cake",
            () -> new BlockItem(TcBlocks.TOFU_CAKE.get(), new Item.Properties().stacksTo(1)));

    // === Swords ===
    public static final DeferredItem<SwordItem> SWORD_KINU = registerSword("sword_kinu", Tiers.WOOD, 0);
    public static final DeferredItem<SwordItem> SWORD_MOMEN = registerSword("sword_momen", Tiers.WOOD, 1);
    public static final DeferredItem<SwordItem> SWORD_SOLID = registerSword("sword_solid", Tiers.STONE, 0);
    public static final DeferredItem<SwordItem> SWORD_METAL = registerSword("sword_metal", Tiers.IRON, 0);
    public static final DeferredItem<SwordItem> SWORD_DIAMOND = registerSword("sword_diamond", Tiers.DIAMOND, 1);

    // === Pickaxes ===
    public static final DeferredItem<PickaxeItem> PICKAXE_KINU = registerPickaxe("pickaxe_kinu", Tiers.WOOD);
    public static final DeferredItem<PickaxeItem> PICKAXE_MOMEN = registerPickaxe("pickaxe_momen", Tiers.WOOD);
    public static final DeferredItem<PickaxeItem> PICKAXE_SOLID = registerPickaxe("pickaxe_solid", Tiers.STONE);
    public static final DeferredItem<PickaxeItem> PICKAXE_METAL = registerPickaxe("pickaxe_metal", Tiers.IRON);
    public static final DeferredItem<PickaxeItem> PICKAXE_DIAMOND = registerPickaxe("pickaxe_diamond", Tiers.DIAMOND);

    // === Axes ===
    public static final DeferredItem<AxeItem> AXE_KINU = registerAxe("axe_kinu", Tiers.WOOD);
    public static final DeferredItem<AxeItem> AXE_MOMEN = registerAxe("axe_momen", Tiers.WOOD);
    public static final DeferredItem<AxeItem> AXE_SOLID = registerAxe("axe_solid", Tiers.STONE);
    public static final DeferredItem<AxeItem> AXE_METAL = registerAxe("axe_metal", Tiers.IRON);
    public static final DeferredItem<AxeItem> AXE_DIAMOND = registerAxe("axe_diamond", Tiers.DIAMOND);

    // === Shovels ===
    public static final DeferredItem<ShovelItem> SHOVEL_KINU = registerShovel("shovel_kinu", Tiers.WOOD);
    public static final DeferredItem<ShovelItem> SHOVEL_MOMEN = registerShovel("shovel_momen", Tiers.WOOD);
    public static final DeferredItem<ShovelItem> SHOVEL_SOLID = registerShovel("shovel_solid", Tiers.STONE);
    public static final DeferredItem<ShovelItem> SHOVEL_METAL = registerShovel("shovel_metal", Tiers.IRON);
    public static final DeferredItem<ShovelItem> SHOVEL_DIAMOND = registerShovel("shovel_diamond", Tiers.DIAMOND);

    // === Hoe ===
    public static final DeferredItem<HoeItem> TOFU_HOE = ITEMS.register("tofu_hoe",
            () -> new HoeItem(Tiers.DIAMOND, new Item.Properties()));

    // === Armor ===
    public static final DeferredItem<ArmorItem> ARMOR_KINU_HELMET = registerArmor("armor_kinu_helmet", TcArmorMaterials.KINU, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ARMOR_KINU_CHESTPLATE = registerArmor("armor_kinu_chestplate", TcArmorMaterials.KINU, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ARMOR_KINU_LEGGINGS = registerArmor("armor_kinu_leggings", TcArmorMaterials.KINU, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ARMOR_KINU_BOOTS = registerArmor("armor_kinu_boots", TcArmorMaterials.KINU, ArmorItem.Type.BOOTS);

    public static final DeferredItem<ArmorItem> ARMOR_MOMEN_HELMET = registerArmor("armor_momen_helmet", TcArmorMaterials.MOMEN, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ARMOR_MOMEN_CHESTPLATE = registerArmor("armor_momen_chestplate", TcArmorMaterials.MOMEN, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ARMOR_MOMEN_LEGGINGS = registerArmor("armor_momen_leggings", TcArmorMaterials.MOMEN, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ARMOR_MOMEN_BOOTS = registerArmor("armor_momen_boots", TcArmorMaterials.MOMEN, ArmorItem.Type.BOOTS);

    public static final DeferredItem<ArmorItem> ARMOR_SOLID_HELMET = registerArmor("armor_solid_helmet", TcArmorMaterials.SOLID, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ARMOR_SOLID_CHESTPLATE = registerArmor("armor_solid_chestplate", TcArmorMaterials.SOLID, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ARMOR_SOLID_LEGGINGS = registerArmor("armor_solid_leggings", TcArmorMaterials.SOLID, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ARMOR_SOLID_BOOTS = registerArmor("armor_solid_boots", TcArmorMaterials.SOLID, ArmorItem.Type.BOOTS);

    public static final DeferredItem<ArmorItem> ARMOR_METAL_HELMET = registerArmor("armor_metal_helmet", TcArmorMaterials.METAL, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ARMOR_METAL_CHESTPLATE = registerArmor("armor_metal_chestplate", TcArmorMaterials.METAL, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ARMOR_METAL_LEGGINGS = registerArmor("armor_metal_leggings", TcArmorMaterials.METAL, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ARMOR_METAL_BOOTS = registerArmor("armor_metal_boots", TcArmorMaterials.METAL, ArmorItem.Type.BOOTS);

    public static final DeferredItem<ArmorItem> ARMOR_DIAMOND_HELMET = registerArmor("armor_diamond_helmet", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> ARMOR_DIAMOND_CHESTPLATE = registerArmor("armor_diamond_chestplate", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> ARMOR_DIAMOND_LEGGINGS = registerArmor("armor_diamond_leggings", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> ARMOR_DIAMOND_BOOTS = registerArmor("armor_diamond_boots", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.BOOTS);

    // === Special Items ===
    public static final DeferredItem<Item> ZUNDA_BOW = ITEMS.register("zunda_bow",
            () -> new ZundaBowItem(new Item.Properties().durability(384)));
    public static final DeferredItem<Item> ZUNDA_ARROW = ITEMS.register("zunda_arrow",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> TOFU_STICK = ITEMS.register("tofu_stick",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BUGLE = ITEMS.register("bugle",
            () -> new TofuBugleItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TOFU_RADAR = ITEMS.register("tofu_radar",
            () -> new TofuRadarItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TOFU_SCOOP = ITEMS.register("tofu_scoop",
            () -> new TofuScoopItem(new Item.Properties().durability(64)));
    public static final DeferredItem<Item> FUKUMAME = ITEMS.register("fukumame",
            () -> new FukumameItem(new Item.Properties()));
    public static final DeferredItem<Item> GOLDEN_SALT = ITEMS.register("golden_salt",
            () -> new GoldenSaltItem(new Item.Properties().durability(180)));
    public static final DeferredItem<Item> MORIJIO_ITEM = ITEMS.register("morijio",
            () -> new BlockItem(TcBlocks.MORIJIO.get(), new Item.Properties()));

    // === Helper methods ===
    private static DeferredItem<Item> registerFood(String name, int nutrition, float saturation, boolean alwaysEdible) {
        return ITEMS.register(name, () -> {
            FoodProperties.Builder builder = new FoodProperties.Builder()
                    .nutrition(nutrition)
                    .saturationModifier(saturation);
            if (alwaysEdible) builder.alwaysEdible();
            return new Item(new Item.Properties().food(builder.build()));
        });
    }

    private static DeferredItem<SwordItem> registerSword(String name, Tier tier, int bonusDamage) {
        return ITEMS.register(name, () -> new SwordItem(tier, new Item.Properties()));
    }

    private static DeferredItem<PickaxeItem> registerPickaxe(String name, Tier tier) {
        return ITEMS.register(name, () -> new PickaxeItem(tier, new Item.Properties()));
    }

    private static DeferredItem<AxeItem> registerAxe(String name, Tier tier) {
        return ITEMS.register(name, () -> new AxeItem(tier, new Item.Properties()));
    }

    private static DeferredItem<ShovelItem> registerShovel(String name, Tier tier) {
        return ITEMS.register(name, () -> new ShovelItem(tier, new Item.Properties()));
    }

    private static DeferredItem<ArmorItem> registerArmor(String name, TcArmorMaterials material, ArmorItem.Type type) {
        return ITEMS.register(name, () -> new ArmorItem(material.holder(), type, new Item.Properties()));
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
