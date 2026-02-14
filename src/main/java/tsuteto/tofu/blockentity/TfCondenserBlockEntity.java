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
import tsuteto.tofu.block.TfCondenserBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.menu.TfCondenserMenu;

import javax.annotation.Nullable;

/**
 * TF Condenser block entity.
 * Condenses items using Tofu Force energy. Takes two inputs and produces one output.
 *
 * Slot layout:
 *   0 = input 1
 *   1 = input 2
 *   2 = output
 */
public class TfCondenserBlockEntity extends BlockEntity implements Container, MenuProvider {

    public static final int SLOT_INPUT_1 = 0;
    public static final int SLOT_INPUT_2 = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int INVENTORY_SIZE = 3;

    public static final int MAX_TF_ENERGY = 10000;
    public static final int MAX_PROGRESS = 200;
    public static final int ENERGY_PER_TICK = 5;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int tfEnergy;
    private int progress;

    private Component customName;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> tfEnergy;
                case 1 -> progress;
                case 2 -> MAX_PROGRESS;
                case 3 -> MAX_TF_ENERGY;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> tfEnergy = value;
                case 1 -> progress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public TfCondenserBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_CONDENSER.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.tf_condenser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TfCondenserMenu(containerId, playerInventory, this, this.dataAccess);
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

    public ItemStackHandler getInventoryHandler() {
        return inventory;
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfCondenserBlockEntity blockEntity) {
        boolean wasActive = state.getValue(TfCondenserBlock.ACTIVE);
        boolean isProcessing = false;

        if (blockEntity.canCondense()) {
            if (blockEntity.tfEnergy >= ENERGY_PER_TICK) {
                blockEntity.tfEnergy -= ENERGY_PER_TICK;
                blockEntity.progress++;
                isProcessing = true;

                if (blockEntity.progress >= MAX_PROGRESS) {
                    blockEntity.condenseItem();
                    blockEntity.progress = 0;
                }
                blockEntity.setChanged();
            } else {
                // Not enough energy, stall
                if (blockEntity.progress > 0) {
                    blockEntity.progress = Math.max(0, blockEntity.progress - 2);
                    blockEntity.setChanged();
                }
            }
        } else {
            if (blockEntity.progress > 0) {
                blockEntity.progress = 0;
                blockEntity.setChanged();
            }
        }

        // Update ACTIVE block state
        if (wasActive != isProcessing) {
            level.setBlock(pos, state.setValue(TfCondenserBlock.ACTIVE, isProcessing), 3);
        }
    }

    private boolean canCondense() {
        ItemStack input1 = inventory.getStackInSlot(SLOT_INPUT_1);
        ItemStack input2 = inventory.getStackInSlot(SLOT_INPUT_2);
        if (input1.isEmpty() || input2.isEmpty()) {
            return false;
        }

        ItemStack result = getCondenserResult(input1, input2);
        if (result.isEmpty()) {
            return false;
        }

        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) {
            return true;
        }
        if (!ItemStack.isSameItemSameComponents(output, result)) {
            return false;
        }
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void condenseItem() {
        ItemStack input1 = inventory.getStackInSlot(SLOT_INPUT_1);
        ItemStack input2 = inventory.getStackInSlot(SLOT_INPUT_2);
        ItemStack result = getCondenserResult(input1, input2);
        if (result.isEmpty()) return;

        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) {
            inventory.setStackInSlot(SLOT_OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(output, result)) {
            output.grow(result.getCount());
        }

        input1.shrink(1);
        input2.shrink(1);
        setChanged();
    }

    /**
     * Determines the condensing output for given inputs.
     * In a full implementation, this would use the TF Condenser recipe type.
     * For now, returns empty to be populated by recipes.
     */
    private ItemStack getCondenserResult(ItemStack input1, ItemStack input2) {
        // TODO: Look up TfCondenserRecipe from the recipe manager
        // For now this is a placeholder. The recipe system will handle actual lookups.
        if (level != null) {
            // Recipe lookup would go here when TfCondenserRecipe is implemented
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
        tag.putInt("Progress", progress);
        tag.put("Inventory", inventory.serializeNBT(registries));
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tfEnergy = tag.getInt("TfEnergy");
        progress = tag.getInt("Progress");
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
