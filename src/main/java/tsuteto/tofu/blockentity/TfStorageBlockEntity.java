package tsuteto.tofu.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import tsuteto.tofu.block.TfStorageBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.menu.TfStorageMenu;

import javax.annotation.Nullable;

/**
 * TF Storage block entity.
 * A large storage unit (27 slots, like a chest) that also stores Tofu Force energy.
 * The ACTIVE block state is set based on whether the storage contains energy.
 */
public class TfStorageBlockEntity extends BlockEntity implements Container, MenuProvider {

    public static final int INVENTORY_SIZE = 27;
    public static final int MAX_TF_ENERGY = 10000;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int tfEnergy;
    private Component customName;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> tfEnergy;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> tfEnergy = value;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public TfStorageBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_STORAGE.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.tf_storage");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TfStorageMenu(containerId, playerInventory, this, this.dataAccess);
    }

    public int getTfEnergy() {
        return tfEnergy;
    }

    public void setTfEnergy(int energy) {
        this.tfEnergy = Math.max(0, Math.min(energy, MAX_TF_ENERGY));
        setChanged();
    }

    /**
     * Adds energy to this storage, returning the amount that was actually accepted.
     */
    public int addEnergy(int amount) {
        int accepted = Math.min(amount, MAX_TF_ENERGY - tfEnergy);
        tfEnergy += accepted;
        if (accepted > 0) {
            setChanged();
        }
        return accepted;
    }

    /**
     * Extracts energy from this storage, returning the amount that was actually extracted.
     */
    public int extractEnergy(int amount) {
        int extracted = Math.min(amount, tfEnergy);
        tfEnergy -= extracted;
        if (extracted > 0) {
            setChanged();
        }
        return extracted;
    }

    public ItemStackHandler getInventoryHandler() {
        return inventory;
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfStorageBlockEntity blockEntity) {
        // Update ACTIVE block state based on energy presence
        boolean hasEnergy = blockEntity.tfEnergy > 0;
        if (state.getValue(TfStorageBlock.ACTIVE) != hasEnergy) {
            level.setBlock(pos, state.setValue(TfStorageBlock.ACTIVE, hasEnergy), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
        tag.put("Inventory", inventory.serializeNBT(registries));
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tfEnergy = tag.getInt("TfEnergy");
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
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

    @Override
    public int getContainerSize() {
        return INVENTORY_SIZE;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = inventory.getStackInSlot(slot);
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack result;
        if (amount >= stack.getCount()) {
            result = stack.copy();
            inventory.setStackInSlot(slot, ItemStack.EMPTY);
        } else {
            result = stack.split(amount);
        }
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = inventory.getStackInSlot(slot);
        inventory.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
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
        for (int i = 0; i < inventory.getSlots(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
