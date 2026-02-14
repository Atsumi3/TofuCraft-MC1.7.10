package tsuteto.tofu.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.blending.Blender;
import tsuteto.tofu.init.TcBlocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Custom chunk generator for the Tofu dimension.
 * Generates flat-ish terrain composed of tofu terrain blocks with gentle height variation.
 */
public class TofuChunkGenerator extends ChunkGenerator {

    public static final MapCodec<TofuChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(gen -> gen.biomeSource)
            ).apply(instance, TofuChunkGenerator::new)
    );

    /** Base height of the tofu terrain surface. */
    private static final int BASE_HEIGHT = 64;

    /** Maximum random variation in height above the base. */
    private static final int HEIGHT_VARIATION = 4;

    /** Bedrock layer thickness at the bottom of the world. */
    private static final int BEDROCK_LAYERS = 1;

    public TofuChunkGenerator(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion level, long seed, RandomState randomState,
                             BiomeManager biomeManager, StructureManager structureManager,
                             ChunkAccess chunk, GenerationStep.Carving step) {
        // No carvers in the Tofu dimension
    }

    @Override
    public void buildSurface(WorldGenRegion level, StructureManager structureManager,
                             RandomState randomState, ChunkAccess chunk) {
        // Surface is built during fillFromNoise; no additional surface decoration needed
    }

    @Override
    public void spawnOriginalMobs(WorldGenRegion level) {
        // Custom mob spawning handled by biome configuration
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public CompletableFuture<ChunkAccess> fillFromNoise(Blender blender, RandomState randomState,
                                                         StructureManager structureManager,
                                                         ChunkAccess chunk) {
        return CompletableFuture.supplyAsync(() -> {
            BlockState tofuTerrain = TcBlocks.TOFU_TERRAIN.get().defaultBlockState();
            BlockState tofuKinu = TcBlocks.TOFU_KINU.get().defaultBlockState();
            BlockState bedrock = Blocks.BEDROCK.defaultBlockState();

            int chunkX = chunk.getPos().x;
            int chunkZ = chunk.getPos().z;

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    int worldX = chunkX * 16 + x;
                    int worldZ = chunkZ * 16 + z;

                    // Generate gentle height variation using simple noise
                    int surfaceHeight = BASE_HEIGHT + getSurfaceHeight(worldX, worldZ);

                    for (int y = chunk.getMinBuildHeight(); y < chunk.getMaxBuildHeight(); y++) {
                        BlockPos pos = new BlockPos(x, y, z);

                        if (y == 0) {
                            // Bedrock floor
                            chunk.setBlockState(pos, bedrock, false);
                        } else if (y < BEDROCK_LAYERS) {
                            chunk.setBlockState(pos, bedrock, false);
                        } else if (y < surfaceHeight - 4) {
                            // Deep underground: kinu tofu (like stone layer)
                            chunk.setBlockState(pos, tofuKinu, false);
                        } else if (y < surfaceHeight) {
                            // Near surface: tofu terrain (like dirt layer)
                            chunk.setBlockState(pos, tofuTerrain, false);
                        } else if (y == surfaceHeight) {
                            // Surface layer
                            chunk.setBlockState(pos, tofuTerrain, false);
                        }
                        // Above surface: air (default)
                    }
                }
            }

            return chunk;
        });
    }

    /**
     * Calculates gentle surface height variation using a simple hash-based approach.
     * Produces smooth rolling hills in the tofu dimension.
     */
    private int getSurfaceHeight(int x, int z) {
        // Simple pseudo-noise for gentle terrain variation
        double noise1 = Math.sin(x * 0.02) * Math.cos(z * 0.02) * 2.0;
        double noise2 = Math.sin(x * 0.05 + 1.3) * Math.cos(z * 0.07 + 2.1) * 1.5;
        double noise3 = Math.sin(x * 0.1 + 5.7) * Math.cos(z * 0.1 + 3.2) * 0.5;
        return (int) Math.round(noise1 + noise2 + noise3);
    }

    @Override
    public int getSeaLevel() {
        return 0; // No sea level in tofu dimension
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getBaseHeight(int x, int z, Heightmap.Types type, LevelHeightAccessor level,
                             RandomState randomState) {
        return BASE_HEIGHT + getSurfaceHeight(x, z);
    }

    @Override
    public NoiseColumn getBaseColumn(int x, int z, LevelHeightAccessor level,
                                     RandomState randomState) {
        int surfaceHeight = BASE_HEIGHT + getSurfaceHeight(x, z);

        BlockState[] states = new BlockState[level.getHeight()];
        BlockState tofuTerrain = TcBlocks.TOFU_TERRAIN.get().defaultBlockState();
        BlockState tofuKinu = TcBlocks.TOFU_KINU.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();

        for (int y = 0; y < states.length; y++) {
            int worldY = y + level.getMinBuildHeight();
            if (worldY < BEDROCK_LAYERS) {
                states[y] = bedrock;
            } else if (worldY < surfaceHeight - 4) {
                states[y] = tofuKinu;
            } else if (worldY <= surfaceHeight) {
                states[y] = tofuTerrain;
            } else {
                states[y] = air;
            }
        }

        return new NoiseColumn(level.getMinBuildHeight(), states);
    }

    @Override
    public void addDebugScreenInfo(List<String> info, RandomState randomState, BlockPos pos) {
        info.add("TofuCraft Chunk Generator");
    }
}
