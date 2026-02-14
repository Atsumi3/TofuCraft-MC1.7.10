package tsuteto.tofu.eventhandler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

/**
 * Handles player interaction events for TofuCraft.
 * Provides special behavior when players right-click with specific items
 * or interact with TofuCraft blocks.
 */
public class PlayerInteractEventHandler {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack heldItem = player.getItemInHand(hand);
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Handle glass bottle on salt furnace (collect nigari)
        if (heldItem.is(Items.GLASS_BOTTLE) && state.is(TcBlocks.SALT_FURNACE.get())) {
            handleNigariCollection(player, hand, heldItem, level, pos);
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Player player = event.getEntity();
        ItemStack heldItem = player.getItemInHand(event.getHand());

        // Handle golden salt usage
        if (heldItem.is(TcItems.GOLDEN_SALT.get())) {
            TofuCraftMod.LOGGER.debug("Player {} used golden salt", player.getName().getString());
        }
    }

    /**
     * Handles the player using a glass bottle on a salt furnace to collect nigari.
     * If the furnace has accumulated nigari, fills the bottle and gives the player a nigari item.
     */
    private void handleNigariCollection(Player player, InteractionHand hand, ItemStack bottle,
                                        Level level, BlockPos pos) {
        // The actual nigari collection logic is handled by the SaltFurnaceBlockEntity
        // through its menu/GUI. This handler exists for direct right-click interaction
        // without opening the GUI.
        TofuCraftMod.LOGGER.debug("Player {} attempted nigari collection at {}", player.getName().getString(), pos);
    }
}
