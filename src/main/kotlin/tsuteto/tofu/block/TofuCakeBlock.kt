package tsuteto.tofu.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape

/**
 * Tofu cake block, similar to vanilla CakeBlock.
 * Has 7 bites (0-6), eating restores hunger.
 */
class TofuCakeBlock(properties: Properties) : Block(properties) {

    init {
        registerDefaultState(stateDefinition.any().setValue(BITES, 0))
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE_BY_BITE[state.getValue(BITES)]
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        if (player.canEat(false)) {
            return eat(level, pos, state, player)
        }
        return InteractionResult.PASS
    }

    private fun eat(level: Level, pos: BlockPos, state: BlockState, player: Player): InteractionResult {
        if (!level.isClientSide) {
            player.awardStat(Stats.EAT_CAKE_SLICE)
            // Restore 2 hunger points and 0.1 saturation per bite
            player.foodData.eat(2, 0.1f)
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f)
            level.gameEvent(player, GameEvent.EAT, pos)

            val bites = state.getValue(BITES)
            if (bites < MAX_BITES) {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3)
            } else {
                level.removeBlock(pos, false)
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos)
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun updateShape(
        state: BlockState, direction: Direction, neighborState: BlockState,
        level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos
    ): BlockState {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState()
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos)
    }

    override fun canSurvive(state: BlockState, level: LevelReader, pos: BlockPos): Boolean {
        return level.getBlockState(pos.below()).isSolid
    }

    override fun getAnalogOutputSignal(state: BlockState, level: Level, pos: BlockPos): Int {
        return getOutputSignal(state.getValue(BITES))
    }

    override fun hasAnalogOutputSignal(state: BlockState): Boolean = true

    override fun isPathfindable(state: BlockState, pathComputationType: PathComputationType): Boolean = false

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(BITES)
    }

    companion object {
        const val MAX_BITES = 6

        @JvmField
        val BITES: IntegerProperty = IntegerProperty.create("bites", 0, MAX_BITES)

        protected const val AABB_OFFSET = 1.0f
        protected const val AABB_SIZE_PER_BITE = 2.0f

        private val SHAPE_BY_BITE = arrayOf(
            Block.box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 0 bites (full)
            Block.box(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 1 bite
            Block.box(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 2 bites
            Block.box(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 3 bites
            Block.box(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 4 bites
            Block.box(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),  // 5 bites
            Block.box(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)   // 6 bites
        )

        @JvmStatic
        fun getOutputSignal(bites: Int): Int = (7 - bites) * 2
    }
}
