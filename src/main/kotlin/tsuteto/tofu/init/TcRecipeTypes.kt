package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.RecipeType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.recipe.TfCondenserRecipe
import tsuteto.tofu.recipe.TfReformerRecipe

object TcRecipeTypes {
    @JvmField
    val RECIPE_TYPES: DeferredRegister<RecipeType<*>> =
        DeferredRegister.create(Registries.RECIPE_TYPE, TofuCraftMod.MOD_ID)

    @JvmField
    val TF_CONDENSER_RECIPE: DeferredHolder<RecipeType<*>, RecipeType<TfCondenserRecipe>> =
        RECIPE_TYPES.register("tf_condenser") {
            object : RecipeType<TfCondenserRecipe> {
                override fun toString(): String =
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_condenser").toString()
            }
        }

    @JvmField
    val TF_REFORMER_RECIPE: DeferredHolder<RecipeType<*>, RecipeType<TfReformerRecipe>> =
        RECIPE_TYPES.register("tf_reformer") {
            object : RecipeType<TfReformerRecipe> {
                override fun toString(): String =
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_reformer").toString()
            }
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        RECIPE_TYPES.register(modEventBus)
    }
}
