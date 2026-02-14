package tsuteto.tofu.entity;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import tsuteto.tofu.init.TcEntityTypes;
import tsuteto.tofu.init.TcItems;

import javax.annotation.Nullable;

/**
 * A custom arrow entity made from zunda (mashed edamame).
 * When it hits a living entity, it applies a brief slowness effect
 * in addition to normal arrow damage. The arrow can be picked up
 * as a Zunda Arrow item.
 */
public class ZundaArrowEntity extends AbstractArrow {

    /** Duration of the slowness effect in ticks (3 seconds) */
    private static final int SLOWNESS_DURATION = 60;

    /** Amplifier for the slowness effect (Slowness I) */
    private static final int SLOWNESS_AMPLIFIER = 0;

    public ZundaArrowEntity(EntityType<? extends ZundaArrowEntity> type, Level level) {
        super(type, level);
    }

    public ZundaArrowEntity(Level level, LivingEntity shooter, ItemStack pickupStack, @Nullable ItemStack weapon) {
        super(TcEntityTypes.ZUNDA_ARROW.get(), shooter, level, pickupStack, weapon);
    }

    public ZundaArrowEntity(Level level, double x, double y, double z,
                            ItemStack pickupStack, @Nullable ItemStack weapon) {
        super(TcEntityTypes.ZUNDA_ARROW.get(), x, y, z, level, pickupStack, weapon);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        // Apply slowness effect to the hit entity
        if (result.getEntity() instanceof LivingEntity livingTarget) {
            livingTarget.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    SLOWNESS_DURATION,
                    SLOWNESS_AMPLIFIER),
                    this.getEffectSource());
        }
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(TcItems.ZUNDA_ARROW.get());
    }
}
