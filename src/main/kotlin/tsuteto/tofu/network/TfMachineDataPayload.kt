package tsuteto.tofu.network

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.network.handling.IPayloadContext
import tsuteto.tofu.TofuCraftMod

/**
 * Server-to-client payload that synchronizes TF machine energy and progress data.
 * Contains the block position along with the current energy and progress values.
 */
@JvmRecord
data class TfMachineDataPayload(val blockPos: BlockPos, val energy: Int, val progress: Int) : CustomPacketPayload {

    companion object {
        @JvmField
        val TYPE: CustomPacketPayload.Type<TfMachineDataPayload> =
            CustomPacketPayload.Type(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_machine_data"))

        @JvmField
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, TfMachineDataPayload> =
            StreamCodec.composite(
                BlockPos.STREAM_CODEC,
                TfMachineDataPayload::blockPos,
                ByteBufCodecs.VAR_INT,
                TfMachineDataPayload::energy,
                ByteBufCodecs.VAR_INT,
                TfMachineDataPayload::progress,
                ::TfMachineDataPayload
            )
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    /**
     * Handles the payload on the client side.
     * Looks up the block entity at the specified position and updates its
     * client-side energy and progress data for rendering.
     */
    fun handle(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() ?: return@enqueueWork
            val level = player.level()
            if (level.isLoaded(blockPos)) {
                val be = level.getBlockEntity(blockPos)
                if (be != null) {
                    // Update the block entity's persistent data with the synced values.
                    // TF machine block entities should read these fields during rendering.
                    val updateTag = CompoundTag()
                    updateTag.putInt("tf_energy", energy)
                    updateTag.putInt("tf_progress", progress)
                    be.handleUpdateTag(updateTag, level.registryAccess())
                }
            }
        }
    }
}
