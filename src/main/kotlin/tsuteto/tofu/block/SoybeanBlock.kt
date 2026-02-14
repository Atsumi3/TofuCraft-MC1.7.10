package tsuteto.tofu.block

import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.level.storage.loot.LootParams
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import tsuteto.tofu.init.TcItems

class SoybeanBlock(properties: Properties) : CropBlock(properties) {

    override fun getAgeProperty(): IntegerProperty = AGE

    override fun getMaxAge(): Int = MAX_AGE

    override fun getBaseSeedId(): ItemLike = TcItems.SOYBEANS.get()

    override fun getShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext): VoxelShape {
        return SHAPE_BY_AGE[getAge(state)]
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun getDrops(state: BlockState, builder: LootParams.Builder): MutableList<ItemStack> {
        val drops = mutableListOf<ItemStack>()
        val age = getAge(state)

        when {
            age >= MAX_AGE -> {
                // Fully grown: drop soybeans
                drops.add(ItemStack(TcItems.SOYBEANS.get(), 1))
                // Bonus seed drops
                val random = builder.level.random
                if (random.nextInt(4) == 0) {
                    drops.add(ItemStack(TcItems.SOYBEANS.get(), 1))
                }
                if (random.nextInt(4) == 0) {
                    drops.add(ItemStack(TcItems.SOYBEANS.get(), 1))
                }
            }
            age >= 5 -> {
                // Stages 5-6: drop edamame
                drops.add(ItemStack(TcItems.EDAMAME.get(), 1))
                drops.add(ItemStack(TcItems.SOYBEANS.get(), 1))
            }
            else -> {
                // Not yet mature: drop seeds
                drops.add(ItemStack(TcItems.SOYBEANS.get(), 1))
            }
        }

        return drops
    }

    companion object {
        const val MAX_AGE = 7

        @JvmField
        val AGE: IntegerProperty = BlockStateProperties.AGE_7

        private val SHAPE_BY_AGE = arrayOf(
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        )
    }
}
