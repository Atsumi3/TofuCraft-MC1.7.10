package tsuteto.tofu.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Tofu cake block, similar to vanilla CakeBlock.
 * Has 7 bites (0-6), eating restores hunger.
 */
public class TofuCakeBlock extends Block {

    public static final int MAX_BITES = 6;
    public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, MAX_BITES);

    protected static final float AABB_OFFSET = 1.0f;
    protected static final float AABB_SIZE_PER_BITE = 2.0f;

    private static final VoxelShape[] SHAPE_BY_BITE = new VoxelShape[]{
            Block.box(1.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 0 bites (full)
            Block.box(3.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 1 bite
            Block.box(5.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 2 bites
            Block.box(7.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 3 bites
            Block.box(9.0, 0.0, 1.0, 15.0, 8.0, 15.0),   // 4 bites
            Block.box(11.0, 0.0, 1.0, 15.0, 8.0, 15.0),  // 5 bites
            Block.box(13.0, 0.0, 1.0, 15.0, 8.0, 15.0)   // 6 bites
    };

    public TofuCakeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_BITE[state.getValue(BITES)];
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.canEat(false)) {
            return eat(level, pos, state, player);
        }
        return InteractionResult.PASS;
    }

    private InteractionResult eat(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            // Restore 2 hunger points and 0.1 saturation per bite
            player.getFoodData().eat(2, 0.1f);
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.5f, level.random.nextFloat() * 0.1f + 0.9f);
            level.gameEvent(player, GameEvent.EAT, pos);

            int bites = state.getValue(BITES);
            if (bites < MAX_BITES) {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                   LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return getOutputSignal(state.getValue(BITES));
    }

    public static int getOutputSignal(int bites) {
        return (7 - bites) * 2;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }
}
