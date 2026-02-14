package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.world.level.material.FlowingFluid
import net.minecraft.world.level.material.Fluid
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.fluids.BaseFlowingFluid
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod

object TcFluids {
    @JvmField
    val FLUIDS: DeferredRegister<Fluid> =
        DeferredRegister.create(Registries.FLUID, TofuCraftMod.MOD_ID)

    // === Soymilk ===
    @JvmField
    val SOYMILK_SOURCE: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soymilk") { BaseFlowingFluid.Source(soymilkProperties()) }

    @JvmField
    val SOYMILK_FLOWING: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soymilk_flowing") { BaseFlowingFluid.Flowing(soymilkProperties()) }

    // === Soymilk Hell ===
    @JvmField
    val SOYMILK_HELL_SOURCE: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soymilk_hell") { BaseFlowingFluid.Source(soymilkHellProperties()) }

    @JvmField
    val SOYMILK_HELL_FLOWING: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soymilk_hell_flowing") { BaseFlowingFluid.Flowing(soymilkHellProperties()) }

    // === Soy Sauce ===
    @JvmField
    val SOY_SAUCE_SOURCE: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soy_sauce") { BaseFlowingFluid.Source(soySauceProperties()) }

    @JvmField
    val SOY_SAUCE_FLOWING: DeferredHolder<Fluid, FlowingFluid> =
        FLUIDS.register("soy_sauce_flowing") { BaseFlowingFluid.Flowing(soySauceProperties()) }

    // === Fluid Properties ===
    private fun soymilkProperties(): BaseFlowingFluid.Properties =
        BaseFlowingFluid.Properties(SOYMILK_SOURCE, SOYMILK_FLOWING)
            .block(TcBlocks.SOYMILK)
            .bucket(TcItems.BUCKET_SOYMILK)
            .slopeFindDistance(4)
            .levelDecreasePerBlock(1)

    private fun soymilkHellProperties(): BaseFlowingFluid.Properties =
        BaseFlowingFluid.Properties(SOYMILK_HELL_SOURCE, SOYMILK_HELL_FLOWING)
            .block(TcBlocks.SOYMILK_HELL)
            .bucket(TcItems.BUCKET_SOYMILK_HELL)
            .slopeFindDistance(4)
            .levelDecreasePerBlock(1)

    private fun soySauceProperties(): BaseFlowingFluid.Properties =
        BaseFlowingFluid.Properties(SOY_SAUCE_SOURCE, SOY_SAUCE_FLOWING)
            .block(TcBlocks.SOY_SAUCE)
            .bucket(TcItems.BUCKET_SOY_SAUCE)
            .slopeFindDistance(3)
            .levelDecreasePerBlock(2)

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        FLUIDS.register(modEventBus)
    }
}
