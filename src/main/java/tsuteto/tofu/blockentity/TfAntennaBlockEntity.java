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
 * TF Antenna block entity.
 * Receives Tofu Force energy from the dimension/environment at a higher rate
 * than a collector, and distributes it to connected TF Collector blocks below
 * or adjacent to it. Acts as a high-capacity energy receiver.
 * No inventory slots.
 */
public class TfAntennaBlockEntity extends BlockEntity {

    public static final int MAX_TF_ENERGY = 50000;
    public static final int RECEIVE_RATE = 5;
    public static final int RECEIVE_INTERVAL = 10;
    public static final int DISTRIBUTE_AMOUNT = 100;
    public static final int DISTRIBUTE_INTERVAL = 5;

    /** Maximum vertical distance to search for collectors below */
    public static final int MAX_SEARCH_DISTANCE = 8;

    private int tfEnergy;

    public TfAntennaBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_ANTENNA.get(), pos, state);
    }

    public int getTfEnergy() {
        return tfEnergy;
    }

    public void setTfEnergy(int energy) {
        this.tfEnergy = Math.max(0, Math.min(energy, MAX_TF_ENERGY));
        setChanged();
    }

    public int addEnergy(int amount) {
        int accepted = Math.min(amount, MAX_TF_ENERGY - tfEnergy);
        tfEnergy += accepted;
        if (accepted > 0) setChanged();
        return accepted;
    }

    public int extractEnergy(int amount) {
        int extracted = Math.min(amount, tfEnergy);
        tfEnergy -= extracted;
        if (extracted > 0) setChanged();
        return extracted;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfAntennaBlockEntity blockEntity) {
        long gameTime = level.getGameTime();

        // Receive ambient energy from the dimension
        if (gameTime % RECEIVE_INTERVAL == 0) {
            if (blockEntity.tfEnergy < MAX_TF_ENERGY) {
                // Higher altitude = better reception
                int rate = RECEIVE_RATE;
                int height = pos.getY();
                if (height > 128) {
                    rate *= 2;
                } else if (height > 200) {
                    rate *= 3;
                }

                // Check if the antenna has a clear view of the sky
                if (level.canSeeSky(pos.above())) {
                    blockEntity.tfEnergy = Math.min(blockEntity.tfEnergy + rate, MAX_TF_ENERGY);
                    blockEntity.setChanged();
                }
            }
        }

        // Distribute energy to connected collectors
        if (gameTime % DISTRIBUTE_INTERVAL == 0 && blockEntity.tfEnergy > 0) {
            blockEntity.distributeToCollectors(level, pos);
        }
    }

    /**
     * Distributes stored energy to TF Collector blocks found below and adjacent.
     * Searches downward up to MAX_SEARCH_DISTANCE blocks, and also checks
     * horizontally adjacent positions at each level.
     */
    private void distributeToCollectors(Level level, BlockPos pos) {
        // Check directly below (column search)
        for (int dy = 1; dy <= MAX_SEARCH_DISTANCE; dy++) {
            if (tfEnergy <= 0) break;

            BlockPos belowPos = pos.below(dy);
            BlockEntity belowBE = level.getBlockEntity(belowPos);

            if (belowBE instanceof TfCollectorBlockEntity collector) {
                int toTransfer = Math.min(DISTRIBUTE_AMOUNT, tfEnergy);
                int transferred = collector.addEnergy(toTransfer);
                if (transferred > 0) {
                    tfEnergy -= transferred;
                    setChanged();
                }
            }
        }

        // Also check horizontally adjacent blocks at our level
        BlockPos[] adjacentPositions = {
                pos.north(), pos.south(), pos.east(), pos.west()
        };
        for (BlockPos adjPos : adjacentPositions) {
            if (tfEnergy <= 0) break;

            BlockEntity adjBE = level.getBlockEntity(adjPos);
            if (adjBE instanceof TfCollectorBlockEntity collector) {
                int toTransfer = Math.min(DISTRIBUTE_AMOUNT, tfEnergy);
                int transferred = collector.addEnergy(toTransfer);
                if (transferred > 0) {
                    tfEnergy -= transferred;
                    setChanged();
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tfEnergy = tag.getInt("TfEnergy");
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
