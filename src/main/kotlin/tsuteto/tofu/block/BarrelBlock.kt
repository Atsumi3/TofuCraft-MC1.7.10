package tsuteto.tofu.block

import com.mojang.serializer.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.Containers
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import tsuteto.tofu.blockentity.BarrelBlockEntity
import tsuteto.tofu.init.TcBlockEntities

/**
 * Fermentation barrel block.
 * The fermentation stage property tracks how far along the fermentation process is.
 * The fermentation rate parameter determines how quickly the barrel processes its contents.
 */
class BarrelBlock(properties: Properties, val fermentationRate: Int) : BaseEntityBlock(properties) {

    init {
        registerDefaultState(stateDefinition.any().setValue(STAGE, 0))
    }

    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { p -> BarrelBlock(p, 3) }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        if (!level.isClientSide) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is BarrelBlockEntity) {
                // Report fermentation progress to the player
                val stage = state.getValue(STAGE)
                if (stage >= MAX_STAGE) {
                    // Fermentation complete: drop result
                    blockEntity.dropResults(level, pos)
                    level.setBlock(pos, state.setValue(STAGE, 0), 3)
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide)
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, movedByPiston: Boolean) {
        if (!state.`is`(newState.block)) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is BarrelBlockEntity) {
                if (level is ServerLevel) {
                    Containers.dropContents(level, pos, blockEntity)
                }
                level.updateNeighbourForOutputSignal(pos, this)
            }
            super.onRemove(state, level, pos, newState, movedByPiston)
        }
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return BarrelBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (level.isClientSide) {
            return null
        }
        return createTickerHelper(blockEntityType, TcBlockEntities.BARREL.get(), BarrelBlockEntity::serverTick)
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(STAGE)
    }

    companion object {
        const val MAX_STAGE = 4

        @JvmField
        val STAGE: IntegerProperty = IntegerProperty.create("stage", 0, MAX_STAGE)

        private val SHAPE: VoxelShape = Shapes.or(
            Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)
        )
    }
}
