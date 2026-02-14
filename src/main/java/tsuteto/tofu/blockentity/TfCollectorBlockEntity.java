package tsuteto.tofu.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import tsuteto.tofu.init.TcBlockEntities;

import javax.annotation.Nullable;

/**
 * TF Collector block entity.
 * Collects ambient Tofu Force energy over time and distributes it
 * to adjacent TF machines (storage, condenser, oven, reformer, saturator).
 * No inventory slots.
 */
public class TfCollectorBlockEntity extends BlockEntity implements Container {

    public static final int MAX_TF_ENERGY = 10000;
    public static final int COLLECT_RATE = 1;
    public static final int COLLECT_INTERVAL = 10;
    public static final int DISTRIBUTE_AMOUNT = 20;
    public static final int DISTRIBUTE_INTERVAL = 5;

    private int tfEnergy;

    private Component customName;

    public TfCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_COLLECTOR.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.tf_collector");
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

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfCollectorBlockEntity blockEntity) {
        long gameTime = level.getGameTime();

        // Collect ambient energy
        if (gameTime % COLLECT_INTERVAL == 0) {
            if (blockEntity.tfEnergy < MAX_TF_ENERGY) {
                // Collection rate can be influenced by environment (e.g., Tofu dimension boosts)
                int rate = COLLECT_RATE;
                blockEntity.tfEnergy = Math.min(blockEntity.tfEnergy + rate, MAX_TF_ENERGY);
                blockEntity.setChanged();
            }
        }

        // Distribute energy to adjacent machines
        if (gameTime % DISTRIBUTE_INTERVAL == 0 && blockEntity.tfEnergy > 0) {
            blockEntity.distributeEnergy(level, pos);
        }
    }

    private void distributeEnergy(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (tfEnergy <= 0) break;

            BlockPos adjacentPos = pos.relative(direction);
            BlockEntity adjacentBE = level.getBlockEntity(adjacentPos);
            if (adjacentBE == null) continue;

            int transferred = 0;
            int toTransfer = Math.min(DISTRIBUTE_AMOUNT, tfEnergy);

            if (adjacentBE instanceof TfStorageBlockEntity storage) {
                transferred = storage.addEnergy(toTransfer);
            } else if (adjacentBE instanceof TfCondenserBlockEntity condenser) {
                transferred = condenser.addEnergy(toTransfer);
            } else if (adjacentBE instanceof TfOvenBlockEntity oven) {
                transferred = oven.addEnergy(toTransfer);
            } else if (adjacentBE instanceof TfReformerBlockEntity reformer) {
                transferred = reformer.addEnergy(toTransfer);
            } else if (adjacentBE instanceof TfSaturatorBlockEntity saturator) {
                transferred = saturator.addEnergy(toTransfer);
            }

            if (transferred > 0) {
                tfEnergy -= transferred;
                setChanged();
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tfEnergy = tag.getInt("TfEnergy");
        if (tag.contains("CustomName")) {
            customName = Component.Serializer.fromJson(tag.getString("CustomName"), registries);
        }
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

    // === Container implementation for Containers.dropContents compatibility ===
    // The collector has no inventory, but we implement Container because
    // the block class calls Containers.dropContents on removal.

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        // No inventory
    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level == null || this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(this.worldPosition.getX() + 0.5,
                this.worldPosition.getY() + 0.5,
                this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        // No inventory
    }
}
