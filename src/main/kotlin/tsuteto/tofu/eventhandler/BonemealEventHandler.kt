package tsuteto.tofu.eventhandler

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.level.BlockEvent
import tsuteto.tofu.block.SoybeanBlock
import tsuteto.tofu.block.SoybeanHellBlock
import tsuteto.tofu.init.TcBlocks

/**
 * Handles bonemeal application events for TofuCraft crops.
 * When bonemeal is applied to soybean crops, accelerates their growth
 * by advancing the age by 1-2 stages beyond what vanilla provides.
 */
class BonemealEventHandler {

    @SubscribeEvent
    fun onBonemeal(event: BlockEvent.CropGrowEvent.Pre) {
        if (event.level.isClientSide) return

        val level = event.level as Level
        val pos = event.pos
        val state = event.state

        // Handle soybean growth acceleration
        when {
            state.`is`(TcBlocks.SOYBEAN.get()) -> handleSoybeanGrowth(level, pos, state)
            state.`is`(TcBlocks.SOYBEAN_HELL.get()) -> handleSoybeanHellGrowth(level, pos, state)
        }
    }

    /**
     * Accelerates normal soybean crop growth when conditions are favorable.
     */
    private fun handleSoybeanGrowth(level: Level, pos: BlockPos, state: BlockState) {
        val currentAge = state.getValue(SoybeanBlock.AGE)
        if (currentAge < SoybeanBlock.MAX_AGE) {
            val newAge = minOf(currentAge + level.random.nextIntBetweenInclusive(1, 2), SoybeanBlock.MAX_AGE)
            level.setBlock(pos, state.setValue(SoybeanBlock.AGE, newAge), 2)
        }
    }

    /**
     * Accelerates hell soybean crop growth when conditions are favorable.
     */
    private fun handleSoybeanHellGrowth(level: Level, pos: BlockPos, state: BlockState) {
        val currentAge = state.getValue(SoybeanHellBlock.AGE)
        if (currentAge < SoybeanHellBlock.MAX_AGE) {
            val newAge = minOf(currentAge + level.random.nextIntBetweenInclusive(1, 2), SoybeanHellBlock.MAX_AGE)
            level.setBlock(pos, state.setValue(SoybeanHellBlock.AGE, newAge), 2)
        }
    }
}
