package tsuteto.tofu.eventhandler;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import tsuteto.tofu.block.SoybeanBlock;
import tsuteto.tofu.block.SoybeanHellBlock;
import tsuteto.tofu.init.TcBlocks;

/**
 * Handles bonemeal application events for TofuCraft crops.
 * When bonemeal is applied to soybean crops, accelerates their growth
 * by advancing the age by 1-2 stages beyond what vanilla provides.
 */
public class BonemealEventHandler {

    @SubscribeEvent
    public void onBonemeal(BlockEvent.CropGrowEvent.Pre event) {
        if (event.getLevel().isClientSide()) {
            return;
        }

        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        // Handle soybean growth acceleration
        if (state.is(TcBlocks.SOYBEAN.get())) {
            handleSoybeanGrowth(level, pos, state);
        } else if (state.is(TcBlocks.SOYBEAN_HELL.get())) {
            handleSoybeanHellGrowth(level, pos, state);
        }
    }

    /**
     * Accelerates normal soybean crop growth when conditions are favorable.
     */
    private void handleSoybeanGrowth(Level level, BlockPos pos, BlockState state) {
        int currentAge = state.getValue(SoybeanBlock.AGE);
        if (currentAge < SoybeanBlock.MAX_AGE) {
            int newAge = Math.min(currentAge + level.random.nextIntBetweenInclusive(1, 2), SoybeanBlock.MAX_AGE);
            level.setBlock(pos, state.setValue(SoybeanBlock.AGE, newAge), 2);
        }
    }

    /**
     * Accelerates hell soybean crop growth when conditions are favorable.
     */
    private void handleSoybeanHellGrowth(Level level, BlockPos pos, BlockState state) {
        int currentAge = state.getValue(SoybeanHellBlock.AGE);
        if (currentAge < SoybeanHellBlock.MAX_AGE) {
            int newAge = Math.min(currentAge + level.random.nextIntBetweenInclusive(1, 2), SoybeanHellBlock.MAX_AGE);
            level.setBlock(pos, state.setValue(SoybeanHellBlock.AGE, newAge), 2);
        }
    }
}
