package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.recipe.TfCondenserRecipe;
import tsuteto.tofu.recipe.TfReformerRecipe;

public class TcRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<TfCondenserRecipe>> TF_CONDENSER_RECIPE =
            RECIPE_TYPES.register("tf_condenser",
                    () -> new RecipeType<>() {
                        @Override
                        public String toString() {
                            return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_condenser").toString();
                        }
                    });

    public static final DeferredHolder<RecipeType<?>, RecipeType<TfReformerRecipe>> TF_REFORMER_RECIPE =
            RECIPE_TYPES.register("tf_reformer",
                    () -> new RecipeType<>() {
                        @Override
                        public String toString() {
                            return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_reformer").toString();
                        }
                    });

    public static void register(IEventBus modEventBus) {
        RECIPE_TYPES.register(modEventBus);
    }
}
