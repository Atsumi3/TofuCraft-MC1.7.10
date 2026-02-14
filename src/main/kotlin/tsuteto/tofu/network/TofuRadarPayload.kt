package tsuteto.tofu.network

import net.minecraft.ChatFormatting
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.IPayloadContext
import tsuteto.tofu.TofuCraftMod
import kotlin.math.roundToInt

/**
 * Server-to-client payload that sends tofu radar scan results.
 * Contains the distance to the nearest detected tofu entity and
 * the cardinal direction string.
 */
@JvmRecord
data class TofuRadarPayload(val distance: Float, val direction: String) : CustomPacketPayload {

    companion object {
        @JvmField
        val TYPE: CustomPacketPayload.Type<TofuRadarPayload> =
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_radar"))

        @JvmField
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, TofuRadarPayload> =
            StreamCodec.composite(
                ByteBufCodecs.FLOAT,
                TofuRadarPayload::distance,
                ByteBufCodecs.STRING_UTF8,
                TofuRadarPayload::direction,
                ::TofuRadarPayload
            )
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    /**
     * Handles the payload on the client side.
     * Displays the radar result as an action bar message.
     */
    fun handle(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() ?: return@enqueueWork
            if (distance < 0) {
                // Negative distance indicates no target found
                player.displayClientMessage(
                    Component.translatable("item.tofucraft.tofu_radar.not_found")
                        .withStyle(ChatFormatting.GRAY),
                    true
                )
            } else {
                val distRounded = distance.roundToInt()
                player.displayClientMessage(
                    Component.translatable("item.tofucraft.tofu_radar.found", distRounded, direction)
                        .withStyle(ChatFormatting.GREEN),
                    true
                )
            }
        }
    }
}
