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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import tsuteto.tofu.block.TfOvenBlock;
import tsuteto.tofu.init.TcBlockEntities;
import tsuteto.tofu.menu.TfOvenMenu;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * TF Oven block entity.
 * Cooks items using Tofu Force energy, functioning like a powered furnace.
 * Uses vanilla smelting recipes.
 *
 * Slot layout:
 *   0 = input
 *   1 = output
 */
public class TfOvenBlockEntity extends BlockEntity implements Container, MenuProvider {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT = 1;
    public static final int INVENTORY_SIZE = 2;

    public static final int MAX_TF_ENERGY = 10000;
    public static final int MAX_COOK_TIME = 100;
    public static final int ENERGY_PER_TICK = 3;

    private final ItemStackHandler inventory = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int tfEnergy;
    private int cookProgress;

    private Component customName;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> tfEnergy;
                case 1 -> cookProgress;
                case 2 -> MAX_COOK_TIME;
                case 3 -> MAX_TF_ENERGY;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> tfEnergy = value;
                case 1 -> cookProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    public TfOvenBlockEntity(BlockPos pos, BlockState state) {
        super(TcBlockEntities.TF_OVEN.get(), pos, state);
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }

    @Override
    public Component getDisplayName() {
        return customName != null ? customName : Component.translatable("container.tofucraft.tf_oven");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TfOvenMenu(containerId, playerInventory, this, this.dataAccess);
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

    public static void serverTick(Level level, BlockPos pos, BlockState state, TfOvenBlockEntity blockEntity) {
        boolean wasActive = state.getValue(TfOvenBlock.ACTIVE);
        boolean isProcessing = false;

        if (blockEntity.canSmelt(level)) {
            if (blockEntity.tfEnergy >= ENERGY_PER_TICK) {
                blockEntity.tfEnergy -= ENERGY_PER_TICK;
                blockEntity.cookProgress++;
                isProcessing = true;

                if (blockEntity.cookProgress >= MAX_COOK_TIME) {
                    blockEntity.smeltItem(level);
                    blockEntity.cookProgress = 0;
                }
                blockEntity.setChanged();
            } else {
                // Not enough energy, slowly lose progress
                if (blockEntity.cookProgress > 0) {
                    blockEntity.cookProgress = Math.max(0, blockEntity.cookProgress - 2);
                    blockEntity.setChanged();
                }
            }
        } else {
            if (blockEntity.cookProgress > 0) {
                blockEntity.cookProgress = 0;
                blockEntity.setChanged();
            }
        }

        // Update ACTIVE block state
        if (wasActive != isProcessing) {
            level.setBlock(pos, state.setValue(TfOvenBlock.ACTIVE, isProcessing), 3);
        }
    }

    private boolean canSmelt(Level level) {
        ItemStack input = inventory.getStackInSlot(SLOT_INPUT);
        if (input.isEmpty()) return false;

        Optional<RecipeHolder<SmeltingRecipe>> recipe = getSmeltingRecipe(level, input);
        if (recipe.isEmpty()) return false;

        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());
        if (result.isEmpty()) return false;

        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) return true;
        if (!ItemStack.isSameItemSameComponents(output, result)) return false;
        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void smeltItem(Level level) {
        ItemStack input = inventory.getStackInSlot(SLOT_INPUT);
        Optional<RecipeHolder<SmeltingRecipe>> recipe = getSmeltingRecipe(level, input);
        if (recipe.isEmpty()) return;

        ItemStack result = recipe.get().value().getResultItem(level.registryAccess());
        ItemStack output = inventory.getStackInSlot(SLOT_OUTPUT);

        if (output.isEmpty()) {
            inventory.setStackInSlot(SLOT_OUTPUT, result.copy());
        } else if (ItemStack.isSameItemSameComponents(output, result)) {
            output.grow(result.getCount());
        }

        input.shrink(1);
        setChanged();
    }

    private Optional<RecipeHolder<SmeltingRecipe>> getSmeltingRecipe(Level level, ItemStack input) {
        return level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(input), level);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("TfEnergy", tfEnergy);
        tag.putInt("CookProgress", cookProgress);
        tag.put("Inventory", inventory.serializeNBT(registries));
        if (customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(customName, registries));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tfEnergy = tag.getInt("TfEnergy");
        cookProgress = tag.getInt("CookProgress");
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
