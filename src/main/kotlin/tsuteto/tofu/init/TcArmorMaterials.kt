package tsuteto.tofu.init

import net.minecraft.Util
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import java.util.EnumMap
import java.util.function.Supplier

enum class TcArmorMaterials(
    materialName: String,
    durabilityMultiplier: Int,
    defense: EnumMap<ArmorItem.Type, Int>,
    enchantmentValue: Int,
    equipSound: Holder<SoundEvent>,
    toughness: Float,
    knockbackResistance: Float,
    repairIngredient: Supplier<Ingredient>
) {
    KINU("kinu",
        5, Util.make(EnumMap(ArmorItem.Type::class.java)) { map ->
            map[ArmorItem.Type.HELMET] = 1
            map[ArmorItem.Type.CHESTPLATE] = 2
            map[ArmorItem.Type.LEGGINGS] = 1
            map[ArmorItem.Type.BOOTS] = 1
        },
        10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
        Supplier { Ingredient.of(TcBlocks.TOFU_KINU.get()) }),

    MOMEN("momen",
        7, Util.make(EnumMap(ArmorItem.Type::class.java)) { map ->
            map[ArmorItem.Type.HELMET] = 1
            map[ArmorItem.Type.CHESTPLATE] = 3
            map[ArmorItem.Type.LEGGINGS] = 2
            map[ArmorItem.Type.BOOTS] = 1
        },
        12, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
        Supplier { Ingredient.of(TcBlocks.TOFU_MOMEN.get()) }),

    SOLID("solid",
        15, Util.make(EnumMap(ArmorItem.Type::class.java)) { map ->
            map[ArmorItem.Type.HELMET] = 2
            map[ArmorItem.Type.CHESTPLATE] = 5
            map[ArmorItem.Type.LEGGINGS] = 4
            map[ArmorItem.Type.BOOTS] = 2
        },
        15, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
        Supplier { Ingredient.of(TcBlocks.TOFU_ISHI.get()) }),

    METAL("metal",
        20, Util.make(EnumMap(ArmorItem.Type::class.java)) { map ->
            map[ArmorItem.Type.HELMET] = 3
            map[ArmorItem.Type.CHESTPLATE] = 7
            map[ArmorItem.Type.LEGGINGS] = 5
            map[ArmorItem.Type.BOOTS] = 3
        },
        18, SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f,
        Supplier { Ingredient.of(TcBlocks.TOFU_METAL.get()) }),

    DIAMOND_TOFU("diamond_tofu",
        33, Util.make(EnumMap(ArmorItem.Type::class.java)) { map ->
            map[ArmorItem.Type.HELMET] = 3
            map[ArmorItem.Type.CHESTPLATE] = 8
            map[ArmorItem.Type.LEGGINGS] = 6
            map[ArmorItem.Type.BOOTS] = 3
        },
        25, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f,
        Supplier { Ingredient.of(TcBlocks.TOFU_DIAMOND.get()) });

    private val registryHolder: DeferredHolder<ArmorMaterial, ArmorMaterial>

    init {
        registryHolder = ARMOR_MATERIALS.register(materialName) {
            ArmorMaterial(
                durabilityMultiplier,
                defense,
                enchantmentValue,
                equipSound,
                toughness,
                knockbackResistance,
                repairIngredient,
                listOf(ArmorMaterial.Layer(
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, materialName)))
            )
        }
    }

    fun holder(): Holder<ArmorMaterial> = registryHolder

    companion object {
        private val ARMOR_MATERIALS: DeferredRegister<ArmorMaterial> =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, TofuCraftMod.MOD_ID)

        @JvmStatic
        fun register(modEventBus: IEventBus) {
            ARMOR_MATERIALS.register(modEventBus)
        }
    }
}
