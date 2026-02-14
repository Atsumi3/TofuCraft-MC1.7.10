package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod

object TcSoundEvents {
    @JvmField
    val SOUND_EVENTS: DeferredRegister<SoundEvent> =
        DeferredRegister.create(Registries.SOUND_EVENT, TofuCraftMod.MOD_ID)

    @JvmField
    val TOFU_BUGLE: DeferredHolder<SoundEvent, SoundEvent> =
        SOUND_EVENTS.register("tofu_bugle") {
            SoundEvent.createVariableRangeEvent(
                ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_bugle"))
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        SOUND_EVENTS.register(modEventBus)
    }
}
