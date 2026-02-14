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
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import tsuteto.tofu.block.TfSaturatorBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.menu.TfSaturatorMenu;

import javax.annotation.Nullable;

/**
 * TF Saturator block entity.
 * Saturates items with soymilk using Tofu Force energy.
 * Has a FluidTank for soymilk storage.
 *
 * Slot layout:
 *   0 = input
 *   1 = output
 */
public class TfSaturatorBlockEntity extends BlockEntity implements Container, MenuProvider {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int INVENTORY_SIZE = 2;

    public static final int MAX_TF_ENERGY = 10000;
    public static final int SOYMILK_CAPACITY = 4000;
    public static final int MAX_PROGRESS = 160;
    public static final int ENERGY_PER_TICK = 4;
    public static final int SOYMILK_PER_OPERATION = 200;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final FluidTank soymilkTank = new FluidTank(SOYMILK_CAPACITY, stack ->
            stack.getFluid() == TcFluids.SOYMILK_SOURCE.get()) {
        @Override
        protected void onContentsChanged() {
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
                case 4 -> soymilkTank.getFluidAmount();
                case 5 -> SOYMILK_CAPACITY;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> tfEnergy = value;
                case 1 -> progress = value;
                case 4 -> {
                    if (soymilkTank.isEmpty() && value > 0) {
                        soymilkTank.fill(new FluidStack(TcFluids.SOYMILK_SOURCE.get(), value),
                                IFluidHandler.FluidAction.EXECUTE);
                    } else if (!soymilkTank.isEmpty()) {
                        soymilkTank.getFluid().setAmount(value);
                    }
                }
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };

    public TfSaturatorBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_SATURATOR.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.tf_saturator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TfSaturatorMenu(containerId, playerInventory, this, this.dataAccess);
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

    public FluidTank getSoymilkTank() {
        return soymilkTank;
    }

    public ItemStackHandler getInventoryHandler() {
        return inventory;
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfSaturatorBlockEntity blockEntity) {
        boolean wasActive = state.getValue(TfSaturatorBlock.ACTIVE);
        boolean isProcessing = false;

        if (blockEntity.canSaturate()) {
            if (blockEntity.tfEnergy >= ENERGY_PER_TICK) {
                blockEntity.tfEnergy -= ENERGY_PER_TICK;
                blockEntity.progress++;
                isProcessing = true;

                if (blockEntity.progress >= MAX_PROGRESS) {
                    blockEntity.saturateItem();
                    blockEntity.progress = 0;
                }
                blockEntity.setChanged();
            } else {
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
            level.setBlock(pos, state.setValue(TfSaturatorBlock.ACTIVE, isProcessing), 3);
        }
    }

    private boolean canSaturate() {
        ItemStack input = inventory.getStackInSlot(SLOT_INPUT);
        if (input.isEmpty()) return false;
        if (soymilkTank.getFluidAmount() < SOYMILK_PER_OPERATION) return false;

        // TODO: Look up saturation recipe
        // For now, check basic conditions
        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        return output.isEmpty() || output.getCount() < output.getMaxStackSize();
    }

    private void saturateItem() {
        ItemStack input = inventory.getStackInSlot(SLOT_INPUT);
        if (input.isEmpty()) return;

        // Consume soymilk
        soymilkTank.drain(SOYMILK_PER_OPERATION, IFluidHandler.FluidAction.EXECUTE);

        // TODO: Look up saturation recipe for proper output
        // For now, this is a placeholder for when recipes are implemented
        // The actual saturate logic will depend on a recipe lookup

        input.shrink(1);
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
        tag.putInt("Progress", progress);
        tag.put("Inventory", inventory.serializeNBT(registries));
        tag.put("SoymilkTank", soymilkTank.writeToNBT(registries, new CompoundTag()));
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
        if (tag.contains("SoymilkTank")) {
            soymilkTank.readFromNBT(registries, tag.getCompound("SoymilkTank"));
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
