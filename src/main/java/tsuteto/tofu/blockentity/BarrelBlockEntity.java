package tsuteto.tofu.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import tsuteto.tofu.block.BarrelBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.init.TcItems;

import javax.annotation.Nullable;

/**
 * Barrel block entity for fermentation.
 * Contains 2 inventory slots (input and output) and a fermentation progress counter.
 * Fermentation is boosted when a morijio block is nearby.
 *
 * Slot layout:
 *   0 = input
 *   1 = output
 */
public class BarrelBlockEntity extends BlockEntity implements Container {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int INVENTORY_SIZE = 2;

    /** Total ticks required for one fermentation cycle */
    public static final int FERMENTATION_TOTAL = 6000;

    /** Speed multiplier when morijio is nearby */
    public static final int MORIJIO_SPEED_BONUS = 2;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int fermentationProgress;
    private boolean hasMorijioNearby;

    /** Timer to reset morijio flag if not refreshed */
    private int morijioCheckTimer;
    private static final int MORIJIO_TIMEOUT = 40;

    public BarrelBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.BARREL.get(), pos, state);
    }

    public ItemStackHandler getInventoryHandler() {
        return inventory;
    }

    public int getFermentationProgress() {
        return fermentationProgress;
    }

    public void setHasMorijioNearby(boolean nearby) {
        this.hasMorijioNearby = nearby;
        this.morijioCheckTimer = 0;
    }

    /**
     * Drops the output item at the given position. Called by the BarrelBlock
     * when a player interacts with a completed barrel.
     */
    public void dropResults(Level level, BlockPos pos) {
        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if (!output.isEmpty()) {
            Containers.dropItemStack(level, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, output.copy());
            inventory.setStackInSlot(SLOT_OUTPUT, ItemStack.EMPTY);
        }
        fermentationProgress = 0;
        setChanged();
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BarrelBlockEntity blockEntity) {
        boolean changed = false;

        // Handle morijio timeout
        blockEntity.morijioCheckTimer++;
        if (blockEntity.morijioCheckTimer >= MORIJIO_TIMEOUT) {
            blockEntity.hasMorijioNearby = false;
            blockEntity.morijioCheckTimer = 0;
        }

        // Only ferment if there is an input and the output is empty or has room
        ItemStack inputStack = blockEntity.inventory.getStackInSlot(SLOT_INPUT);
        if (!inputStack.isEmpty() && blockEntity.canFerment()) {
            int speed = 1;
            if (blockEntity.hasMorijioNearby) {
                speed = MORIJIO_SPEED_BONUS;
            }

            // Apply fermentation rate from the block
            if (state.getBlock() instanceof BarrelBlock barrelBlock) {
                speed *= barrelBlock.getFermentationRate();
            }

            blockEntity.fermentationProgress += speed;
            changed = true;

            if (blockEntity.fermentationProgress >= FERMENTATION_TOTAL) {
                blockEntity.completeFermentation();

                // Update block state stage
                int currentStage = state.getValue(BarrelBlock.STAGE);
                if (currentStage < BarrelBlock.MAX_STAGE) {
                    level.setBlock(pos, state.setValue(BarrelBlock.STAGE, BarrelBlock.MAX_STAGE), 3);
                }
            } else {
                // Update intermediate stage
                int newStage = (int) ((float) blockEntity.fermentationProgress / FERMENTATION_TOTAL * BarrelBlock.MAX_STAGE);
                newStage = Math.min(newStage, BarrelBlock.MAX_STAGE - 1);
                int currentStage = state.getValue(BarrelBlock.STAGE);
                if (newStage != currentStage) {
                    level.setBlock(pos, state.setValue(BarrelBlock.STAGE, newStage), 3);
                }
            }
        }

        if (changed) {
            blockEntity.setChanged();
        }
    }

    private boolean canFerment() {
        ItemStack outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (outputStack.isEmpty()) {
            return true;
        }
        // Check output is not full
        return outputStack.getCount() < outputStack.getMaxStackSize();
    }

    private void completeFermentation() {
        ItemStack inputStack = inventory.getStackInSlot(SLOT_INPUT);
        if (inputStack.isEmpty()) return;

        // Determine output based on input
        ItemStack result = getFermentationResult(inputStack);
        if (result.isEmpty()) return;

        ItemStack outputStack = inventory.getStackInSlot(SLOT_OUTPUT);
        if (outputStack.isEmpty()) {
            inventory.setStackInSlot(SLOT_OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(outputStack, result)
                && outputStack.getCount() < outputStack.getMaxStackSize()) {
            outputStack.grow(result.getCount());
        }

        inputStack.shrink(1);
        fermentationProgress = 0;
        setChanged();
    }

    /**
     * Determines the fermentation output for a given input item.
     * This is a simplified mapping; in a full implementation this could use a recipe system.
     */
    private ItemStack getFermentationResult(ItemStack input) {
        if (input.is(TcItems.KOJI.get())) {
            return new ItemStack(TcItems.MISO.get());
        }
        if (input.is(TcItems.SOYBEANS.get())) {
            return new ItemStack(TcItems.NATTO.get());
        }
        // Default: no result for unrecognized inputs
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("FermentationProgress", fermentationProgress);
        tag.putBoolean("HasMorijio", hasMorijioNearby);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fermentationProgress = tag.getInt("FermentationProgress");
        hasMorijioNearby = tag.getBoolean("HasMorijio");
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
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
