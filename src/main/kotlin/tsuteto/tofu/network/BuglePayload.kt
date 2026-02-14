package tsuteto.tofu.network

import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.neoforged.neoforge.network.handling.IPayloadContext
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.init.TcSoundEvents

/**
 * Client-to-server payload that triggers the tofu bugle sound at the player's location.
 * This is a simple signal payload with no data fields.
 */
@JvmRecord
data class BuglePayload(private val dummy: Unit = Unit) : CustomPacketPayload {

    constructor() : this(Unit)

    companion object {
        @JvmField
        val TYPE: CustomPacketPayload.Type<BuglePayload> =
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "bugle"))

        @JvmField
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, BuglePayload> =
            StreamCodec.unit(BuglePayload())
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    /**
     * Handles the payload on the server side.
     * Plays the tofu bugle sound at the sending player's location.
     */
    fun handle(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player()
            if (player is ServerPlayer) {
                player.level().playSound(
                    null,
                    player.x,
                    player.y,
                    player.z,
                    TcSoundEvents.TOFU_BUGLE.get(),
                    SoundSource.PLAYERS,
                    3.0f,
                    1.0f
                )
            }
        }
    }
}
