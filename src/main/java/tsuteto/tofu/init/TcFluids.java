package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;

public class TcFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, TofuCraftMod.MOD_ID);

    // === Soymilk ===
    public static final DeferredHolder<Fluid, FlowingFluid> SOYMILK_SOURCE =
            FLUIDS.register("soymilk",
                    () -> new BaseFlowingFluid.Source(TcFluids.soymilkProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> SOYMILK_FLOWING =
            FLUIDS.register("soymilk_flowing",
                    () -> new BaseFlowingFluid.Flowing(TcFluids.soymilkProperties()));

    // === Soymilk Hell ===
    public static final DeferredHolder<Fluid, FlowingFluid> SOYMILK_HELL_SOURCE =
            FLUIDS.register("soymilk_hell",
                    () -> new BaseFlowingFluid.Source(TcFluids.soymilkHellProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> SOYMILK_HELL_FLOWING =
            FLUIDS.register("soymilk_hell_flowing",
                    () -> new BaseFlowingFluid.Flowing(TcFluids.soymilkHellProperties()));

    // === Soy Sauce ===
    public static final DeferredHolder<Fluid, FlowingFluid> SOY_SAUCE_SOURCE =
            FLUIDS.register("soy_sauce",
                    () -> new BaseFlowingFluid.Source(TcFluids.soySauceProperties()));

    public static final DeferredHolder<Fluid, FlowingFluid> SOY_SAUCE_FLOWING =
            FLUIDS.register("soy_sauce_flowing",
                    () -> new BaseFlowingFluid.Flowing(TcFluids.soySauceProperties()));

    // === Fluid Properties ===
    private static BaseFlowingFluid.Properties soymilkProperties() {
        return new BaseFlowingFluid.Properties(
                TcFluids.SOYMILK_SOURCE,
                TcFluids.SOYMILK_FLOWING)
                .block(TcBlocks.SOYMILK)
                .bucket(TcItems.BUCKET_SOYMILK)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    private static BaseFlowingFluid.Properties soymilkHellProperties() {
        return new BaseFlowingFluid.Properties(
                TcFluids.SOYMILK_HELL_SOURCE,
                TcFluids.SOYMILK_HELL_FLOWING)
                .block(TcBlocks.SOYMILK_HELL)
                .bucket(TcItems.BUCKET_SOYMILK_HELL)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(1);
    }

    private static BaseFlowingFluid.Properties soySauceProperties() {
        return new BaseFlowingFluid.Properties(
                TcFluids.SOY_SAUCE_SOURCE,
                TcFluids.SOY_SAUCE_FLOWING)
                .block(TcBlocks.SOY_SAUCE)
                .bucket(TcItems.BUCKET_SOY_SAUCE)
                .slopeFindDistance(3)
                .levelDecreasePerBlock(2);
    }

    public static void register(IEventBus modEventBus) {
        FLUIDS.register(modEventBus);
    }
}
