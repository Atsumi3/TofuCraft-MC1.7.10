package tsuteto.tofu.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import tsuteto.tofu.block.SaltFurnaceBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.init.TcFluids;
import tsuteto.tofu.init.TcItems;
import tsuteto.tofu.menu.SaltFurnaceMenu;

import javax.annotation.Nullable;

/**
 * Salt Furnace block entity.
 * Burns fuel to boil water from a cauldron placed above, producing salt and nigari.
 *
 * Slot layout:
 *   0 = fuel input
 *   1 = salt output
 *   2 = glass bottle input (for nigari bottling)
 *   3 = nigari bottle output
 */
public class SaltFurnaceBlockEntity extends BlockEntity implements Container, MenuProvider {

    public static final int SLOT_FUEL = 0;
    public static final int SLOT_SALT_OUTPUT = 1;
    public static final int SLOT_BOTTLE_INPUT = 2;
    public static final int SLOT_NIGARI_OUTPUT = 3;
    public static final int INVENTORY_SIZE = 4;

    public static final int MAX_COOK_TIME = 200;
    public static final int NIGARI_CAPACITY = 120;
    public static final int NIGARI_PER_COOK = 30;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final FluidTank nigariTank = new FluidTank(NIGARI_CAPACITY) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };

    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int furnaceCookTime;

    private Component customName;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> furnaceBurnTime;
                case 1 -> currentItemBurnTime;
                case 2 -> furnaceCookTime;
                case 3 -> nigariTank.getFluidAmount();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> furnaceBurnTime = value;
                case 1 -> currentItemBurnTime = value;
                case 2 -> furnaceCookTime = value;
                case 3 -> {
                    if (!nigariTank.isEmpty()) {
                        nigariTank.getFluid().setAmount(value);
                    }
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public SaltFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.SALT_FURNACE.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.salt_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SaltFurnaceMenu(containerId, playerInventory, this, this.dataAccess);
    }

    public boolean isBurning() {
        return furnaceBurnTime > 0;
    }

    public ItemStackHandler getInventoryHandler() {
        return inventory;
    }

    public FluidTank getNigariTank() {
        return nigariTank;
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SaltFurnaceBlockEntity blockEntity) {
        boolean wasBurning = blockEntity.isBurning();
        boolean changed = false;

        // Decrease burn time
        if (blockEntity.furnaceBurnTime > 0) {
            blockEntity.furnaceBurnTime--;
        }

        // Check for cauldron above
        boolean hasCauldronAbove = blockEntity.hasCauldronWithWater(level, pos);

        // Try to consume fuel if needed
        if (!blockEntity.isBurning() && hasCauldronAbove) {
            ItemStack fuelStack = blockEntity.inventory.getStackInSlot(SLOT_FUEL);
            if (!fuelStack.isEmpty()) {
                int burnTime = fuelStack.getBurnTime(RecipeType.SMELTING);
                if (burnTime > 0) {
                    blockEntity.furnaceBurnTime = burnTime;
                    blockEntity.currentItemBurnTime = burnTime;
                    fuelStack.shrink(1);
                    changed = true;
                }
            }
        }

        // Process cooking
        if (blockEntity.isBurning() && hasCauldronAbove) {
            blockEntity.furnaceCookTime++;
            if (blockEntity.furnaceCookTime >= MAX_COOK_TIME) {
                blockEntity.furnaceCookTime = 0;
                blockEntity.produceSaltAndNigari(level, pos);
                changed = true;
            }
        } else {
            if (blockEntity.furnaceCookTime > 0) {
                blockEntity.furnaceCookTime = 0;
                changed = true;
            }
        }

        // Try to fill nigari into bottles
        if (blockEntity.nigariTank.getFluidAmount() >= NIGARI_PER_COOK) {
            blockEntity.tryFillNigariBottle();
        }

        // Update LIT block state
        boolean isNowBurning = blockEntity.isBurning();
        if (wasBurning != isNowBurning) {
            level.setBlock(pos, state.setValue(SaltFurnaceBlock.LIT, isNowBurning), 3);
            changed = true;
        }

        if (changed) {
            blockEntity.setChanged();
        }
    }

    private boolean hasCauldronWithWater(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        return aboveState.is(Blocks.WATER_CAULDRON);
    }

    private void produceSaltAndNigari(Level level, BlockPos pos) {
        // Produce salt
        ItemStack saltOutput = inventory.getStackInSlot(SLOT_SALT_OUTPUT);
        ItemStack saltResult = new ItemStack(TcItems.SALT.get());
        if (saltOutput.isEmpty()) {
            inventory.setStackInSlot(SLOT_SALT_OUTPUT, saltResult);
        } else if (ItemStack.isSameItemSameComponents(saltOutput, saltResult)
                && saltOutput.getCount() < saltOutput.getMaxStackSize()) {
            saltOutput.grow(1);
        }

        // Produce nigari fluid
        FluidStack nigariFluid = new FluidStack(TcFluids.SOYMILK_SOURCE.get(), NIGARI_PER_COOK);
        nigariTank.fill(nigariFluid, IFluidHandler.FluidAction.EXECUTE);

        // Reduce water level in cauldron above
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.is(Blocks.WATER_CAULDRON)) {
            // Drain one level from the cauldron
            net.minecraft.world.level.block.LayeredCauldronBlock.lowerFillLevel(aboveState, level, above);
        }
    }

    private void tryFillNigariBottle() {
        ItemStack bottleInput = inventory.getStackInSlot(SLOT_BOTTLE_INPUT);
        ItemStack nigariOutput = inventory.getStackInSlot(SLOT_NIGARI_OUTPUT);

        if (!bottleInput.isEmpty() && bottleInput.is(Items.GLASS_BOTTLE)) {
            ItemStack nigariBottle = new ItemStack(TcItems.NIGARI.get());
            boolean canOutput;
            if (nigariOutput.isEmpty()) {
                canOutput = true;
            } else {
                canOutput = ItemStack.isSameItemSameComponents(nigariOutput, nigariBottle)
                        && nigariOutput.getCount() < nigariOutput.getMaxStackSize();
            }

            if (canOutput && nigariTank.getFluidAmount() >= NIGARI_PER_COOK) {
                nigariTank.drain(NIGARI_PER_COOK, IFluidHandler.FluidAction.EXECUTE);
                bottleInput.shrink(1);
                if (nigariOutput.isEmpty()) {
                    inventory.setStackInSlot(SLOT_NIGARI_OUTPUT, nigariBottle);
                } else {
                    nigariOutput.grow(1);
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("BurnTime", furnaceBurnTime);
        tag.putInt("CurrentItemBurnTime", currentItemBurnTime);
        tag.putInt("CookTime", furnaceCookTime);
        tag.put("Inventory", inventory.serializeNBT(registries));
        tag.put("NigariTank", nigariTank.writeToNBT(registries, new CompoundTag()));
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        furnaceBurnTime = tag.getInt("BurnTime");
        currentItemBurnTime = tag.getInt("CurrentItemBurnTime");
        furnaceCookTime = tag.getInt("CookTime");
        if (tag.contains("Inventory")) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
        if (tag.contains("NigariTank")) {
            nigariTank.readFromNBT(registries, tag.getCompound("NigariTank"));
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
