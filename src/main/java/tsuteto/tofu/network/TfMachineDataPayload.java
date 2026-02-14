package tsuteto.tofu.network;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import tsuteto.tofu.TofuCraftMod;

/**
 * Server-to-client payload that synchronizes TF machine energy and progress data.
 * Contains the block position along with the current energy and progress values.
 */
public record TfMachineDataPayload(BlockPos blockPos, int energy, int progress) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<TfMachineDataPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tf_machine_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TfMachineDataPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    TfMachineDataPayload::blockPos,
                    ByteBufCodecs.VAR_INT,
                    TfMachineDataPayload::energy,
                    ByteBufCodecs.VAR_INT,
                    TfMachineDataPayload::progress,
                    TfMachineDataPayload::new
            );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    /**
     * Handles the payload on the client side.
     * Looks up the block entity at the specified position and updates its
     * client-side energy and progress data for rendering.
     */
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player != null) {
                Level level = player.level();
                if (level.isLoaded(blockPos)) {
                    BlockEntity be = level.getBlockEntity(blockPos);
                    if (be != null) {
                        // Update the block entity's persistent data with the synced values.
                        // TF machine block entities should read these fields during rendering.
                        CompoundTag updateTag = new CompoundTag();
                        updateTag.putInt("tf_energy", energy);
                        updateTag.putInt("tf_progress", progress);
                        be.handleUpdateTag(updateTag, level.registryAccess());
                    }
                }
            }
        });
    }
}
