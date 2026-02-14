package tsuteto.tofu.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tsuteto.tofu.init.TcItems;

import java.util.ArrayList;
import java.util.List;

public class SoybeanHellBlock extends CropBlock {

    public static final int MAX_AGE = 7;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };

    public SoybeanHellBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return TcItems.SOYBEANS_HELL.get();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[getAge(state)];
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Hell soybeans can grow on soul sand instead of farmland.
     */
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL) || super.mayPlaceOn(state, level, pos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);
        // Nether soybeans do not require light to grow
        return mayPlaceOn(belowState, level, below);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Custom growth logic: does not require light level
        int age = getAge(state);
        if (age < getMaxAge()) {
            float growthSpeed = getGrowthSpeed(this, level, pos);
            if (random.nextInt((int) (25.0f / growthSpeed) + 1) == 0) {
                level.setBlock(pos, getStateForAge(age + 1), 2);
            }
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, net.minecraft.world.level.storage.loot.LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        int age = getAge(state);

        if (age >= MAX_AGE) {
            // Fully grown: drop hell soybeans
            drops.add(new ItemStack(TcItems.SOYBEANS_HELL.get(), 1));
            // Bonus seed drops
            RandomSource random = builder.getLevel().getRandom();
            if (random.nextInt(4) == 0) {
                drops.add(new ItemStack(TcItems.SOYBEANS_HELL.get(), 1));
            }
            if (random.nextInt(4) == 0) {
                drops.add(new ItemStack(TcItems.SOYBEANS_HELL.get(), 1));
            }
        } else if (age >= 5) {
            // Stages 5-6: drop edamame
            drops.add(new ItemStack(TcItems.EDAMAME.get(), 1));
            drops.add(new ItemStack(TcItems.SOYBEANS_HELL.get(), 1));
        } else {
            // Not yet mature: drop seeds
            drops.add(new ItemStack(TcItems.SOYBEANS_HELL.get(), 1));
        }

        return drops;
    }
}
