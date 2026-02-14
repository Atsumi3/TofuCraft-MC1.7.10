package tsuteto.tofu.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.init.TcSoundEvents;

/**
 * Client-to-server payload that triggers the tofu bugle sound at the player's location.
 * This is a simple signal payload with no data fields.
 */
public record BuglePayload() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<BuglePayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "bugle"));

    public static final StreamCodec<RegistryFriendlyByteBuf, BuglePayload> STREAM_CODEC =
            StreamCodec.unit(new BuglePayload());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the payload on the server side.
     * Plays the tofu bugle sound at the sending player's location.
     */
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                serverPlayer.level().playSound(
                        null,
                        serverPlayer.getX(),
                        serverPlayer.getY(),
                        serverPlayer.getZ(),
                        TcSoundEvents.TOFU_BUGLE.get(),
                        SoundSource.PLAYERS,
                        3.0F,
                        1.0F
                );
            }
        });
    }
}
