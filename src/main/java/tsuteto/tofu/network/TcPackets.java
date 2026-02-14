package tsuteto.tofu.network;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import tsuteto.tofu.TofuCraftMod;

/**
 * Registers all custom network payloads for TofuCraft using NeoForge's payload system.
 *
 * Payloads registered:
 * <ul>
 *   <li>{@link DimTripPayload} - client to server: trigger dimension travel</li>
 *   <li>{@link BuglePayload} - client to server: play bugle sound</li>
 *   <li>{@link TofuRadarPayload} - server to client: radar scan result data</li>
 *   <li>{@link TfMachineDataPayload} - server to client: machine energy/progress sync</li>
 * </ul>
 */
public class TcPackets {

    /**
     * Called from the mod constructor to register this class as a listener
     * on the mod event bus for payload registration.
     */
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(TcPackets::onRegisterPayloadHandlers);
    }

    /**
     * Registers all custom packet payloads with the NeoForge networking system.
     * Each payload specifies its type, stream codec, and handler.
     */
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(TofuCraftMod.MOD_ID)
                .versioned("1.0.0");

        // Client -> Server payloads
        registrar.playToServer(
                DimTripPayload.TYPE,
                DimTripPayload.STREAM_CODEC,
                DimTripPayload::handle
        );

        registrar.playToServer(
                BuglePayload.TYPE,
                BuglePayload.STREAM_CODEC,
                BuglePayload::handle
        );

        // Server -> Client payloads
        registrar.playToClient(
                TofuRadarPayload.TYPE,
                TofuRadarPayload.STREAM_CODEC,
                TofuRadarPayload::handle
        );

        registrar.playToClient(
                TfMachineDataPayload.TYPE,
                TfMachineDataPayload.STREAM_CODEC,
                TfMachineDataPayload::handle
        );
    }
}
