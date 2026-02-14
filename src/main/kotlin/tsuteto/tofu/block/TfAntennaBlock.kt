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
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import tsuteto.tofu.blockentity.TfAntennaBlockEntity
import tsuteto.tofu.init.TcBlockEntities

/**
 * TofuFactory Antenna block.
 * Receives and transmits TF energy wirelessly. Non-full block with a custom shape
 * resembling an antenna pole with cross-arms.
 */
class TfAntennaBlock(properties: Properties) : BaseEntityBlock(properties) {

    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec(::TfAntennaBlock)

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return TfAntennaBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        if (level.isClientSide) {
            return null
        }
        return createTickerHelper(blockEntityType, TcBlockEntities.TF_ANTENNA.get(), TfAntennaBlockEntity::serverTick)
    }

    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    companion object {
        private val SHAPE: VoxelShape = Shapes.or(
            Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0),   // Central pole
            Block.box(2.0, 10.0, 7.0, 14.0, 12.0, 9.0),    // Cross arm X-axis
            Block.box(7.0, 10.0, 2.0, 9.0, 12.0, 14.0),    // Cross arm Z-axis
            Block.box(4.0, 14.0, 7.0, 12.0, 16.0, 9.0),    // Upper cross arm X-axis
            Block.box(7.0, 14.0, 4.0, 9.0, 16.0, 12.0)     // Upper cross arm Z-axis
        )
    }
}
