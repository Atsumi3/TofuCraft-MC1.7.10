package tsuteto.tofu.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

import java.util.concurrent.CompletableFuture;

public class TcRecipeProvider extends RecipeProvider {

    public TcRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // === Tofu block crafting (4 food items -> 1 block) ===
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_KINU_FOOD.get(), TcBlocks.TOFU_KINU.get(), "tofu_kinu_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_MOMEN_FOOD.get(), TcBlocks.TOFU_MOMEN.get(), "tofu_momen_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_ISHI_FOOD.get(), TcBlocks.TOFU_ISHI.get(), "tofu_ishi_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_GRILLED_FOOD.get(), TcBlocks.TOFU_GRILLED.get(), "tofu_grilled_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_FRIED_POUCH_FOOD.get(), TcBlocks.TOFU_FRIED_POUCH.get(), "tofu_fried_pouch_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_FRIED_FOOD.get(), TcBlocks.TOFU_FRIED.get(), "tofu_fried_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_EGG_FOOD.get(), TcBlocks.TOFU_EGG.get(), "tofu_egg_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_ANNIN_FOOD.get(), TcBlocks.TOFU_ANNIN.get(), "tofu_annin_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_SESAME_FOOD.get(), TcBlocks.TOFU_SESAME.get(), "tofu_sesame_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_ZUNDA_FOOD.get(), TcBlocks.TOFU_ZUNDA.get(), "tofu_zunda_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_STRAWBERRY_FOOD.get(), TcBlocks.TOFU_STRAWBERRY.get(), "tofu_strawberry_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_MISO_FOOD.get(), TcBlocks.TOFU_MISO.get(), "tofu_miso_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_HELL_FOOD.get(), TcBlocks.TOFU_HELL.get(), "tofu_hell_block");
        tofuBlockRecipe(recipeOutput, TcItems.TOFU_GLOW_FOOD.get(), TcBlocks.TOFU_GLOW.get(), "tofu_glow_block");

        // Tofu block -> 4 food items
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcItems.TOFU_KINU_FOOD.get(), "tofu_kinu_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcItems.TOFU_MOMEN_FOOD.get(), "tofu_momen_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcItems.TOFU_ISHI_FOOD.get(), "tofu_ishi_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_GRILLED.get(), TcItems.TOFU_GRILLED_FOOD.get(), "tofu_grilled_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_FRIED_POUCH.get(), TcItems.TOFU_FRIED_POUCH_FOOD.get(), "tofu_fried_pouch_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_FRIED.get(), TcItems.TOFU_FRIED_FOOD.get(), "tofu_fried_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_EGG.get(), TcItems.TOFU_EGG_FOOD.get(), "tofu_egg_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_ANNIN.get(), TcItems.TOFU_ANNIN_FOOD.get(), "tofu_annin_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_SESAME.get(), TcItems.TOFU_SESAME_FOOD.get(), "tofu_sesame_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_ZUNDA.get(), TcItems.TOFU_ZUNDA_FOOD.get(), "tofu_zunda_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_STRAWBERRY.get(), TcItems.TOFU_STRAWBERRY_FOOD.get(), "tofu_strawberry_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_MISO.get(), TcItems.TOFU_MISO_FOOD.get(), "tofu_miso_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_HELL.get(), TcItems.TOFU_HELL_FOOD.get(), "tofu_hell_food_from_block");
        tofuUnpackRecipe(recipeOutput, TcBlocks.TOFU_GLOW.get(), TcItems.TOFU_GLOW_FOOD.get(), "tofu_glow_food_from_block");

        // === Salt furnace ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.SALT_FURNACE.get())
                .pattern("SSS")
                .pattern("S S")
                .pattern("CCC")
                .define('S', TcBlocks.SALT_BLOCK.get())
                .define('C', Items.COBBLESTONE)
                .unlockedBy("has_salt_block", has(TcBlocks.SALT_BLOCK.get()))
                .save(recipeOutput, modLoc("salt_furnace"));

        // === Salt block (9 salt -> 1 block) ===
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TcBlocks.SALT_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', TcItems.SALT.get())
                .unlockedBy("has_salt", has(TcItems.SALT.get()))
                .save(recipeOutput, modLoc("salt_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcItems.SALT.get(), 9)
                .requires(TcBlocks.SALT_BLOCK.get())
                .unlockedBy("has_salt_block", has(TcBlocks.SALT_BLOCK.get()))
                .save(recipeOutput, modLoc("salt_from_salt_block"));

        // === Salt pan ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.SALT_PAN.get())
                .pattern("S S")
                .pattern("PPP")
                .define('S', Items.STICK)
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput, modLoc("salt_pan"));

        // === Machine case ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_MACHINE_CASE.get())
                .pattern("III")
                .pattern("ITI")
                .pattern("III")
                .define('I', Items.IRON_INGOT)
                .define('T', TcBlocks.TOFU_METAL.get())
                .unlockedBy("has_tofu_metal", has(TcBlocks.TOFU_METAL.get()))
                .save(recipeOutput, modLoc("tf_machine_case"));

        // === TF Machines ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_STORAGE.get())
                .pattern("IGI")
                .pattern("GCG")
                .pattern("IGI")
                .define('I', Items.IRON_INGOT)
                .define('G', Items.GOLD_INGOT)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_storage"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_CONDENSER.get())
                .pattern("IDI")
                .pattern("DCD")
                .pattern("IDI")
                .define('I', Items.IRON_INGOT)
                .define('D', Items.DIAMOND)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_condenser"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_OVEN.get())
                .pattern("IFI")
                .pattern("FCF")
                .pattern("IFI")
                .define('I', Items.IRON_INGOT)
                .define('F', Items.FURNACE)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_oven"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_REFORMER.get())
                .pattern("IRI")
                .pattern("RCR")
                .pattern("IRI")
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_reformer"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_SATURATOR.get())
                .pattern("IEI")
                .pattern("ECE")
                .pattern("IEI")
                .define('I', Items.IRON_INGOT)
                .define('E', Items.EMERALD)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_saturator"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_COLLECTOR.get())
                .pattern("ILI")
                .pattern("LCL")
                .pattern("ILI")
                .define('I', Items.IRON_INGOT)
                .define('L', Items.LAPIS_LAZULI)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_collector"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TF_ANTENNA.get())
                .pattern(" I ")
                .pattern("ICI")
                .pattern(" I ")
                .define('I', Items.IRON_INGOT)
                .define('C', TcBlocks.TF_MACHINE_CASE.get())
                .unlockedBy("has_machine_case", has(TcBlocks.TF_MACHINE_CASE.get()))
                .save(recipeOutput, modLoc("tf_antenna"));

        // === Tofu stick ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcItems.TOFU_STICK.get(), 4)
                .pattern("T")
                .pattern("T")
                .define('T', TcItems.TOFU_KINU_FOOD.get())
                .unlockedBy("has_tofu_kinu_food", has(TcItems.TOFU_KINU_FOOD.get()))
                .save(recipeOutput, modLoc("tofu_stick"));

        // === Tool recipes ===
        // Kinu tools
        toolRecipes(recipeOutput, TcItems.TOFU_KINU_FOOD.get(),
                TcItems.SWORD_KINU.get(), TcItems.PICKAXE_KINU.get(),
                TcItems.AXE_KINU.get(), TcItems.SHOVEL_KINU.get(), "kinu");

        // Momen tools
        toolRecipes(recipeOutput, TcItems.TOFU_MOMEN_FOOD.get(),
                TcItems.SWORD_MOMEN.get(), TcItems.PICKAXE_MOMEN.get(),
                TcItems.AXE_MOMEN.get(), TcItems.SHOVEL_MOMEN.get(), "momen");

        // Solid tools
        toolRecipes(recipeOutput, TcItems.TOFU_ISHI_FOOD.get(),
                TcItems.SWORD_SOLID.get(), TcItems.PICKAXE_SOLID.get(),
                TcItems.AXE_SOLID.get(), TcItems.SHOVEL_SOLID.get(), "solid");

        // Metal tools
        toolRecipes(recipeOutput, TcBlocks.TOFU_METAL.get(),
                TcItems.SWORD_METAL.get(), TcItems.PICKAXE_METAL.get(),
                TcItems.AXE_METAL.get(), TcItems.SHOVEL_METAL.get(), "metal");

        // Diamond tools
        toolRecipes(recipeOutput, TcBlocks.TOFU_DIAMOND.get(),
                TcItems.SWORD_DIAMOND.get(), TcItems.PICKAXE_DIAMOND.get(),
                TcItems.AXE_DIAMOND.get(), TcItems.SHOVEL_DIAMOND.get(), "diamond");

        // === Tofu hoe ===
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TcItems.TOFU_HOE.get())
                .pattern("TT")
                .pattern(" S")
                .pattern(" S")
                .define('T', TcBlocks.TOFU_DIAMOND.get())
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_tofu_diamond", has(TcBlocks.TOFU_DIAMOND.get()))
                .save(recipeOutput, modLoc("tofu_hoe"));

        // === Armor recipes ===
        armorRecipes(recipeOutput, TcItems.TOFU_KINU_FOOD.get(),
                TcItems.ARMOR_KINU_HELMET.get(), TcItems.ARMOR_KINU_CHESTPLATE.get(),
                TcItems.ARMOR_KINU_LEGGINGS.get(), TcItems.ARMOR_KINU_BOOTS.get(), "kinu");

        armorRecipes(recipeOutput, TcItems.TOFU_MOMEN_FOOD.get(),
                TcItems.ARMOR_MOMEN_HELMET.get(), TcItems.ARMOR_MOMEN_CHESTPLATE.get(),
                TcItems.ARMOR_MOMEN_LEGGINGS.get(), TcItems.ARMOR_MOMEN_BOOTS.get(), "momen");

        armorRecipes(recipeOutput, TcItems.TOFU_ISHI_FOOD.get(),
                TcItems.ARMOR_SOLID_HELMET.get(), TcItems.ARMOR_SOLID_CHESTPLATE.get(),
                TcItems.ARMOR_SOLID_LEGGINGS.get(), TcItems.ARMOR_SOLID_BOOTS.get(), "solid");

        armorRecipes(recipeOutput, TcBlocks.TOFU_METAL.get(),
                TcItems.ARMOR_METAL_HELMET.get(), TcItems.ARMOR_METAL_CHESTPLATE.get(),
                TcItems.ARMOR_METAL_LEGGINGS.get(), TcItems.ARMOR_METAL_BOOTS.get(), "metal");

        armorRecipes(recipeOutput, TcBlocks.TOFU_DIAMOND.get(),
                TcItems.ARMOR_DIAMOND_HELMET.get(), TcItems.ARMOR_DIAMOND_CHESTPLATE.get(),
                TcItems.ARMOR_DIAMOND_LEGGINGS.get(), TcItems.ARMOR_DIAMOND_BOOTS.get(), "diamond_tofu");

        // === Food recipes ===
        // Miso soup
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.MISO_SOUP.get())
                .requires(Items.BOWL)
                .requires(TcItems.MISO.get())
                .requires(TcItems.TOFU_KINU_FOOD.get())
                .requires(TcItems.DASHI.get())
                .unlockedBy("has_miso", has(TcItems.MISO.get()))
                .save(recipeOutput, modLoc("miso_soup"));

        // Yudofu
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.YUDOFU.get())
                .requires(Items.BOWL)
                .requires(TcItems.TOFU_KINU_FOOD.get())
                .requires(TcItems.DASHI.get())
                .unlockedBy("has_tofu_kinu_food", has(TcItems.TOFU_KINU_FOOD.get()))
                .save(recipeOutput, modLoc("yudofu"));

        // TTT Burger
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TcItems.TTT_BURGER.get())
                .pattern(" B ")
                .pattern("TLT")
                .pattern(" B ")
                .define('B', Items.BREAD)
                .define('T', TcItems.TOFU_GRILLED_FOOD.get())
                .define('L', TcItems.LEEK.get())
                .unlockedBy("has_tofu_grilled_food", has(TcItems.TOFU_GRILLED_FOOD.get()))
                .save(recipeOutput, modLoc("ttt_burger"));

        // Miso dengaku
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.MISO_DENGAKU.get())
                .requires(TcItems.TOFU_GRILLED_FOOD.get())
                .requires(TcItems.MISO.get())
                .requires(Items.STICK)
                .unlockedBy("has_tofu_grilled_food", has(TcItems.TOFU_GRILLED_FOOD.get()))
                .save(recipeOutput, modLoc("miso_dengaku"));

        // Salty melon
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.SALTY_MELON.get())
                .requires(Items.MELON_SLICE)
                .requires(TcItems.SALT.get())
                .unlockedBy("has_salt", has(TcItems.SALT.get()))
                .save(recipeOutput, modLoc("salty_melon"));

        // Agedashi tofu
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.AGEDASHI_TOFU.get())
                .requires(Items.BOWL)
                .requires(TcItems.TOFU_FRIED_FOOD.get())
                .requires(TcItems.BOTTLE_SOY_SAUCE.get())
                .requires(TcItems.DASHI.get())
                .unlockedBy("has_tofu_fried_food", has(TcItems.TOFU_FRIED_FOOD.get()))
                .save(recipeOutput, modLoc("agedashi_tofu"));

        // Kinako manju
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.KINAKO_MANJU.get())
                .requires(Items.BREAD)
                .requires(TcItems.KINAKO.get())
                .unlockedBy("has_kinako", has(TcItems.KINAKO.get()))
                .save(recipeOutput, modLoc("kinako_manju"));

        // Zunda manju
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.ZUNDA_MANJU.get())
                .requires(Items.BREAD)
                .requires(TcItems.ZUNDA.get())
                .unlockedBy("has_zunda", has(TcItems.ZUNDA.get()))
                .save(recipeOutput, modLoc("zunda_manju"));

        // Rice natto
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.RICE_NATTO.get())
                .requires(Items.BOWL)
                .requires(TcItems.NATTO.get())
                .requires(TcItems.BOTTLE_SOY_SAUCE.get())
                .unlockedBy("has_natto", has(TcItems.NATTO.get()))
                .save(recipeOutput, modLoc("rice_natto"));

        // Rice natto leek
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.RICE_NATTO_LEEK.get())
                .requires(TcItems.RICE_NATTO.get())
                .requires(TcItems.LEEK.get())
                .unlockedBy("has_rice_natto", has(TcItems.RICE_NATTO.get()))
                .save(recipeOutput, modLoc("rice_natto_leek"));

        // Natto hiyayakko
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, TcItems.NATTO_HIYAYAKKO.get())
                .requires(Items.BOWL)
                .requires(TcItems.NATTO.get())
                .requires(TcItems.TOFU_KINU_FOOD.get())
                .requires(TcItems.BOTTLE_SOY_SAUCE.get())
                .unlockedBy("has_natto", has(TcItems.NATTO.get()))
                .save(recipeOutput, modLoc("natto_hiyayakko"));

        // Okara stick
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TcItems.OKARA_STICK.get(), 4)
                .pattern("OO")
                .pattern("OO")
                .define('O', TcItems.OKARA.get())
                .unlockedBy("has_okara", has(TcItems.OKARA.get()))
                .save(recipeOutput, modLoc("okara_stick"));

        // === Crafting material recipes ===
        // Filter cloth
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcItems.FILTER_CLOTH.get())
                .pattern("SSS")
                .pattern("SSS")
                .define('S', Items.STRING)
                .unlockedBy("has_string", has(Items.STRING))
                .save(recipeOutput, modLoc("filter_cloth"));

        // Tofu diamond nugget (9 -> 1 diamond tofu)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TOFU_DIAMOND.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', TcItems.TOFU_DIAMOND_NUGGET.get())
                .unlockedBy("has_tofu_diamond_nugget", has(TcItems.TOFU_DIAMOND_NUGGET.get()))
                .save(recipeOutput, modLoc("tofu_diamond_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcItems.TOFU_DIAMOND_NUGGET.get(), 9)
                .requires(TcBlocks.TOFU_DIAMOND.get())
                .unlockedBy("has_tofu_diamond", has(TcBlocks.TOFU_DIAMOND.get()))
                .save(recipeOutput, modLoc("tofu_diamond_nugget_from_block"));

        // Tofu metal nugget (9 -> 1 metal tofu)
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcBlocks.TOFU_METAL.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("NNN")
                .define('N', TcItems.TOFU_METAL_NUGGET.get())
                .unlockedBy("has_tofu_metal_nugget", has(TcItems.TOFU_METAL_NUGGET.get()))
                .save(recipeOutput, modLoc("tofu_metal_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcItems.TOFU_METAL_NUGGET.get(), 9)
                .requires(TcBlocks.TOFU_METAL.get())
                .unlockedBy("has_tofu_metal", has(TcBlocks.TOFU_METAL.get()))
                .save(recipeOutput, modLoc("tofu_metal_nugget_from_block"));

        // === Zunda items ===
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcItems.ZUNDA.get())
                .requires(TcItems.EDAMAME_BOILED.get())
                .requires(TcItems.EDAMAME_BOILED.get())
                .unlockedBy("has_edamame_boiled", has(TcItems.EDAMAME_BOILED.get()))
                .save(recipeOutput, modLoc("zunda"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TcItems.ZUNDA_BOW.get())
                .pattern(" ZS")
                .pattern("Z S")
                .pattern(" ZS")
                .define('Z', TcItems.ZUNDAMA.get())
                .define('S', Items.STRING)
                .unlockedBy("has_zundama", has(TcItems.ZUNDAMA.get()))
                .save(recipeOutput, modLoc("zunda_bow"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, TcItems.ZUNDA_ARROW.get(), 4)
                .pattern("Z")
                .pattern("S")
                .pattern("F")
                .define('Z', TcItems.ZUNDAMA.get())
                .define('S', Items.STICK)
                .define('F', Items.FEATHER)
                .unlockedBy("has_zundama", has(TcItems.ZUNDAMA.get()))
                .save(recipeOutput, modLoc("zunda_arrow"));

        // === Barrel recipes ===
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, TcItems.BARREL_EMPTY.get())
                .pattern("P P")
                .pattern("P P")
                .pattern("PPP")
                .define('P', ItemTags.PLANKS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(recipeOutput, modLoc("barrel_empty"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcBlocks.BARREL_MISO.get())
                .requires(TcItems.BARREL_EMPTY.get())
                .requires(TcItems.MISO.get())
                .requires(TcItems.MISO.get())
                .requires(TcItems.MISO.get())
                .requires(TcItems.MISO.get())
                .unlockedBy("has_miso", has(TcItems.MISO.get()))
                .save(recipeOutput, modLoc("barrel_miso"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcBlocks.BARREL_MISO_TOFU.get())
                .requires(TcBlocks.BARREL_MISO.get())
                .requires(TcItems.TOFU_MOMEN_FOOD.get())
                .requires(TcItems.TOFU_MOMEN_FOOD.get())
                .requires(TcItems.TOFU_MOMEN_FOOD.get())
                .requires(TcItems.TOFU_MOMEN_FOOD.get())
                .unlockedBy("has_barrel_miso", has(TcBlocks.BARREL_MISO.get()))
                .save(recipeOutput, modLoc("barrel_miso_tofu"));

        // === Special items ===
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TcItems.BUGLE.get())
                .pattern("  T")
                .pattern(" T ")
                .pattern("T  ")
                .define('T', TcBlocks.TOFU_METAL.get())
                .unlockedBy("has_tofu_metal", has(TcBlocks.TOFU_METAL.get()))
                .save(recipeOutput, modLoc("bugle"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TcItems.TOFU_RADAR.get())
                .pattern(" T ")
                .pattern("TRT")
                .pattern(" T ")
                .define('T', TcBlocks.TOFU_METAL.get())
                .define('R', Items.REDSTONE)
                .unlockedBy("has_tofu_metal", has(TcBlocks.TOFU_METAL.get()))
                .save(recipeOutput, modLoc("tofu_radar"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TcItems.TOFU_SCOOP.get())
                .pattern(" TT")
                .pattern(" ST")
                .pattern("S  ")
                .define('T', TcItems.TOFU_MOMEN_FOOD.get())
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_tofu_momen_food", has(TcItems.TOFU_MOMEN_FOOD.get()))
                .save(recipeOutput, modLoc("tofu_scoop"));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, TcItems.GOLDEN_SALT.get())
                .pattern("GGG")
                .pattern("GSG")
                .pattern("GGG")
                .define('G', Items.GOLD_INGOT)
                .define('S', TcItems.SALT.get())
                .unlockedBy("has_salt", has(TcItems.SALT.get()))
                .save(recipeOutput, modLoc("golden_salt"));

        // === Tofu cake ===
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, TcBlocks.TOFU_CAKE.get())
                .pattern("TST")
                .pattern("SAS")
                .pattern("TST")
                .define('T', TcItems.TOFU_KINU_FOOD.get())
                .define('S', Items.SUGAR)
                .define('A', TcItems.TOFU_ANNIN_FOOD.get())
                .unlockedBy("has_tofu_annin_food", has(TcItems.TOFU_ANNIN_FOOD.get()))
                .save(recipeOutput, modLoc("tofu_cake"));

        // === Morijio ===
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, TcItems.MORIJIO_ITEM.get())
                .pattern(" S ")
                .pattern("SSS")
                .define('S', TcItems.SALT.get())
                .unlockedBy("has_salt", has(TcItems.SALT.get()))
                .save(recipeOutput, modLoc("morijio"));

        // === Smelting ===
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TcItems.SOYBEANS.get()),
                        RecipeCategory.MISC, TcItems.SOYBEANS_PARCHED.get(), 0.1f, 200)
                .unlockedBy("has_soybeans", has(TcItems.SOYBEANS.get()))
                .save(recipeOutput, modLoc("soybeans_parched_from_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TcItems.TOFU_KINU_FOOD.get()),
                        RecipeCategory.FOOD, TcItems.TOFU_GRILLED_FOOD.get(), 0.1f, 200)
                .unlockedBy("has_tofu_kinu_food", has(TcItems.TOFU_KINU_FOOD.get()))
                .save(recipeOutput, modLoc("tofu_grilled_from_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TcItems.TOFU_MOMEN_FOOD.get()),
                        RecipeCategory.FOOD, TcItems.TOFU_GRILLED_FOOD.get(), 0.1f, 200)
                .unlockedBy("has_tofu_momen_food", has(TcItems.TOFU_MOMEN_FOOD.get()))
                .save(recipeOutput, modLoc("tofu_grilled_from_momen_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TcItems.EDAMAME.get()),
                        RecipeCategory.FOOD, TcItems.EDAMAME_BOILED.get(), 0.1f, 200)
                .unlockedBy("has_edamame", has(TcItems.EDAMAME.get()))
                .save(recipeOutput, modLoc("edamame_boiled_from_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(TcItems.STARCH_RAW.get()),
                        RecipeCategory.MISC, TcItems.STARCH.get(), 0.1f, 200)
                .unlockedBy("has_starch_raw", has(TcItems.STARCH_RAW.get()))
                .save(recipeOutput, modLoc("starch_from_smelting"));

        // === Stairs recipes ===
        stairsRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_STAIRS_KINU.get(), "tofu_stairs_kinu");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_STAIRS_MOMEN.get(), "tofu_stairs_momen");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_STAIRS_ISHI.get(), "tofu_stairs_ishi");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_METAL.get(), TcBlocks.TOFU_STAIRS_METAL.get(), "tofu_stairs_metal");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_GRILLED.get(), TcBlocks.TOFU_STAIRS_GRILLED.get(), "tofu_stairs_grilled");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_DRIED.get(), TcBlocks.TOFU_STAIRS_DRIED.get(), "tofu_stairs_dried");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_DIAMOND.get(), TcBlocks.TOFU_STAIRS_DIAMOND.get(), "tofu_stairs_diamond");
        stairsRecipe(recipeOutput, TcBlocks.TOFU_MISO.get(), TcBlocks.TOFU_STAIRS_MISO.get(), "tofu_stairs_miso");

        // === Slab recipes ===
        slabRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_SLAB_KINU.get(), "tofu_slab_kinu");
        slabRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_SLAB_MOMEN.get(), "tofu_slab_momen");
        slabRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_SLAB_ISHI.get(), "tofu_slab_ishi");
        slabRecipe(recipeOutput, TcBlocks.TOFU_METAL.get(), TcBlocks.TOFU_SLAB_METAL.get(), "tofu_slab_metal");
        slabRecipe(recipeOutput, TcBlocks.TOFU_DIAMOND.get(), TcBlocks.TOFU_SLAB_DIAMOND.get(), "tofu_slab_diamond");

        // === Wall recipes ===
        wallRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_WALL_KINU.get(), "tofu_wall_kinu");
        wallRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_WALL_MOMEN.get(), "tofu_wall_momen");
        wallRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_WALL_ISHI.get(), "tofu_wall_ishi");
        wallRecipe(recipeOutput, TcBlocks.TOFU_METAL.get(), TcBlocks.TOFU_WALL_METAL.get(), "tofu_wall_metal");
        wallRecipe(recipeOutput, TcBlocks.TOFU_GRILLED.get(), TcBlocks.TOFU_WALL_GRILLED.get(), "tofu_wall_grilled");
        wallRecipe(recipeOutput, TcBlocks.TOFU_DRIED.get(), TcBlocks.TOFU_WALL_DRIED.get(), "tofu_wall_dried");
        wallRecipe(recipeOutput, TcBlocks.TOFU_DIAMOND.get(), TcBlocks.TOFU_WALL_DIAMOND.get(), "tofu_wall_diamond");

        // === Door recipes ===
        doorRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_DOOR_KINU.get(), "tofu_door_kinu");
        doorRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_DOOR_MOMEN.get(), "tofu_door_momen");
        doorRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_DOOR_ISHI.get(), "tofu_door_ishi");
        doorRecipe(recipeOutput, TcBlocks.TOFU_METAL.get(), TcBlocks.TOFU_DOOR_METAL.get(), "tofu_door_metal");
        doorRecipe(recipeOutput, TcBlocks.TOFU_DIAMOND.get(), TcBlocks.TOFU_DOOR_DIAMOND.get(), "tofu_door_diamond");

        // === Fence gate recipes ===
        fenceGateRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_FENCE_GATE_KINU.get(), "tofu_fence_gate_kinu");
        fenceGateRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_FENCE_GATE_MOMEN.get(), "tofu_fence_gate_momen");
        fenceGateRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_FENCE_GATE_ISHI.get(), "tofu_fence_gate_ishi");
        fenceGateRecipe(recipeOutput, TcBlocks.TOFU_METAL.get(), TcBlocks.TOFU_FENCE_GATE_METAL.get(), "tofu_fence_gate_metal");
        fenceGateRecipe(recipeOutput, TcBlocks.TOFU_DIAMOND.get(), TcBlocks.TOFU_FENCE_GATE_DIAMOND.get(), "tofu_fence_gate_diamond");

        // === Trapdoor recipes ===
        trapdoorRecipe(recipeOutput, TcBlocks.TOFU_KINU.get(), TcBlocks.TOFU_TRAPDOOR_KINU.get(), "tofu_trapdoor_kinu");
        trapdoorRecipe(recipeOutput, TcBlocks.TOFU_MOMEN.get(), TcBlocks.TOFU_TRAPDOOR_MOMEN.get(), "tofu_trapdoor_momen");
        trapdoorRecipe(recipeOutput, TcBlocks.TOFU_ISHI.get(), TcBlocks.TOFU_TRAPDOOR_ISHI.get(), "tofu_trapdoor_ishi");

        // === Adv tofu gem ===
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TcBlocks.ADV_TOFU_GEM.get())
                .pattern("DGD")
                .pattern("GEG")
                .pattern("DGD")
                .define('D', TcBlocks.TOFU_DIAMOND.get())
                .define('G', TcBlocks.TOFU_GLOW.get())
                .define('E', Items.EMERALD)
                .unlockedBy("has_tofu_diamond", has(TcBlocks.TOFU_DIAMOND.get()))
                .save(recipeOutput, modLoc("adv_tofu_gem"));

        // === Chikuwa platform ===
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, TcBlocks.CHIKUWA_PLATFORM.get(), 4)
                .pattern("CC")
                .pattern("CC")
                .define('C', TcItems.TOFU_ISHI_FOOD.get())
                .unlockedBy("has_tofu_ishi_food", has(TcItems.TOFU_ISHI_FOOD.get()))
                .save(recipeOutput, modLoc("chikuwa_platform"));

        // === Fukumame ===
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TcItems.FUKUMAME.get(), 4)
                .requires(TcItems.SOYBEANS_PARCHED.get())
                .unlockedBy("has_soybeans_parched", has(TcItems.SOYBEANS_PARCHED.get()))
                .save(recipeOutput, modLoc("fukumame"));
    }

    // === Helper methods ===

    private void tofuBlockRecipe(RecipeOutput output, ItemLike food, ItemLike block, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("TT")
                .pattern("TT")
                .define('T', food)
                .unlockedBy("has_" + name, has(food))
                .save(output, modLoc(name));
    }

    private void tofuUnpackRecipe(RecipeOutput output, ItemLike block, ItemLike food, String name) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, food, 4)
                .requires(block)
                .unlockedBy("has_block", has(block))
                .save(output, modLoc(name));
    }

    private void toolRecipes(RecipeOutput output, ItemLike material,
                             ItemLike sword, ItemLike pickaxe, ItemLike axe, ItemLike shovel,
                             String prefix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, sword)
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .define('M', material)
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("sword_" + prefix));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, pickaxe)
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .define('M', material)
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("pickaxe_" + prefix));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, axe)
                .pattern("MM")
                .pattern("MS")
                .pattern(" S")
                .define('M', material)
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("axe_" + prefix));

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, shovel)
                .pattern("M")
                .pattern("S")
                .pattern("S")
                .define('M', material)
                .define('S', TcItems.TOFU_STICK.get())
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("shovel_" + prefix));
    }

    private void armorRecipes(RecipeOutput output, ItemLike material,
                              ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots,
                              String prefix) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, helmet)
                .pattern("MMM")
                .pattern("M M")
                .define('M', material)
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("armor_" + prefix + "_helmet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, chestplate)
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', material)
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("armor_" + prefix + "_chestplate"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, leggings)
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', material)
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("armor_" + prefix + "_leggings"));

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, boots)
                .pattern("M M")
                .pattern("M M")
                .define('M', material)
                .unlockedBy("has_material", has(material))
                .save(output, modLoc("armor_" + prefix + "_boots"));
    }

    private void stairsRecipe(RecipeOutput output, ItemLike base, ItemLike stairs, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .pattern("B  ")
                .pattern("BB ")
                .pattern("BBB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private void slabRecipe(RecipeOutput output, ItemLike base, ItemLike slab, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("BBB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private void wallRecipe(RecipeOutput output, ItemLike base, ItemLike wall, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, wall, 6)
                .pattern("BBB")
                .pattern("BBB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private void doorRecipe(RecipeOutput output, ItemLike base, ItemLike door, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3)
                .pattern("BB")
                .pattern("BB")
                .pattern("BB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private void fenceGateRecipe(RecipeOutput output, ItemLike base, ItemLike gate, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, gate)
                .pattern("SBS")
                .pattern("SBS")
                .define('S', Items.STICK)
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private void trapdoorRecipe(RecipeOutput output, ItemLike base, ItemLike trapdoor, String name) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                .pattern("BBB")
                .pattern("BBB")
                .define('B', base)
                .unlockedBy("has_base", has(base))
                .save(output, modLoc(name));
    }

    private ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, path);
    }
}
