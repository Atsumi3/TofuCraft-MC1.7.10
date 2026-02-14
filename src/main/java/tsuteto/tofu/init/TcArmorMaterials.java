package tsuteto.tofu.init;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public enum TcArmorMaterials {
    KINU("kinu",
            5, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.CHESTPLATE, 2);
                map.put(ArmorItem.Type.LEGGINGS, 1);
                map.put(ArmorItem.Type.BOOTS, 1);
            }),
            10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
            () -> Ingredient.of(TcBlocks.TOFU_KINU.get())),

    MOMEN("momen",
            7, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.CHESTPLATE, 3);
                map.put(ArmorItem.Type.LEGGINGS, 2);
                map.put(ArmorItem.Type.BOOTS, 1);
            }),
            12, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
            () -> Ingredient.of(TcBlocks.TOFU_MOMEN.get())),

    SOLID("solid",
            15, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 2);
                map.put(ArmorItem.Type.CHESTPLATE, 5);
                map.put(ArmorItem.Type.LEGGINGS, 4);
                map.put(ArmorItem.Type.BOOTS, 2);
            }),
            15, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f,
            () -> Ingredient.of(TcBlocks.TOFU_ISHI.get())),

    METAL("metal",
            20, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 7);
                map.put(ArmorItem.Type.LEGGINGS, 5);
                map.put(ArmorItem.Type.BOOTS, 3);
            }),
            18, SoundEvents.ARMOR_EQUIP_IRON, 1.0f, 0.0f,
            () -> Ingredient.of(TcBlocks.TOFU_METAL.get())),

    DIAMOND_TOFU("diamond_tofu",
            33, Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.BOOTS, 3);
            }),
            25, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0f, 0.0f,
            () -> Ingredient.of(TcBlocks.TOFU_DIAMOND.get()));

    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, TofuCraftMod.MOD_ID);

    private final DeferredHolder<ArmorMaterial, ArmorMaterial> registryHolder;

    TcArmorMaterials(String name,
                     int durabilityMultiplier,
                     EnumMap<ArmorItem.Type, Integer> defense,
                     int enchantmentValue,
                     Holder<SoundEvent> equipSound,
                     float toughness,
                     float knockbackResistance,
                     Supplier<Ingredient> repairIngredient) {

        this.registryHolder = ARMOR_MATERIALS.register(name,
                () -> new ArmorMaterial(
                        durabilityMultiplier,
                        defense,
                        enchantmentValue,
                        equipSound,
                        toughness,
                        knockbackResistance,
                        repairIngredient,
                        List.of(new ArmorMaterial.Layer(
                                ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, name)))
                ));
    }

    public Holder<ArmorMaterial> holder() {
        return registryHolder;
    }

    public static void register(IEventBus modEventBus) {
        ARMOR_MATERIALS.register(modEventBus);
    }
}
