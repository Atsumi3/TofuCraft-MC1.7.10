package tsuteto.tofu.block

import com.mojang.serializer.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import tsuteto.tofu.blockentity.MorijioBlockEntity
import tsuteto.tofu.init.TcBlockEntities

/**
 * Morijio (mound of salt) block used for fermentation effects.
 * Creates a MorijioBlockEntity that provides a fermentation-boosting aura
 * to nearby barrel blocks.
 */
class MorijioBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec(::MorijioBlock)

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return MorijioBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (level.isClientSide) {
            return null
        }
        return createTickerHelper(blockEntityType, TcBlockEntities.MORIJIO.get(), MorijioBlockEntity::serverTick)
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    companion object {
        private val SHAPE: VoxelShape = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0)
    }
}
