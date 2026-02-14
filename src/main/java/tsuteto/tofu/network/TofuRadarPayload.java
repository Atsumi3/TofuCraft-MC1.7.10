package tsuteto.tofu.network;

import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tsuteto.tofu.TofuCraftMod;

/**
 * Server-to-client payload that sends tofu radar scan results.
 * Contains the distance to the nearest detected tofu entity and
 * the cardinal direction string.
 */
public record TofuRadarPayload(float distance, String direction) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TofuRadarPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_radar"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TofuRadarPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.FLOAT,
                    TofuRadarPayload::distance,
                    ByteBufCodecs.STRING_UTF8,
                    TofuRadarPayload::direction,
                    TofuRadarPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the payload on the client side.
     * Displays the radar result as an action bar message.
     */
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                if (distance < 0) {
                    // Negative distance indicates no target found
                    player.displayClientMessage(
                            Component.translatable("item.tofucraft.tofu_radar.not_found")
                                    .withStyle(ChatFormatting.GRAY),
                            true);
                } else {
                    int distRounded = Math.round(distance);
                    player.displayClientMessage(
                            Component.translatable("item.tofucraft.tofu_radar.found",
                                            distRounded, direction)
                                    .withStyle(ChatFormatting.GREEN),
                            true);
                }
            }
        });
    }
}
