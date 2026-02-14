package tsuteto.tofu.network;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tsuteto.tofu.TofuCraftMod;

/**
 * Client-to-server payload that triggers dimension travel (e.g. through the Tofu Portal).
 * Contains the target dimension ResourceKey to travel to.
 */
public record DimTripPayload(ResourceKey<Level> targetDimension) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DimTripPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "dim_trip"));

    public static final StreamCodec<net.minecraft.network.RegistryFriendlyByteBuf, DimTripPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ResourceKey.streamCodec(Registries.DIMENSION),
                    DimTripPayload::targetDimension,
                    DimTripPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the payload on the server side.
     * Teleports the sending player to the specified dimension.
     */
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                ServerLevel targetLevel = serverPlayer.server.getLevel(targetDimension);
                if (targetLevel != null && targetLevel != serverPlayer.serverLevel()) {
                    serverPlayer.changeDimension(targetLevel);
                    TofuCraftMod.LOGGER.debug("Player {} teleported to dimension {}",
                            serverPlayer.getName().getString(), targetDimension.location());
                }
            }
        });
    }
}
