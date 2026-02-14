package tsuteto.tofu.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import tsuteto.tofu.init.TcItems;

/**
 * A tofu-themed creeper that is slightly weaker than a normal creeper.
 * On explosion, it scatters various tofu items around the area instead of
 * causing a normal explosion. The terrain is not destroyed.
 */
public class EntityTofuCreeper extends Creeper {

    /** Explosion power for the tofu creeper (weaker than normal creeper's 3.0) */
    private static final float TOFU_EXPLOSION_POWER = 2.0F;

    /** Number of tofu items scattered on explosion */
    private static final int MIN_SCATTER_ITEMS = 4;
    private static final int MAX_SCATTER_ITEMS = 10;

    public EntityTofuCreeper(EntityType<? extends EntityTofuCreeper> type, Level level) {
        super(type, level);
    }

    /**
     * Creates the attribute supplier for tofu creeper entities.
     * Slightly weaker than normal creepers with less health.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)  // Normal creeper has 20
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected void explodeCreeper() {
        if (!this.level().isClientSide()) {
            Level level = this.level();
            float explosionPower = this.isPowered() ? TOFU_EXPLOSION_POWER * 1.5F : TOFU_EXPLOSION_POWER;

            // Play explosion sound and cause knockback without terrain damage
            level.explode(this, this.getX(), this.getY(), this.getZ(),
                    explosionPower, Level.ExplosionInteraction.NONE);

            // Scatter tofu items
            scatterTofuItems(level);

            // Discard the entity after exploding
            this.discard();
        }
    }

    /**
     * Scatters various tofu items around the explosion area.
     */
    private void scatterTofuItems(Level level) {
        int itemCount = MIN_SCATTER_ITEMS + this.random.nextInt(MAX_SCATTER_ITEMS - MIN_SCATTER_ITEMS + 1);

        // Additional items if powered
        if (this.isPowered()) {
            itemCount += 4;
        }

        for (int i = 0; i < itemCount; i++) {
            ItemStack tofuItem = getRandomTofuDrop();

            double offsetX = (this.random.nextDouble() - 0.5D) * 4.0D;
            double offsetY = this.random.nextDouble() * 2.0D + 0.5D;
            double offsetZ = (this.random.nextDouble() - 0.5D) * 4.0D;

            ItemEntity itemEntity = new ItemEntity(level,
                    this.getX() + offsetX,
                    this.getY() + offsetY,
                    this.getZ() + offsetZ,
                    tofuItem);

            // Give the items some velocity to scatter them
            itemEntity.setDeltaMovement(
                    (this.random.nextDouble() - 0.5D) * 0.3D,
                    this.random.nextDouble() * 0.4D + 0.1D,
                    (this.random.nextDouble() - 0.5D) * 0.3D);
            itemEntity.setDefaultPickUpDelay();

            level.addFreshEntity(itemEntity);
        }
    }

    /**
     * Returns a random tofu item to scatter during the explosion.
     */
    private ItemStack getRandomTofuDrop() {
        int roll = this.random.nextInt(100);

        if (roll < 25) {
            // 25% - Kinu tofu food (1-3)
            return new ItemStack(TcItems.TOFU_KINU_FOOD.get(), 1 + this.random.nextInt(3));
        } else if (roll < 45) {
            // 20% - Momen tofu food (1-3)
            return new ItemStack(TcItems.TOFU_MOMEN_FOOD.get(), 1 + this.random.nextInt(3));
        } else if (roll < 60) {
            // 15% - Grilled tofu food (1-2)
            return new ItemStack(TcItems.TOFU_GRILLED_FOOD.get(), 1 + this.random.nextInt(2));
        } else if (roll < 72) {
            // 12% - Fried tofu food (1-2)
            return new ItemStack(TcItems.TOFU_FRIED_FOOD.get(), 1 + this.random.nextInt(2));
        } else if (roll < 82) {
            // 10% - Soybeans (1-4)
            return new ItemStack(TcItems.SOYBEANS.get(), 1 + this.random.nextInt(4));
        } else if (roll < 90) {
            // 8% - Tofu stick
            return new ItemStack(TcItems.TOFU_STICK.get(), 1);
        } else if (roll < 96) {
            // 6% - Nigari
            return new ItemStack(TcItems.NIGARI.get(), 1);
        } else {
            // 4% - Zunda
            return new ItemStack(TcItems.ZUNDA.get(), 1);
        }
    }

    @Override
    protected float getDeathKnockback() {
        // Slightly less knockback on death than normal creeper
        return 0.5F;
    }
}
