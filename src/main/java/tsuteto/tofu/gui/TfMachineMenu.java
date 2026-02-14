package tsuteto.tofu.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;

/**
 * Abstract base menu class for TofuFactory machine GUIs.
 * Provides common slot layout, ContainerData syncing for energy and progress,
 * and a standard quickMoveStack implementation.
 *
 * Subclasses should call {@link #addMachineSlots()} and {@link #addPlayerInventory(Inventory)}
 * in their constructor, then {@link #addDataSlots(ContainerData)}.
 *
 * ContainerData layout: 0=energy, 1=maxEnergy, 2=progress, 3=maxProgress
 */
public abstract class TfMachineMenu extends AbstractContainerMenu {

    /** ContainerData indices */
    public static final int DATA_ENERGY = 0;
    public static final int DATA_MAX_ENERGY = 1;
    public static final int DATA_PROGRESS = 2;
    public static final int DATA_MAX_PROGRESS = 3;
    public static final int DATA_COUNT = 4;

    protected final BlockEntity blockEntity;
    protected final ContainerData data;

    /** The number of machine-specific slots (set by subclass) */
    protected int machineSlotCount;

    protected TfMachineMenu(@Nullable MenuType<?> menuType, int containerId,
                            BlockEntity blockEntity, ContainerData data, int machineSlotCount) {
        super(menuType, containerId);
        this.blockEntity = blockEntity;
        this.data = data;
        this.machineSlotCount = machineSlotCount;

        checkContainerDataCount(data, DATA_COUNT);
    }

    /**
     * Subclasses implement this to add their machine-specific slots
     * (input, output, catalyst, etc.).
     */
    protected abstract void addMachineSlots();

    /**
     * Adds the standard 27+9 player inventory slots at the default position.
     * Call this in your subclass constructor after addMachineSlots().
     */
    protected void addPlayerInventory(Inventory playerInventory) {
        addPlayerInventory(playerInventory, 8, 84);
    }

    /**
     * Adds the standard 27+9 player inventory slots at a custom position.
     */
    protected void addPlayerInventory(Inventory playerInventory, int startX, int startY) {
        // Main inventory (3 rows of 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9,
                        startX + col * 18, startY + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col,
                    startX + col * 18, startY + 58));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.getBlockPos().closerToCenterThan(player.position(), 8.0);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            resultStack = slotStack.copy();

            int playerInvStart = machineSlotCount;
            int playerInvEnd = playerInvStart + 27;
            int hotbarStart = playerInvEnd;
            int hotbarEnd = hotbarStart + 9;

            if (slotIndex < machineSlotCount) {
                // Moving from machine slots to player inventory
                if (!this.moveItemStackTo(slotStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, resultStack);
            } else {
                // Moving from player inventory to machine input slots
                if (!this.moveItemStackTo(slotStack, 0, machineSlotCount, false)) {
                    // Move between inventory and hotbar
                    if (slotIndex < playerInvEnd) {
                        if (!this.moveItemStackTo(slotStack, hotbarStart, hotbarEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex < hotbarEnd) {
                        if (!this.moveItemStackTo(slotStack, playerInvStart, playerInvEnd, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
            }

            if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == resultStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return resultStack;
    }

    // === Accessors for screen rendering ===

    public int getEnergy() {
        return this.data.get(DATA_ENERGY);
    }

    public int getMaxEnergy() {
        return this.data.get(DATA_MAX_ENERGY);
    }

    public int getProgress() {
        return this.data.get(DATA_PROGRESS);
    }

    public int getMaxProgress() {
        return this.data.get(DATA_MAX_PROGRESS);
    }

    /**
     * Returns the scaled energy bar height for rendering.
     * @param maxPixels the maximum height of the energy bar in pixels
     * @return a value between 0 and maxPixels
     */
    public int getScaledEnergy(int maxPixels) {
        int maxEnergy = getMaxEnergy();
        if (maxEnergy == 0) {
            return 0;
        }
        return Math.min(getEnergy() * maxPixels / maxEnergy, maxPixels);
    }

    /**
     * Returns the scaled progress arrow width for rendering.
     * @param maxPixels the maximum width of the progress arrow in pixels
     * @return a value between 0 and maxPixels
     */
    public int getScaledProgress(int maxPixels) {
        int maxProgress = getMaxProgress();
        if (maxProgress == 0) {
            return 0;
        }
        return Math.min(getProgress() * maxPixels / maxProgress, maxPixels);
    }

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }
}
