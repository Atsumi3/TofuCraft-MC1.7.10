package tsuteto.tofu.gui;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import tsuteto.tofu.blockentity.SaltFurnaceBlockEntity;
import tsuteto.tofu.init.TcMenuTypes;

/**
 * Container menu for the Salt Furnace block.
 * Manages 4 machine slots (fuel, salt output, bottle input, nigari output)
 * plus the standard player inventory and hotbar.
 */
public class SaltFurnaceMenu extends AbstractContainerMenu {

    /** The block entity backing this menu */
    private final SaltFurnaceBlockEntity blockEntity;

    /** Synced data: 0=burnTime, 1=cookTime, 2=currentItemBurnTime */
    private final ContainerData data;

    /** Number of data slots synced via ContainerData */
    private static final int DATA_COUNT = 3;

    /** Slot indices */
    public static final int SLOT_FUEL = 0;
    public static final int SLOT_SALT_OUTPUT = 1;
    public static final int SLOT_BOTTLE_INPUT = 2;
    public static final int SLOT_NIGARI_OUTPUT = 3;

    private static final int MACHINE_SLOT_COUNT = 4;
    private static final int PLAYER_INV_START = MACHINE_SLOT_COUNT;
    private static final int PLAYER_INV_END = PLAYER_INV_START + 27;
    private static final int HOTBAR_START = PLAYER_INV_END;
    private static final int HOTBAR_END = HOTBAR_START + 9;

    /**
     * Server-side constructor. Called when the player opens the block GUI.
     */
    public SaltFurnaceMenu(int containerId, Inventory playerInventory,
                           SaltFurnaceBlockEntity blockEntity, ContainerData data) {
        super(TcMenuTypes.SALT_FURNACE.get(), containerId);
        this.blockEntity = blockEntity;
        this.data = data;

        checkContainerDataCount(data, DATA_COUNT);

        IItemHandler itemHandler = blockEntity.getItemHandler();

        // Machine slots
        this.addSlot(new SlotItemHandler(itemHandler, SLOT_FUEL, 56, 53));           // Fuel
        this.addSlot(new SlotItemHandler(itemHandler, SLOT_SALT_OUTPUT, 116, 35) {   // Salt output
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });
        this.addSlot(new SlotItemHandler(itemHandler, SLOT_BOTTLE_INPUT, 56, 17));   // Bottle input
        this.addSlot(new SlotItemHandler(itemHandler, SLOT_NIGARI_OUTPUT, 138, 35) { // Nigari output
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Player inventory (3 rows of 9)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Player hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        this.addDataSlots(data);
    }

    /**
     * Client-side factory constructor, called via network when the menu is opened remotely.
     */
    public SaltFurnaceMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, getBlockEntity(playerInventory, buf), new SimpleContainerData(DATA_COUNT));
    }

    private static SaltFurnaceBlockEntity getBlockEntity(Inventory playerInventory, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInventory.player.level().getBlockEntity(pos);
        if (be instanceof SaltFurnaceBlockEntity saltFurnace) {
            return saltFurnace;
        }
        throw new IllegalStateException("Block entity at " + pos + " is not a SaltFurnaceBlockEntity");
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            resultStack = slotStack.copy();

            if (slotIndex < MACHINE_SLOT_COUNT) {
                // Moving from machine slots to player inventory
                if (!this.moveItemStackTo(slotStack, PLAYER_INV_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(slotStack, resultStack);
            } else {
                // Moving from player inventory to machine slots
                // Try fuel slot first, then bottle input
                if (!this.moveItemStackTo(slotStack, SLOT_FUEL, SLOT_FUEL + 1, false)
                        && !this.moveItemStackTo(slotStack, SLOT_BOTTLE_INPUT, SLOT_BOTTLE_INPUT + 1, false)) {
                    // Move between inventory and hotbar
                    if (slotIndex < PLAYER_INV_END) {
                        if (!this.moveItemStackTo(slotStack, HOTBAR_START, HOTBAR_END, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (slotIndex < HOTBAR_END) {
                        if (!this.moveItemStackTo(slotStack, PLAYER_INV_START, PLAYER_INV_END, false)) {
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

    public int getBurnTime() {
        return this.data.get(0);
    }

    public int getCookTime() {
        return this.data.get(1);
    }

    public int getCurrentItemBurnTime() {
        return this.data.get(2);
    }

    public boolean isBurning() {
        return getBurnTime() > 0;
    }

    /**
     * Returns the scaled burn progress for rendering the flame indicator.
     * @return a value between 0 and 13 (pixel height of flame sprite)
     */
    public int getScaledBurnTime() {
        int burnTime = getBurnTime();
        int maxBurnTime = getCurrentItemBurnTime();
        if (maxBurnTime == 0) {
            maxBurnTime = 200;
        }
        return burnTime * 13 / maxBurnTime;
    }

    /**
     * Returns the scaled cook progress for rendering the arrow indicator.
     * @return a value between 0 and 24 (pixel width of arrow sprite)
     */
    public int getScaledCookProgress() {
        int cookTime = getCookTime();
        if (cookTime == 0) {
            return 0;
        }
        return cookTime * 24 / 200;
    }

    public SaltFurnaceBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
