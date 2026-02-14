package tsuteto.tofu.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.init.TcItems;

public class TcItemModelProvider extends ItemModelProvider {

    public TcItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TofuCraftMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // === Tofu food items ===
        basicItem(TcItems.TOFU_KINU_FOOD.get());
        basicItem(TcItems.TOFU_MOMEN_FOOD.get());
        basicItem(TcItems.TOFU_ISHI_FOOD.get());
        basicItem(TcItems.TOFU_GRILLED_FOOD.get());
        basicItem(TcItems.TOFU_FRIED_POUCH_FOOD.get());
        basicItem(TcItems.TOFU_FRIED_FOOD.get());
        basicItem(TcItems.TOFU_EGG_FOOD.get());
        basicItem(TcItems.TOFU_ANNIN_FOOD.get());
        basicItem(TcItems.TOFU_SESAME_FOOD.get());
        basicItem(TcItems.TOFU_ZUNDA_FOOD.get());
        basicItem(TcItems.TOFU_STRAWBERRY_FOOD.get());
        basicItem(TcItems.TOFU_MISO_FOOD.get());
        basicItem(TcItems.TOFU_HELL_FOOD.get());
        basicItem(TcItems.TOFU_GLOW_FOOD.get());

        // === Seeds & crops ===
        basicItem(TcItems.SOYBEANS.get());
        basicItem(TcItems.SOYBEANS_HELL.get());
        basicItem(TcItems.SESAME_SEEDS.get());
        basicItem(TcItems.APRICOT_SEED.get());
        basicItem(TcItems.LEEK.get());
        basicItem(TcItems.EDAMAME.get());
        basicItem(TcItems.EDAMAME_BOILED.get());

        // === Materials & crafting ===
        basicItem(TcItems.SALT.get());
        basicItem(TcItems.NIGARI.get());
        basicItem(TcItems.KOJI_BASE.get());
        basicItem(TcItems.KOJI.get());
        basicItem(TcItems.MISO.get());
        basicItem(TcItems.NATTO.get());
        basicItem(TcItems.ZUNDA.get());
        basicItem(TcItems.KINAKO.get());
        basicItem(TcItems.OKARA.get());
        basicItem(TcItems.STARCH_RAW.get());
        basicItem(TcItems.STARCH.get());
        basicItem(TcItems.FILTER_CLOTH.get());
        basicItem(TcItems.ZUNDAMA.get());
        basicItem(TcItems.GELATIN.get());
        basicItem(TcItems.KYONINSO.get());
        basicItem(TcItems.MINCED_POTATO.get());
        basicItem(TcItems.SOYBEANS_PARCHED.get());
        basicItem(TcItems.BARREL_EMPTY.get());
        basicItem(TcItems.TOFU_DIAMOND_NUGGET.get());
        basicItem(TcItems.TOFU_METAL_NUGGET.get());
        basicItem(TcItems.TOFU_STICK.get());

        // === Fluid buckets ===
        basicItem(TcItems.BUCKET_SOYMILK.get());
        basicItem(TcItems.BUCKET_SOYMILK_HELL.get());
        basicItem(TcItems.BUCKET_SOY_SAUCE.get());

        // === Bottles ===
        basicItem(TcItems.BOTTLE_SOYMILK.get());
        basicItem(TcItems.BOTTLE_SOY_SAUCE.get());
        basicItem(TcItems.DASHI.get());
        basicItem(TcItems.SOY_OIL.get());
        basicItem(TcItems.DOUBANJIANG.get());
        basicItem(TcItems.STRAWBERRY_JAM.get());
        basicItem(TcItems.DEFATTING_POTION.get());

        // === Cooked foods & dishes ===
        basicItem(TcItems.YUDOFU.get());
        basicItem(TcItems.TTT_BURGER.get());
        basicItem(TcItems.YUBA.get());
        basicItem(TcItems.MISO_SOUP.get());
        basicItem(TcItems.MISO_DENGAKU.get());
        basicItem(TcItems.SALTY_MELON.get());
        basicItem(TcItems.TASTY_STEW.get());
        basicItem(TcItems.TASTY_BEEF_STEW.get());
        basicItem(TcItems.NIKUJAGA.get());
        basicItem(TcItems.AGEDASHI_TOFU.get());
        basicItem(TcItems.KINAKO_MANJU.get());
        basicItem(TcItems.ZUNDA_MANJU.get());
        basicItem(TcItems.FUKUMENI.get());
        basicItem(TcItems.KOYADOFU_STEW.get());
        basicItem(TcItems.APRICOT.get());
        basicItem(TcItems.OKARA_STICK.get());
        basicItem(TcItems.RICE_NATTO.get());
        basicItem(TcItems.RICE_NATTO_LEEK.get());
        basicItem(TcItems.NATTO_HIYAYAKKO.get());

