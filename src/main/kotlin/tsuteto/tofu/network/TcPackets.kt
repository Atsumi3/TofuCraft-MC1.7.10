package tsuteto.tofu.network

import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import tsuteto.tofu.TofuCraftMod

/**
 * Registers all custom network payloads for TofuCraft using NeoForge's payload system.
 *
 * Payloads registered:
 * - [DimTripPayload] - client to server: trigger dimension travel
 * - [BuglePayload] - client to server: play bugle sound
 * - [TofuRadarPayload] - server to client: radar scan result data
 * - [TfMachineDataPayload] - server to client: machine energy/progress sync
 */
object TcPackets {

    /**
     * Called from the mod constructor to register this class as a listener
     * on the mod event bus for payload registration.
     */
    @JvmStatic
    fun register(modEventBus: IEventBus) {
        modEventBus.addListener(::onRegisterPayloadHandlers)
    }

    /**
     * Registers all custom packet payloads with the NeoForge networking system.
     * Each payload specifies its type, stream codec, and handler.
     */
    @SubscribeEvent
    @JvmStatic
    fun onRegisterPayloadHandlers(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar(TofuCraftMod.MOD_ID)
            .versioned("1.0.0")

        // Client -> Server payloads
        registrar.playToServer(
            DimTripPayload.TYPE,
            DimTripPayload.STREAM_CODEC,
            DimTripPayload::handle
        )

        registrar.playToServer(
            BuglePayload.TYPE,
            BuglePayload.STREAM_CODEC,
            BuglePayload::handle
        )

        // Server -> Client payloads
        registrar.playToClient(
            TofuRadarPayload.TYPE,
            TofuRadarPayload.STREAM_CODEC,
            TofuRadarPayload::handle
        )

        registrar.playToClient(
            TfMachineDataPayload.TYPE,
            TfMachineDataPayload.STREAM_CODEC,
            TfMachineDataPayload::handle
        )
    }
}
