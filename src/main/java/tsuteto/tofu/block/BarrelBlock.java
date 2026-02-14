package tsuteto.tofu.block;

import com.mojang.serializer.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tsuteto.tofu.blockentity.BarrelBlockEntity;
import tsuteto.tofu.init.TcBlockEntities;

import javax.annotation.Nullable;

/**
 * Fermentation barrel block.
 * The fermentation stage property tracks how far along the fermentation process is.
 * The fermentation rate parameter determines how quickly the barrel processes its contents.
 */
public class BarrelBlock extends BaseEntityBlock {

    public static final int MAX_STAGE = 4;
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, MAX_STAGE);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)
    );

    private final int fermentationRate;

    public BarrelBlock(Properties properties, int fermentationRate) {
        super(properties);
        this.fermentationRate = fermentationRate;
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(p -> new BarrelBlock(p, 3));
    }

    public int getFermentationRate() {
        return fermentationRate;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BarrelBlockEntity barrelBE) {
                // Report fermentation progress to the player
                int stage = state.getValue(STAGE);
                if (stage >= MAX_STAGE) {
                    // Fermentation complete: drop result
                    barrelBE.dropResults(level, pos);
                    level.setBlock(pos, state.setValue(STAGE, 0), 3);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BarrelBlockEntity barrelBE) {
                if (level instanceof ServerLevel serverLevel) {
                    Containers.dropContents(serverLevel, pos, barrelBE);
                }
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BarrelBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(blockEntityType, TcBlockEntities.BARREL.get(),
                BarrelBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STAGE);
    }
}
