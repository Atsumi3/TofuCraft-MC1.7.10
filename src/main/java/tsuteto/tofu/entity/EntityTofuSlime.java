package tsuteto.tofu.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import tsuteto.tofu.init.TcBiomes;
import tsuteto.tofu.init.TcEntityTypes;
import tsuteto.tofu.init.TcItems;

import javax.annotation.Nullable;

/**
 * A tofu-themed slime that spawns in Tofu biomes and in specific chunks
 * at y=15-40 in the overworld. Drops tofu items instead of slimeballs,
 * and uses snowball particles instead of slime particles.
 */
public class EntityTofuSlime extends Slime {

    public EntityTofuSlime(EntityType<? extends EntityTofuSlime> type, Level level) {
        super(type, level);
    }

    /**
     * Creates the attribute supplier for tofu slime entities.
     * Slightly weaker than normal slimes.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    @Override
    public void tick() {
        // Spawn snowball particles instead of slime particles
        if (this.level().isClientSide()) {
            int size = getSize();
            for (int i = 0; i < size * 2; i++) {
                float xOffset = (this.random.nextFloat() - 0.5F) * size * 0.5F;
                float yOffset = (this.random.nextFloat() - 0.5F) * size * 0.5F;
                float zOffset = (this.random.nextFloat() - 0.5F) * size * 0.5F;
                if (this.random.nextFloat() < 0.075F) {
                    this.level().addParticle(ParticleTypes.SNOWFLAKE,
                            this.getX() + xOffset,
                            this.getY() + 0.5D + yOffset,
                            this.getZ() + zOffset,
                            0.0D, 0.0D, 0.0D);
                }
            }
        }
        super.tick();
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevelAccessor level, DamageSource damageSource, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, damageSource, recentlyHit);

        int size = this.getSize();

        // Small slimes (size 1) drop tofu kinu food
        if (size == 1) {
            int dropCount = this.random.nextInt(3); // 0-2 items
            for (int i = 0; i < dropCount; i++) {
                ItemEntity itemEntity = this.spawnAtLocation(new ItemStack(TcItems.TOFU_KINU_FOOD.get()));
                if (itemEntity != null) {
                    itemEntity.setDefaultPickUpDelay();
                }
            }

            // Rare chance to drop a tofu stick
            if (this.random.nextFloat() < 0.15F) {
                ItemEntity stickEntity = this.spawnAtLocation(new ItemStack(TcItems.TOFU_STICK.get()));
                if (stickEntity != null) {
                    stickEntity.setDefaultPickUpDelay();
                }
            }
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return this.getSize() > 1 ? SoundEvents.SLIME_HURT : SoundEvents.SLIME_HURT_SMALL;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_DEATH : SoundEvents.SLIME_DEATH_SMALL;
    }

    @Override
    protected SoundEvent getSquishSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_SQUISH : SoundEvents.SLIME_SQUISH_SMALL;
    }

    @Override
    protected SoundEvent getJumpSound() {
        return this.getSize() > 1 ? SoundEvents.SLIME_JUMP : SoundEvents.SLIME_JUMP_SMALL;
    }

    /**
     * Checks whether a tofu slime can spawn at the given location.
     * Tofu slimes spawn in Tofu biomes at any height, or in specific
     * chunks at y=15-40 in the overworld (similar to vanilla slime chunk logic).
     */
    public static boolean checkTofuSlimeSpawnRules(EntityType<EntityTofuSlime> type, ServerLevelAccessor level,
                                                    MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // Always allow spawning in Tofu biomes
        var biomeHolder = level.getBiome(pos);
        if (biomeHolder.is(TcBiomes.TOFU_PLAINS)
                || biomeHolder.is(TcBiomes.TOFU_FOREST)
                || biomeHolder.is(TcBiomes.TOFU_RIVER)) {
            return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
        }

        // In the overworld, allow spawning at y=15-40 in specific chunks
        if (level.dimensionType().natural()) {
            if (pos.getY() >= 15 && pos.getY() <= 40) {
                // Use chunk coordinates to determine if this is a "tofu slime chunk"
                // Uses a fixed seed offset to differentiate from vanilla slime chunks
                long seed = level.getLevel().getSeed();
                int chunkX = pos.getX() >> 4;
                int chunkZ = pos.getZ() >> 4;
                long chunkSeed = seed +
                        (long) (chunkX * chunkX * 4987142) +
                        (long) (chunkX * 5947611) +
                        (long) (chunkZ * chunkZ) * 4392871L +
                        (long) (chunkZ * 389711) ^ 987234911L;
                RandomSource chunkRandom = RandomSource.create(chunkSeed);
                if (chunkRandom.nextInt(10) == 0) {
                    return Monster.checkMonsterSpawnRules(type, level, spawnType, pos, random);
                }
            }
        }

        return false;
    }

    @Nullable
    @Override
    public Slime createInstance(ServerLevelAccessor level, int size) {
        // Override to produce a new EntityTofuSlime when splitting
        EntityTofuSlime tofuSlime = TcEntityTypes.TOFU_SLIME.get().create(level.getLevel());
        return tofuSlime;
    }
}
