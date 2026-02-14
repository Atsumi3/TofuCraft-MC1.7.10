package tsuteto.tofu.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tsuteto.tofu.init.TcBlockEntities;

import javax.annotation.Nullable;

/**
 * Morijio (mound of salt) block entity.
 * A simple block entity that counts ticks and provides a fermentation aura
 * to nearby barrel blocks within a certain radius. The timer accumulates
 * and can be used to determine the strength/age of the morijio.
 */
public class MorijioBlockEntity extends BlockEntity {

    /** How many ticks this morijio has been active */
    private int timer;

    /** The radius in blocks within which this morijio affects barrels */
    public static final int AURA_RADIUS = 4;

    /** How often (in ticks) the morijio checks for nearby barrels */
    private static final int CHECK_INTERVAL = 20;

    public MorijioBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.MORIJIO.get(), pos, state);
    }

    public int getTimer() {
        return timer;
    }

    /**
     * Returns true if this morijio has been active long enough to provide a fermentation boost.
     * Requires at least 100 ticks (5 seconds) of activity.
     */
    public boolean isActive() {
        return timer >= 100;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MorijioBlockEntity blockEntity) {
        blockEntity.timer++;

        // Periodically notify nearby barrel block entities of our presence
        if (blockEntity.timer % CHECK_INTERVAL == 0 && blockEntity.isActive()) {
            notifyNearbyBarrels(level, pos);
        }
    }

    /**
     * Scans for nearby barrel block entities and marks them as having a morijio aura.
     */
    private static void notifyNearbyBarrels(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int dx = -AURA_RADIUS; dx <= AURA_RADIUS; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -AURA_RADIUS; dz <= AURA_RADIUS; dz++) {
                    mutablePos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                    BlockEntity be = level.getBlockEntity(mutablePos);
                    if (be instanceof BarrelBlockEntity barrel) {
                        barrel.setHasMorijioNearby(true);
                    }
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Timer", timer);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        timer = tag.getInt("Timer");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
