package tsuteto.tofu.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.Rotation
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.EnumProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import tsuteto.tofu.init.TcBlocks

/**
 * Tofu portal block used for transporting entities to the Tofu dimension.
 * Works similarly to the vanilla nether portal concept.
 */
class TofuPortalBlock(properties: Properties) : Block(properties) {

    init {
        registerDefaultState(stateDefinition.any().setValue(AXIS, Direction.Axis.X))
    }

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return when (state.getValue(AXIS)) {
            Direction.Axis.Z -> Z_AXIS_AABB
            else -> X_AXIS_AABB
        }
    }

    override fun updateShape(
        state: BlockState, direction: Direction, neighborState: BlockState,
        level: LevelAccessor, pos: BlockPos, neighborPos: BlockPos
    ): BlockState {
        val portalAxis = state.getValue(AXIS)
        // If the direction of the changed neighbor is along the portal axis or vertical,
        // check that the portal frame is still intact
        if (direction.axis == portalAxis
            || direction == Direction.UP
            || direction == Direction.DOWN
        ) {
            // Simplified check: if the neighbor in the portal plane is neither this portal
            // nor a valid frame block, break the portal
            val isPortal = neighborState.`is`(this)
            val isFrame = neighborState.`is`(TcBlocks.TOFU_DIAMOND.get())
            if (!isPortal && !isFrame) {
                return Blocks.AIR.defaultBlockState()
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos)
    }

    override fun entityInside(state: BlockState, level: Level, pos: BlockPos, entity: Entity) {
        if (!entity.isPassenger && !entity.isVehicle && entity.canChangeDimensions()) {
            if (entity.isOnPortalCooldown) {
                entity.setPortalCooldown()
            } else {
                if (!level.isClientSide && !entity.isOnPortalCooldown) {
                    entity.setPortalCooldown()
                    // Dimension transfer logic would be handled by a separate TofuPortalForcer/handler
                    // The actual teleportation is delegated to the dimension system
                    handlePortalTeleport(entity, level as ServerLevel, pos)
                }
            }
        }
    }

    private fun handlePortalTeleport(entity: Entity, level: ServerLevel, pos: BlockPos) {
        // Dimension transfer is handled externally by the portal handler system.
        // This method serves as the entry point for teleportation logic.
        // Implementation depends on TcDimensions registration.
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, random: RandomSource) {
        if (random.nextInt(100) == 0) {
            level.playLocalSound(
                pos.x + 0.5, pos.y + 0.5, pos.z + 0.5,
                SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS,
                0.5f, random.nextFloat() * 0.4f + 0.8f, false
            )
        }

        for (i in 0 until 4) {
            val x = pos.x + random.nextDouble()
            val y = pos.y + random.nextDouble()
            val z = pos.z + random.nextDouble()
            val xSpeed = (random.nextFloat() - 0.5) * 0.5
            val ySpeed = (random.nextFloat() - 0.5) * 0.5
            val zSpeed = (random.nextFloat() - 0.5) * 0.5

            level.addParticle(ParticleTypes.PORTAL, x, y, z, xSpeed.toDouble(), ySpeed.toDouble(), zSpeed.toDouble())
        }
    }

    override fun getCloneItemStack(level: LevelReader, pos: BlockPos, state: BlockState): ItemStack {
        return ItemStack.EMPTY
    }

    override fun rotate(state: BlockState, rotation: Rotation): BlockState {
        return when (rotation) {
            Rotation.COUNTERCLOCKWISE_90, Rotation.CLOCKWISE_90 -> {
                when (state.getValue(AXIS)) {
                    Direction.Axis.Z -> state.setValue(AXIS, Direction.Axis.X)
                    Direction.Axis.X -> state.setValue(AXIS, Direction.Axis.Z)
                    else -> state
                }
            }
            else -> state
        }
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AXIS)
    }

    companion object {
        @JvmField
        val AXIS: EnumProperty<Direction.Axis> = BlockStateProperties.HORIZONTAL_AXIS

        @JvmField
        val X_AXIS_AABB: VoxelShape = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0)

        @JvmField
        val Z_AXIS_AABB: VoxelShape = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0)
    }
}
