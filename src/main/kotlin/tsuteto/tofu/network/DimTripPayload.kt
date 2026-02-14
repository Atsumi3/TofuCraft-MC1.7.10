package tsuteto.tofu.network

import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.neoforged.neoforge.network.handling.IPayloadContext
import tsuteto.tofu.TofuCraftMod

/**
 * Client-to-server payload that triggers dimension travel (e.g. through the Tofu Portal).
 * Contains the target dimension ResourceKey to travel to.
 */
@JvmRecord
data class DimTripPayload(val targetDimension: ResourceKey<Level>) : CustomPacketPayload {

    companion object {
        @JvmField
        val TYPE: CustomPacketPayload.Type<DimTripPayload> =
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "dim_trip"))

        @JvmField
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, DimTripPayload> =
            StreamCodec.composite(
                ResourceKey.streamCodec(Registries.DIMENSION),
                DimTripPayload::targetDimension,
                ::DimTripPayload
            )
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    /**
     * Handles the payload on the server side.
     * Teleports the sending player to the specified dimension.
     */
    fun handle(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player()
            if (player is ServerPlayer) {
                val targetLevel = player.server.getLevel(targetDimension)
                if (targetLevel != null && targetLevel != player.serverLevel()) {
                    player.changeDimension(targetLevel)
                    TofuCraftMod.LOGGER.debug(
                        "Player {} teleported to dimension {}",
                        player.name.string, targetDimension.location()
                    )
                }
            }
        }
    }
}
