package tsuteto.tofu.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import tsuteto.tofu.init.TcItems;

/**
 * Salt pan block for producing salt through water evaporation.
 * Status values:
 *   0 = empty
 *   1-3 = water evaporating
 *   4 = salt ready for collection
 *   8 = bittern (nigari) ready for collection
 */
public class SaltPanBlock extends Block {

    public static final IntegerProperty STATUS = IntegerProperty.create("status", 0, 8);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),   // Bottom
            Block.box(0.0, 2.0, 0.0, 16.0, 6.0, 2.0),     // North wall
            Block.box(0.0, 2.0, 14.0, 16.0, 6.0, 16.0),   // South wall
            Block.box(0.0, 2.0, 2.0, 2.0, 6.0, 14.0),     // West wall
            Block.box(14.0, 2.0, 2.0, 16.0, 6.0, 14.0)    // East wall
    );

    public SaltPanBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATUS, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                               Player player, InteractionHand hand, BlockHitResult hitResult) {
        int status = state.getValue(STATUS);

        // Fill with water bucket when empty
        if (status == 0 && stack.is(Items.WATER_BUCKET)) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(STATUS, 1), 3);
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        int status = state.getValue(STATUS);

        // Collect salt
        if (status == 4) {
            if (!level.isClientSide) {
                popResource(level, pos, new ItemStack(TcItems.SALT.get(), 1));
                level.setBlock(pos, state.setValue(STATUS, 8), 3);
                level.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Collect bittern (nigari)
        if (status == 8) {
            if (!level.isClientSide) {
                popResource(level, pos, new ItemStack(TcItems.NIGARI.get(), 1));
                level.setBlock(pos, state.setValue(STATUS, 0), 3);
                level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int status = state.getValue(STATUS);

        // Only advance evaporation while water is present (status 1-3)
        if (status >= 1 && status <= 3) {
            // Check if exposed to sky (sunlight accelerates evaporation)
            boolean canSeeSky = level.canSeeSky(pos.above());

            // Rain slows or stops evaporation
            if (level.isRaining() && canSeeSky) {
                // Rain fills it back to status 1 if partially evaporated
                if (status > 1) {
                    level.setBlock(pos, state.setValue(STATUS, 1), 3);
                }
                return;
            }

            // Check biome temperature for evaporation rate
            Biome.Precipitation precipitation = level.getBiome(pos).value().getPrecipitationAt(pos);
            boolean isHotBiome = level.getBiome(pos).value().getBaseTemperature() > 1.0f;

            int chance = 8; // Base chance (1 in 8 random ticks)
            if (canSeeSky && level.isDay()) {
                chance = 4; // Faster in sunlight during day
            }
            if (isHotBiome) {
                chance = Math.max(1, chance / 2); // Even faster in hot biomes
            }

            if (random.nextInt(chance) == 0) {
                if (status < 3) {
                    level.setBlock(pos, state.setValue(STATUS, status + 1), 3);
                } else {
                    // Evaporation complete: salt is ready
                    level.setBlock(pos, state.setValue(STATUS, 4), 3);
                }
            }
        }
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATUS);
    }
}
