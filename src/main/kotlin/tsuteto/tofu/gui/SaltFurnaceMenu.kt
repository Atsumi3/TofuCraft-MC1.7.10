package tsuteto.tofu.gui

import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.neoforged.neoforge.items.SlotItemHandler
import tsuteto.tofu.blockentity.SaltFurnaceBlockEntity
import tsuteto.tofu.init.TcMenuTypes

/**
 * Container menu for the Salt Furnace block.
 * Manages 4 machine slots (fuel, salt output, bottle input, nigari output)
 * plus the standard player inventory and hotbar.
 */
class SaltFurnaceMenu : AbstractContainerMenu {

    /** The block entity backing this menu */
    val blockEntity: SaltFurnaceBlockEntity

    /** Synced data: 0=burnTime, 1=cookTime, 2=currentItemBurnTime */
    private val data: ContainerData

    companion object {
        /** Number of data slots synced via ContainerData */
        private const val DATA_COUNT = 3

        /** Slot indices */
        const val SLOT_FUEL = 0
        const val SLOT_SALT_OUTPUT = 1
        const val SLOT_BOTTLE_INPUT = 2
        const val SLOT_NIGARI_OUTPUT = 3

        private const val MACHINE_SLOT_COUNT = 4
        private const val PLAYER_INV_START = MACHINE_SLOT_COUNT
        private const val PLAYER_INV_END = PLAYER_INV_START + 27
        private const val HOTBAR_START = PLAYER_INV_END
        private const val HOTBAR_END = HOTBAR_START + 9

        private fun getBlockEntity(playerInventory: Inventory, buf: FriendlyByteBuf): SaltFurnaceBlockEntity {
            val pos: BlockPos = buf.readBlockPos()
            val be: BlockEntity? = playerInventory.player.level().getBlockEntity(pos)
            if (be is SaltFurnaceBlockEntity) {
                return be
            }
            throw IllegalStateException("Block entity at $pos is not a SaltFurnaceBlockEntity")
        }
    }

    /**
     * Server-side constructor. Called when the player opens the block GUI.
     */
    constructor(
        containerId: Int, playerInventory: Inventory,
        blockEntity: SaltFurnaceBlockEntity, data: ContainerData
    ) : super(TcMenuTypes.SALT_FURNACE.get(), containerId) {
        this.blockEntity = blockEntity
        this.data = data

        checkContainerDataCount(data, DATA_COUNT)

        val itemHandler = blockEntity.itemHandler

        // Machine slots
        addSlot(SlotItemHandler(itemHandler, SLOT_FUEL, 56, 53))           // Fuel
        addSlot(object : SlotItemHandler(itemHandler, SLOT_SALT_OUTPUT, 116, 35) {   // Salt output
            override fun mayPlace(stack: ItemStack): Boolean = false
        })
        addSlot(SlotItemHandler(itemHandler, SLOT_BOTTLE_INPUT, 56, 17))   // Bottle input
        addSlot(object : SlotItemHandler(itemHandler, SLOT_NIGARI_OUTPUT, 138, 35) { // Nigari output
            override fun mayPlace(stack: ItemStack): Boolean = false
        })

        // Player inventory (3 rows of 9)
        for (row in 0 until 3) {
            for (col in 0 until 9) {
                addSlot(Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18))
            }
        }

        // Player hotbar
        for (col in 0 until 9) {
            addSlot(Slot(playerInventory, col, 8 + col * 18, 142))
        }

        addDataSlots(data)
    }

    /**
     * Client-side factory constructor, called via network when the menu is opened remotely.
     */
    constructor(containerId: Int, playerInventory: Inventory, buf: FriendlyByteBuf) :
            this(containerId, playerInventory, getBlockEntity(playerInventory, buf), SimpleContainerData(DATA_COUNT))

    override fun stillValid(player: Player): Boolean = blockEntity.stillValid(player)

    override fun quickMoveStack(player: Player, slotIndex: Int): ItemStack {
        var resultStack = ItemStack.EMPTY
        val slot = slots[slotIndex]

        if (slot.hasItem()) {
            val slotStack = slot.item
            resultStack = slotStack.copy()

            if (slotIndex < MACHINE_SLOT_COUNT) {
                // Moving from machine slots to player inventory
                if (!moveItemStackTo(slotStack, PLAYER_INV_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY
                }
                slot.onQuickCraft(slotStack, resultStack)
            } else {
                // Moving from player inventory to machine slots
                // Try fuel slot first, then bottle input
                if (!moveItemStackTo(slotStack, SLOT_FUEL, SLOT_FUEL + 1, false)
                    && !moveItemStackTo(slotStack, SLOT_BOTTLE_INPUT, SLOT_BOTTLE_INPUT + 1, false)
                ) {
                    // Move between inventory and hotbar
                    if (slotIndex < PLAYER_INV_END) {
                        if (!moveItemStackTo(slotStack, HOTBAR_START, HOTBAR_END, false)) {
                            return ItemStack.EMPTY
                        }
                    } else if (slotIndex < HOTBAR_END) {
                        if (!moveItemStackTo(slotStack, PLAYER_INV_START, PLAYER_INV_END, false)) {
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

    val burnTime: Int get() = data.get(0)

    val cookTime: Int get() = data.get(1)

    val currentItemBurnTime: Int get() = data.get(2)

    val isBurning: Boolean get() = burnTime > 0

    /**
     * Returns the scaled burn progress for rendering the flame indicator.
     * @return a value between 0 and 13 (pixel height of flame sprite)
     */
    val scaledBurnTime: Int
        get() {
            val burn = burnTime
            var maxBurn = currentItemBurnTime
            if (maxBurn == 0) {
                maxBurn = 200
            }
            return burn * 13 / maxBurn
        }

    /**
     * Returns the scaled cook progress for rendering the arrow indicator.
     * @return a value between 0 and 24 (pixel width of arrow sprite)
     */
    val scaledCookProgress: Int
        get() {
            val cook = cookTime
            if (cook == 0) return 0
            return cook * 24 / 200
        }
}
