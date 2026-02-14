package tsuteto.tofu.init

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.*
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.item.*

object TcItems {
    @JvmField
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(TofuCraftMod.MOD_ID)

    // === Block Items (auto-registered with blocks) ===
    // Tofu blocks
    @JvmField val TOFU_KINU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_KINU)
    @JvmField val TOFU_MOMEN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MOMEN)
    @JvmField val TOFU_ISHI_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ISHI)
    @JvmField val TOFU_METAL_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_METAL)
    @JvmField val TOFU_GRILLED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_GRILLED)
    @JvmField val TOFU_DRIED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_DRIED)
    @JvmField val TOFU_FRIED_POUCH_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_FRIED_POUCH)
    @JvmField val TOFU_FRIED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_FRIED)
    @JvmField val TOFU_EGG_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_EGG)
    @JvmField val TOFU_ANNIN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ANNIN)
    @JvmField val TOFU_SESAME_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SESAME)
    @JvmField val TOFU_ZUNDA_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_ZUNDA)
    @JvmField val TOFU_STRAWBERRY_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STRAWBERRY)
    @JvmField val TOFU_MISO_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MISO)
    @JvmField val TOFU_HELL_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_HELL)
    @JvmField val TOFU_GLOW_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_GLOW)
    @JvmField val TOFU_DIAMOND_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_DIAMOND)
    @JvmField val TOFU_MINCED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_MINCED)

    // Stairs block items
    @JvmField val TOFU_STAIRS_KINU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_KINU)
    @JvmField val TOFU_STAIRS_MOMEN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_MOMEN)
    @JvmField val TOFU_STAIRS_ISHI_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_ISHI)
    @JvmField val TOFU_STAIRS_METAL_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_METAL)
    @JvmField val TOFU_STAIRS_GRILLED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_GRILLED)
    @JvmField val TOFU_STAIRS_DRIED_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_DRIED)
    @JvmField val TOFU_STAIRS_DIAMOND_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_DIAMOND)
    @JvmField val TOFU_STAIRS_MISO_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_STAIRS_MISO)

    // Slab block items
    @JvmField val TOFU_SLAB_KINU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_KINU)
    @JvmField val TOFU_SLAB_MOMEN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_MOMEN)
    @JvmField val TOFU_SLAB_ISHI_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_ISHI)
    @JvmField val TOFU_SLAB_METAL_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_METAL)
    @JvmField val TOFU_SLAB_DIAMOND_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_SLAB_DIAMOND)

    // Functional block items
    @JvmField val SALT_PAN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_PAN)
    @JvmField val SALT_FURNACE_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_FURNACE)
    @JvmField val MORIJIO_BLOCK_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.MORIJIO)
    @JvmField val BARREL_MISO_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_MISO)
    @JvmField val BARREL_MISO_TOFU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_MISO_TOFU)
    @JvmField val BARREL_GLOWTOFU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.BARREL_GLOWTOFU)
    @JvmField val TF_MACHINE_CASE_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_MACHINE_CASE)
    @JvmField val TF_STORAGE_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_STORAGE)
    @JvmField val TF_CONDENSER_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_CONDENSER)
    @JvmField val TF_OVEN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_OVEN)
    @JvmField val TF_REFORMER_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_REFORMER)
    @JvmField val TF_SATURATOR_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_SATURATOR)
    @JvmField val TF_COLLECTOR_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_COLLECTOR)
    @JvmField val TF_ANTENNA_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TF_ANTENNA)

    // Other block items
    @JvmField val TOFU_TERRAIN_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TOFU_TERRAIN)
    @JvmField val ORE_TOFU_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.ORE_TOFU)
    @JvmField val ORE_TOFU_DIAMOND_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.ORE_TOFU_DIAMOND)
    @JvmField val TC_LOG_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TC_LOG)
    @JvmField val TC_LEAVES_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TC_LEAVES)
    @JvmField val TC_SAPLING_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.TC_SAPLING)
    @JvmField val ADV_TOFU_GEM_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.ADV_TOFU_GEM)
    @JvmField val SALT_BLOCK_ITEM: DeferredItem<BlockItem> = ITEMS.registerSimpleBlockItem(TcBlocks.SALT_BLOCK)

    // === Tofu Food Items ===
    @JvmField val TOFU_KINU_FOOD = registerFood("tofu_kinu_food", 2, 0.1f, true)
    @JvmField val TOFU_MOMEN_FOOD = registerFood("tofu_momen_food", 2, 0.1f, true)
    @JvmField val TOFU_ISHI_FOOD = registerFood("tofu_ishi_food", 3, 0.4f, false)
    @JvmField val TOFU_GRILLED_FOOD = registerFood("tofu_grilled_food", 3, 0.2f, true)
    @JvmField val TOFU_FRIED_POUCH_FOOD = registerFood("tofu_fried_pouch_food", 4, 0.2f, true)
    @JvmField val TOFU_FRIED_FOOD = registerFood("tofu_fried_food", 4, 0.2f, true)
    @JvmField val TOFU_EGG_FOOD = registerFood("tofu_egg_food", 4, 0.2f, true)
    @JvmField val TOFU_ANNIN_FOOD = registerFood("tofu_annin_food", 4, 0.2f, true)
    @JvmField val TOFU_SESAME_FOOD = registerFood("tofu_sesame_food", 4, 0.2f, true)
    @JvmField val TOFU_ZUNDA_FOOD = registerFood("tofu_zunda_food", 4, 0.2f, true)
    @JvmField val TOFU_STRAWBERRY_FOOD = registerFood("tofu_strawberry_food", 3, 0.2f, true)
    @JvmField val TOFU_MISO_FOOD = registerFood("tofu_miso_food", 5, 0.8f, true)

    @JvmField
    val TOFU_HELL_FOOD: DeferredItem<Item> = ITEMS.register("tofu_hell_food") {
        Item(Item.Properties().food(
            FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).alwaysEdible()
                .effect({ MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600, 0) }, 1.0f)
                .build()))
    }

    @JvmField val TOFU_GLOW_FOOD = registerFood("tofu_glow_food", 2, 0.2f, true)

    // === Seeds & Crops ===
    @JvmField
    val SOYBEANS: DeferredItem<Item> = ITEMS.register("soybeans") {
        ItemNameBlockItem(TcBlocks.SOYBEAN.get(), Item.Properties())
    }

    @JvmField
    val SOYBEANS_HELL: DeferredItem<Item> = ITEMS.register("soybeans_hell") {
        ItemNameBlockItem(TcBlocks.SOYBEAN_HELL.get(), Item.Properties())
    }

    @JvmField val SESAME_SEEDS: DeferredItem<Item> = ITEMS.register("sesame_seeds") { Item(Item.Properties()) }
    @JvmField val APRICOT_SEED: DeferredItem<Item> = ITEMS.register("apricot_seed") { Item(Item.Properties()) }

    @JvmField
    val LEEK: DeferredItem<Item> = ITEMS.register("leek") {
        Item(Item.Properties().food(
            FoodProperties.Builder().nutrition(2).saturationModifier(0.3f).build()))
    }

    @JvmField val EDAMAME: DeferredItem<Item> = ITEMS.register("edamame") { Item(Item.Properties()) }
    @JvmField val EDAMAME_BOILED = registerFood("edamame_boiled", 1, 0.25f, true)

    // === Materials & Crafting ===
    @JvmField val SALT: DeferredItem<Item> = ITEMS.register("salt") { Item(Item.Properties()) }
    @JvmField val NIGARI: DeferredItem<Item> = ITEMS.register("nigari") { Item(Item.Properties()) }
    @JvmField val KOJI_BASE: DeferredItem<Item> = ITEMS.register("koji_base") { Item(Item.Properties()) }
    @JvmField val KOJI: DeferredItem<Item> = ITEMS.register("koji") { Item(Item.Properties()) }
    @JvmField val MISO: DeferredItem<Item> = ITEMS.register("miso") { Item(Item.Properties()) }
    @JvmField val NATTO: DeferredItem<Item> = ITEMS.register("natto") { Item(Item.Properties()) }
    @JvmField val ZUNDA: DeferredItem<Item> = ITEMS.register("zunda") { Item(Item.Properties()) }
    @JvmField val KINAKO: DeferredItem<Item> = ITEMS.register("kinako") { Item(Item.Properties()) }
    @JvmField val OKARA: DeferredItem<Item> = ITEMS.register("okara") { Item(Item.Properties()) }
    @JvmField val STARCH_RAW: DeferredItem<Item> = ITEMS.register("starch_raw") { Item(Item.Properties()) }
    @JvmField val STARCH: DeferredItem<Item> = ITEMS.register("starch") { Item(Item.Properties()) }
    @JvmField val FILTER_CLOTH: DeferredItem<Item> = ITEMS.register("filter_cloth") { Item(Item.Properties()) }
    @JvmField val ZUNDAMA: DeferredItem<Item> = ITEMS.register("zundama") { Item(Item.Properties()) }
    @JvmField val GELATIN: DeferredItem<Item> = ITEMS.register("gelatin") { Item(Item.Properties()) }
    @JvmField val KYONINSO: DeferredItem<Item> = ITEMS.register("kyoninso") { Item(Item.Properties()) }
    @JvmField val MINCED_POTATO: DeferredItem<Item> = ITEMS.register("minced_potato") { Item(Item.Properties()) }
    @JvmField val SOYBEANS_PARCHED: DeferredItem<Item> = ITEMS.register("soybeans_parched") { Item(Item.Properties()) }
    @JvmField val BARREL_EMPTY: DeferredItem<Item> = ITEMS.register("barrel_empty") { Item(Item.Properties()) }
    @JvmField val TOFU_DIAMOND_NUGGET: DeferredItem<Item> = ITEMS.register("tofu_diamond_nugget") { Item(Item.Properties()) }
    @JvmField val TOFU_METAL_NUGGET: DeferredItem<Item> = ITEMS.register("tofu_metal_nugget") { Item(Item.Properties()) }

    // === Fluid Buckets ===
    @JvmField
    val BUCKET_SOYMILK: DeferredItem<Item> = ITEMS.register("bucket_soymilk") {
        BucketItem(TcFluids.SOYMILK_SOURCE.get(), Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    }

    @JvmField
    val BUCKET_SOYMILK_HELL: DeferredItem<Item> = ITEMS.register("bucket_soymilk_hell") {
        BucketItem(TcFluids.SOYMILK_HELL_SOURCE.get(), Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    }

    @JvmField
    val BUCKET_SOY_SAUCE: DeferredItem<Item> = ITEMS.register("bucket_soy_sauce") {
        BucketItem(TcFluids.SOY_SAUCE_SOURCE.get(), Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    }

    // === Bottles ===
    @JvmField
    val BOTTLE_SOYMILK: DeferredItem<Item> = ITEMS.register("bottle_soymilk") {
        Item(Item.Properties().food(
            FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).alwaysEdible().build()))
    }

    @JvmField val BOTTLE_SOY_SAUCE: DeferredItem<Item> = ITEMS.register("bottle_soy_sauce") { Item(Item.Properties().stacksTo(16)) }
    @JvmField val DASHI: DeferredItem<Item> = ITEMS.register("dashi") { Item(Item.Properties().stacksTo(16)) }
    @JvmField val SOY_OIL: DeferredItem<Item> = ITEMS.register("soy_oil") { Item(Item.Properties().stacksTo(16)) }
    @JvmField val DOUBANJIANG: DeferredItem<Item> = ITEMS.register("doubanjiang") { Item(Item.Properties().stacksTo(16)) }
    @JvmField val STRAWBERRY_JAM: DeferredItem<Item> = ITEMS.register("strawberry_jam") { Item(Item.Properties().stacksTo(16)) }
    @JvmField val DEFATTING_POTION: DeferredItem<Item> = ITEMS.register("defatting_potion") { Item(Item.Properties().stacksTo(1)) }

    // === Cooked Foods & Dishes ===
    @JvmField val YUDOFU = registerFood("yudofu", 3, 0.3f, false)
    @JvmField val TTT_BURGER = registerFood("ttt_burger", 8, 0.4f, false)
    @JvmField val YUBA = registerFood("yuba", 1, 1.0f, true)
    @JvmField val MISO_SOUP = registerFood("miso_soup", 4, 0.6f, false)
    @JvmField val MISO_DENGAKU = registerFood("miso_dengaku", 5, 0.6f, false)
    @JvmField val SALTY_MELON = registerFood("salty_melon", 3, 0.5f, false)
    @JvmField val TASTY_STEW = registerFood("tasty_stew", 20, 1.0f, false)
    @JvmField val TASTY_BEEF_STEW = registerFood("tasty_beef_stew", 20, 1.0f, false)
    @JvmField val NIKUJAGA = registerFood("nikujaga", 10, 0.7f, false)
    @JvmField val AGEDASHI_TOFU = registerFood("agedashi_tofu", 6, 0.6f, false)
    @JvmField val KINAKO_MANJU = registerFood("kinako_manju", 4, 0.5f, false)
    @JvmField val ZUNDA_MANJU = registerFood("zunda_manju", 6, 0.8f, false)
    @JvmField val FUKUMENI = registerFood("fukumeni", 3, 1.0f, false)
    @JvmField val KOYADOFU_STEW = registerFood("koyadofu_stew", 8, 0.8f, false)
    @JvmField val APRICOT = registerFood("apricot", 3, 0.3f, false)
    @JvmField val OKARA_STICK = registerFood("okara_stick", 5, 0.6f, false)
    @JvmField val RICE_NATTO = registerFood("rice_natto", 8, 0.8f, false)
    @JvmField val RICE_NATTO_LEEK = registerFood("rice_natto_leek", 9, 0.8f, false)
    @JvmField val NATTO_HIYAYAKKO = registerFood("natto_hiyayakko", 8, 0.8f, false)

    @JvmField
    val TOFU_CAKE_ITEM: DeferredItem<Item> = ITEMS.register("tofu_cake") {
        BlockItem(TcBlocks.TOFU_CAKE.get(), Item.Properties().stacksTo(1))
    }

    // === Swords ===
    @JvmField val SWORD_KINU = registerSword("sword_kinu", Tiers.WOOD, 0)
    @JvmField val SWORD_MOMEN = registerSword("sword_momen", Tiers.WOOD, 1)
    @JvmField val SWORD_SOLID = registerSword("sword_solid", Tiers.STONE, 0)
    @JvmField val SWORD_METAL = registerSword("sword_metal", Tiers.IRON, 0)
    @JvmField val SWORD_DIAMOND = registerSword("sword_diamond", Tiers.DIAMOND, 1)

    // === Pickaxes ===
    @JvmField val PICKAXE_KINU = registerPickaxe("pickaxe_kinu", Tiers.WOOD)
    @JvmField val PICKAXE_MOMEN = registerPickaxe("pickaxe_momen", Tiers.WOOD)
    @JvmField val PICKAXE_SOLID = registerPickaxe("pickaxe_solid", Tiers.STONE)
    @JvmField val PICKAXE_METAL = registerPickaxe("pickaxe_metal", Tiers.IRON)
    @JvmField val PICKAXE_DIAMOND = registerPickaxe("pickaxe_diamond", Tiers.DIAMOND)

    // === Axes ===
    @JvmField val AXE_KINU = registerAxe("axe_kinu", Tiers.WOOD)
    @JvmField val AXE_MOMEN = registerAxe("axe_momen", Tiers.WOOD)
    @JvmField val AXE_SOLID = registerAxe("axe_solid", Tiers.STONE)
    @JvmField val AXE_METAL = registerAxe("axe_metal", Tiers.IRON)
    @JvmField val AXE_DIAMOND = registerAxe("axe_diamond", Tiers.DIAMOND)

    // === Shovels ===
    @JvmField val SHOVEL_KINU = registerShovel("shovel_kinu", Tiers.WOOD)
    @JvmField val SHOVEL_MOMEN = registerShovel("shovel_momen", Tiers.WOOD)
    @JvmField val SHOVEL_SOLID = registerShovel("shovel_solid", Tiers.STONE)
    @JvmField val SHOVEL_METAL = registerShovel("shovel_metal", Tiers.IRON)
    @JvmField val SHOVEL_DIAMOND = registerShovel("shovel_diamond", Tiers.DIAMOND)

    // === Hoe ===
    @JvmField
    val TOFU_HOE: DeferredItem<HoeItem> = ITEMS.register("tofu_hoe") {
        HoeItem(Tiers.DIAMOND, Item.Properties())
    }

    // === Armor ===
    @JvmField val ARMOR_KINU_HELMET = registerArmor("armor_kinu_helmet", TcArmorMaterials.KINU, ArmorItem.Type.HELMET)
    @JvmField val ARMOR_KINU_CHESTPLATE = registerArmor("armor_kinu_chestplate", TcArmorMaterials.KINU, ArmorItem.Type.CHESTPLATE)
    @JvmField val ARMOR_KINU_LEGGINGS = registerArmor("armor_kinu_leggings", TcArmorMaterials.KINU, ArmorItem.Type.LEGGINGS)
    @JvmField val ARMOR_KINU_BOOTS = registerArmor("armor_kinu_boots", TcArmorMaterials.KINU, ArmorItem.Type.BOOTS)

    @JvmField val ARMOR_MOMEN_HELMET = registerArmor("armor_momen_helmet", TcArmorMaterials.MOMEN, ArmorItem.Type.HELMET)
    @JvmField val ARMOR_MOMEN_CHESTPLATE = registerArmor("armor_momen_chestplate", TcArmorMaterials.MOMEN, ArmorItem.Type.CHESTPLATE)
    @JvmField val ARMOR_MOMEN_LEGGINGS = registerArmor("armor_momen_leggings", TcArmorMaterials.MOMEN, ArmorItem.Type.LEGGINGS)
    @JvmField val ARMOR_MOMEN_BOOTS = registerArmor("armor_momen_boots", TcArmorMaterials.MOMEN, ArmorItem.Type.BOOTS)

    @JvmField val ARMOR_SOLID_HELMET = registerArmor("armor_solid_helmet", TcArmorMaterials.SOLID, ArmorItem.Type.HELMET)
    @JvmField val ARMOR_SOLID_CHESTPLATE = registerArmor("armor_solid_chestplate", TcArmorMaterials.SOLID, ArmorItem.Type.CHESTPLATE)
    @JvmField val ARMOR_SOLID_LEGGINGS = registerArmor("armor_solid_leggings", TcArmorMaterials.SOLID, ArmorItem.Type.LEGGINGS)
    @JvmField val ARMOR_SOLID_BOOTS = registerArmor("armor_solid_boots", TcArmorMaterials.SOLID, ArmorItem.Type.BOOTS)

    @JvmField val ARMOR_METAL_HELMET = registerArmor("armor_metal_helmet", TcArmorMaterials.METAL, ArmorItem.Type.HELMET)
    @JvmField val ARMOR_METAL_CHESTPLATE = registerArmor("armor_metal_chestplate", TcArmorMaterials.METAL, ArmorItem.Type.CHESTPLATE)
    @JvmField val ARMOR_METAL_LEGGINGS = registerArmor("armor_metal_leggings", TcArmorMaterials.METAL, ArmorItem.Type.LEGGINGS)
    @JvmField val ARMOR_METAL_BOOTS = registerArmor("armor_metal_boots", TcArmorMaterials.METAL, ArmorItem.Type.BOOTS)

    @JvmField val ARMOR_DIAMOND_HELMET = registerArmor("armor_diamond_helmet", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.HELMET)
    @JvmField val ARMOR_DIAMOND_CHESTPLATE = registerArmor("armor_diamond_chestplate", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.CHESTPLATE)
    @JvmField val ARMOR_DIAMOND_LEGGINGS = registerArmor("armor_diamond_leggings", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.LEGGINGS)
    @JvmField val ARMOR_DIAMOND_BOOTS = registerArmor("armor_diamond_boots", TcArmorMaterials.DIAMOND_TOFU, ArmorItem.Type.BOOTS)

    // === Special Items ===
    @JvmField
    val ZUNDA_BOW: DeferredItem<Item> = ITEMS.register("zunda_bow") {
        ZundaBowItem(Item.Properties().durability(384))
    }

    @JvmField val ZUNDA_ARROW: DeferredItem<Item> = ITEMS.register("zunda_arrow") { Item(Item.Properties()) }
    @JvmField val TOFU_STICK: DeferredItem<Item> = ITEMS.register("tofu_stick") { Item(Item.Properties()) }

    @JvmField
    val BUGLE: DeferredItem<Item> = ITEMS.register("bugle") {
        TofuBugleItem(Item.Properties().stacksTo(1))
    }

    @JvmField
    val TOFU_RADAR: DeferredItem<Item> = ITEMS.register("tofu_radar") {
        TofuRadarItem(Item.Properties().stacksTo(1))
    }

    @JvmField
    val TOFU_SCOOP: DeferredItem<Item> = ITEMS.register("tofu_scoop") {
        TofuScoopItem(Item.Properties().durability(64))
    }

    @JvmField
    val FUKUMAME: DeferredItem<Item> = ITEMS.register("fukumame") {
        FukumameItem(Item.Properties())
    }

    @JvmField
    val GOLDEN_SALT: DeferredItem<Item> = ITEMS.register("golden_salt") {
        GoldenSaltItem(Item.Properties().durability(180))
    }

    @JvmField
    val MORIJIO_ITEM: DeferredItem<Item> = ITEMS.register("morijio") {
        BlockItem(TcBlocks.MORIJIO.get(), Item.Properties())
    }

    // === Helper methods ===
    private fun registerFood(name: String, nutrition: Int, saturation: Float, alwaysEdible: Boolean): DeferredItem<Item> =
        ITEMS.register(name) {
            val builder = FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
            if (alwaysEdible) builder.alwaysEdible()
            Item(Item.Properties().food(builder.build()))
        }

    private fun registerSword(name: String, tier: Tier, bonusDamage: Int): DeferredItem<SwordItem> =
        ITEMS.register(name) { SwordItem(tier, Item.Properties()) }

    private fun registerPickaxe(name: String, tier: Tier): DeferredItem<PickaxeItem> =
        ITEMS.register(name) { PickaxeItem(tier, Item.Properties()) }

    private fun registerAxe(name: String, tier: Tier): DeferredItem<AxeItem> =
        ITEMS.register(name) { AxeItem(tier, Item.Properties()) }

    private fun registerShovel(name: String, tier: Tier): DeferredItem<ShovelItem> =
        ITEMS.register(name) { ShovelItem(tier, Item.Properties()) }

    private fun registerArmor(name: String, material: TcArmorMaterials, type: ArmorItem.Type): DeferredItem<ArmorItem> =
        ITEMS.register(name) { ArmorItem(material.holder(), type, Item.Properties()) }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        ITEMS.register(modEventBus)
    }
}
