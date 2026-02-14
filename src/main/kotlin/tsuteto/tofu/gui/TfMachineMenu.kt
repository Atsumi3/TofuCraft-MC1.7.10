package tsuteto.tofu.gui

import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity

/**
 * Abstract base menu class for TofuFactory machine GUIs.
 * Provides common slot layout, ContainerData syncing for energy and progress,
 * and a standard quickMoveStack implementation.
 *
 * Subclasses should call [addMachineSlots] and [addPlayerInventory]
 * in their constructor, then [addDataSlots].
 *
 * ContainerData layout: 0=energy, 1=maxEnergy, 2=progress, 3=maxProgress
 */
abstract class TfMachineMenu(
    menuType: MenuType<*>?,
    containerId: Int,
    val blockEntity: BlockEntity,
    protected val data: ContainerData,
    /** The number of machine-specific slots (set by subclass) */
    protected var machineSlotCount: Int
) : AbstractContainerMenu(menuType, containerId) {

    companion object {
        /** ContainerData indices */
        const val DATA_ENERGY = 0
        const val DATA_MAX_ENERGY = 1
        const val DATA_PROGRESS = 2
        const val DATA_MAX_PROGRESS = 3
        const val DATA_COUNT = 4
    }

    init {
        checkContainerDataCount(data, DATA_COUNT)
    }

    /**
     * Subclasses implement this to add their machine-specific slots
     * (input, output, catalyst, etc.).
     */
    protected abstract fun addMachineSlots()

    /**
     * Adds the standard 27+9 player inventory slots at the default position.
     * Call this in your subclass constructor after addMachineSlots().
     */
    protected fun addPlayerInventory(playerInventory: Inventory) {
        addPlayerInventory(playerInventory, 8, 84)
    }

    /**
     * Adds the standard 27+9 player inventory slots at a custom position.
     */
    protected fun addPlayerInventory(playerInventory: Inventory, startX: Int, startY: Int) {
        // Main inventory (3 rows of 9)
        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(Slot(playerInventory, col + row * 9 + 9,
                    startX + col * 18, startY + row * 18))
            }
        }

        // Hotbar
        for (col in 0 until 9) {
            addSlot(Slot(playerInventory, col,
                startX + col * 18, startY + 58))
        }
    }

    override fun stillValid(player: Player): Boolean {
        return blockEntity.blockPos.closerToCenterThan(player.position(), 8.0)
    }

    override fun quickMoveStack(player: Player, slotIndex: Int): ItemStack {
        var resultStack = ItemStack.EMPTY
        val slot = slots[slotIndex]

        if (slot.hasItem()) {
            val slotStack = slot.item
            resultStack = slotStack.copy()

            val playerInvStart = machineSlotCount
            val playerInvEnd = playerInvStart + 27
            val hotbarStart = playerInvEnd
            val hotbarEnd = hotbarStart + 9

            if (slotIndex < machineSlotCount) {
                // Moving from machine slots to player inventory
                if (!moveItemStackTo(slotStack, playerInvStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickCraft(slotStack, resultStack)
            } else {
                // Moving from player inventory to machine input slots
                if (!moveItemStackTo(slotStack, 0, machineSlotCount, false)) {
                    // Move between inventory and hotbar
                    if (slotIndex < playerInvEnd) {
                        if (!moveItemStackTo(slotStack, hotbarStart, hotbarEnd, false)) {
                            return ItemStack.EMPTY
                        }
                    } else if (slotIndex < hotbarEnd) {
                        if (!moveItemStackTo(slotStack, playerInvStart, playerInvEnd, false)) {
                            return ItemStack.EMPTY
                        }
                    }
                }
            }

            if (slotStack.isEmpty) {
                slot.setByPlayer(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }

            if (slotStack.count == resultStack.count) {
                return ItemStack.EMPTY
            }

            slot.onTake(player, slotStack)
        }

        return resultStack
    }

    // === Accessors for screen rendering ===

    val energy: Int get() = data.get(DATA_ENERGY)

    val maxEnergy: Int get() = data.get(DATA_MAX_ENERGY)

    val progress: Int get() = data.get(DATA_PROGRESS)

    val maxProgress: Int get() = data.get(DATA_MAX_PROGRESS)

    /**
     * Returns the scaled energy bar height for rendering.
     * @param maxPixels the maximum height of the energy bar in pixels
     * @return a value between 0 and maxPixels
     */
    fun getScaledEnergy(maxPixels: Int): Int {
        val max = maxEnergy
        if (max == 0) return 0
        return minOf(energy * maxPixels / max, maxPixels)
    }

    /**
     * Returns the scaled progress arrow width for rendering.
     * @param maxPixels the maximum width of the progress arrow in pixels
     * @return a value between 0 and maxPixels
     */
    fun getScaledProgress(maxPixels: Int): Int {
        val max = maxProgress
        if (max == 0) return 0
        return minOf(progress * maxPixels / max, maxPixels)
    }
}
