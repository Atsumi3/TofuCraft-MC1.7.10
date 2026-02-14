package tsuteto.tofu.item;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import tsuteto.tofu.TofuCraftMod;

/**
 * A scoop tool used to convert tofu blocks into their item form.
 * Right-clicking on any block tagged as a tofu block will break the block
 * and drop its corresponding item. The scoop has limited durability.
 */
public class TofuScoopItem extends Item {

    /** Block tag identifying all scoopable tofu blocks */
    public static final TagKey<Block> TOFU_BLOCKS_TAG =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_blocks"));

    public TofuScoopItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }

        if (!state.is(TOFU_BLOCKS_TAG)) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            // Get the block's drop as an item
            ItemStack drop = new ItemStack(state.getBlock().asItem());
            if (drop.isEmpty()) {
                return InteractionResult.PASS;
            }

            // Remove the block
            level.removeBlock(pos, false);

            // Spawn the item entity at the block position
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(level, x, y, z, drop);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);

            // Play scoop sound
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 0.8F);

            // Damage the scoop
            ItemStack scoopStack = context.getItemInHand();
            scoopStack.hurtAndBreak(1, (ServerLevel) level, player, item -> {});
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
