package tsuteto.tofu.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import tsuteto.tofu.init.TcEntityTypes;
import tsuteto.tofu.init.TcItems;

/**
 * A throwable bean projectile inspired by the Japanese Setsubun tradition.
 * Fukumame (fortune beans) are thrown to drive away evil spirits.
 * On hit, they deal 1-2 hearts of damage (2-4 HP) to entities.
 * Undead entities take slightly more damage.
 */
public class FukumameEntity extends ThrowableItemProjectile {

    /** Base damage dealt to entities (in half-hearts) */
    private static final float BASE_DAMAGE = 2.0F;

    /** Maximum additional random damage */
    private static final float RANDOM_DAMAGE = 2.0F;

    /** Bonus damage multiplier against undead mobs */
    private static final float UNDEAD_BONUS_MULTIPLIER = 1.5F;

    public FukumameEntity(EntityType<? extends FukumameEntity> type, Level level) {
        super(type, level);
    }

    public FukumameEntity(Level level, LivingEntity shooter) {
        super(TcEntityTypes.FUKUMAME.get(), shooter, level);
    }

    public FukumameEntity(Level level, double x, double y, double z) {
        super(TcEntityTypes.FUKUMAME.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return TcItems.FUKUMAME.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (result.getEntity() instanceof LivingEntity target) {
            // Calculate damage: base 2.0 + random 0-2.0 = 2-4 HP (1-2 hearts)
            float damage = BASE_DAMAGE + this.random.nextFloat() * RANDOM_DAMAGE;

            // Bonus damage against undead mobs (zombies, skeletons, etc.)
            if (target.isInvertedHealAndHarm()) {
                damage *= UNDEAD_BONUS_MULTIPLIER;
            }

            target.hurt(this.damageSources().thrown(this, this.getOwner()), damage);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        // Discard the entity after hitting anything (block or entity)
        if (!this.level().isClientSide()) {
            this.discard();
        }
    }

    @Override
    protected float getGravity() {
        // Slightly higher gravity than snowballs for a more arc-like trajectory
        return 0.04F;
    }
}
