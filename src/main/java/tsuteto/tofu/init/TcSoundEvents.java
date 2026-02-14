package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;

public class TcSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> TOFU_BUGLE =
            SOUND_EVENTS.register("tofu_bugle",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_bugle")));

    public static void register(IEventBus modEventBus) {
        SOUND_EVENTS.register(modEventBus);
    }
}
