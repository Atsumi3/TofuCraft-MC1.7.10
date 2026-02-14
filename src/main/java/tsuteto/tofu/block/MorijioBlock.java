package tsuteto.tofu.block;

import com.mojang.serializer.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import tsuteto.tofu.blockentity.MorijioBlockEntity;
import tsuteto.tofu.init.TcBlockEntities;

import javax.annotation.Nullable;

/**
 * Morijio (mound of salt) block used for fermentation effects.
 * Creates a MorijioBlockEntity that provides a fermentation-boosting aura
 * to nearby barrel blocks.
 */
public class MorijioBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0);

    public MorijioBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MorijioBlock::new);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MorijioBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }
        return createTickerHelper(blockEntityType, TcBlockEntities.MORIJIO.get(),
                MorijioBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