        // === Swords (handheld) ===
        handheldItem(TcItems.SWORD_KINU);
        handheldItem(TcItems.SWORD_MOMEN);
        handheldItem(TcItems.SWORD_SOLID);
        handheldItem(TcItems.SWORD_METAL);
        handheldItem(TcItems.SWORD_DIAMOND);

        // === Pickaxes (handheld) ===
        handheldItem(TcItems.PICKAXE_KINU);
        handheldItem(TcItems.PICKAXE_MOMEN);
        handheldItem(TcItems.PICKAXE_SOLID);
        handheldItem(TcItems.PICKAXE_METAL);
        handheldItem(TcItems.PICKAXE_DIAMOND);

        // === Axes (handheld) ===
        handheldItem(TcItems.AXE_KINU);
        handheldItem(TcItems.AXE_MOMEN);
        handheldItem(TcItems.AXE_SOLID);
        handheldItem(TcItems.AXE_METAL);
        handheldItem(TcItems.AXE_DIAMOND);

        // === Shovels (handheld) ===
        handheldItem(TcItems.SHOVEL_KINU);
        handheldItem(TcItems.SHOVEL_MOMEN);
        handheldItem(TcItems.SHOVEL_SOLID);
        handheldItem(TcItems.SHOVEL_METAL);
        handheldItem(TcItems.SHOVEL_DIAMOND);

        // === Hoe (handheld) ===
        handheldItem(TcItems.TOFU_HOE);

        // === Armor ===
        basicItem(TcItems.ARMOR_KINU_HELMET.get());
        basicItem(TcItems.ARMOR_KINU_CHESTPLATE.get());
        basicItem(TcItems.ARMOR_KINU_LEGGINGS.get());
        basicItem(TcItems.ARMOR_KINU_BOOTS.get());

        basicItem(TcItems.ARMOR_MOMEN_HELMET.get());
        basicItem(TcItems.ARMOR_MOMEN_CHESTPLATE.get());
        basicItem(TcItems.ARMOR_MOMEN_LEGGINGS.get());
        basicItem(TcItems.ARMOR_MOMEN_BOOTS.get());

        basicItem(TcItems.ARMOR_SOLID_HELMET.get());
        basicItem(TcItems.ARMOR_SOLID_CHESTPLATE.get());
        basicItem(TcItems.ARMOR_SOLID_LEGGINGS.get());
        basicItem(TcItems.ARMOR_SOLID_BOOTS.get());

        basicItem(TcItems.ARMOR_METAL_HELMET.get());
        basicItem(TcItems.ARMOR_METAL_CHESTPLATE.get());
        basicItem(TcItems.ARMOR_METAL_LEGGINGS.get());
        basicItem(TcItems.ARMOR_METAL_BOOTS.get());

        basicItem(TcItems.ARMOR_DIAMOND_HELMET.get());
        basicItem(TcItems.ARMOR_DIAMOND_CHESTPLATE.get());
        basicItem(TcItems.ARMOR_DIAMOND_LEGGINGS.get());
        basicItem(TcItems.ARMOR_DIAMOND_BOOTS.get());

        // === Special items ===
        basicItem(TcItems.ZUNDA_BOW.get());
        basicItem(TcItems.ZUNDA_ARROW.get());
        basicItem(TcItems.BUGLE.get());
        basicItem(TcItems.TOFU_RADAR.get());
        handheldItem(TcItems.TOFU_SCOOP);
        basicItem(TcItems.FUKUMAME.get());
        basicItem(TcItems.GOLDEN_SALT.get());

        // === Sapling block item ===
        withExistingParent("tc_sapling", mcLoc("item/generated"))
                .texture("layer0", modLoc("block/tc_sapling"));
    }

    private <T extends Item> void handheldItem(DeferredItem<T> item) {
        ResourceLocation id = item.getId();
        withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "item/" + id.getPath()));
    }

    private ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, path);
    }

    private ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
