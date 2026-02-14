package tsuteto.tofu.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import tsuteto.tofu.init.TcItems;

import java.util.function.Predicate;

/**
 * A special bow that exclusively uses Zunda Arrows as ammunition.
 * Features a custom charging animation and enhanced arrow velocity.
 */
public class ZundaBowItem extends BowItem {

    /** Maximum charge time in ticks (1 second) */
    private static final int MAX_CHARGE_TICKS = 20;

    /** Arrow velocity multiplier compared to vanilla bow */
    private static final float VELOCITY_MULTIPLIER = 1.2f;

    public ZundaBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> stack.is(TcItems.ZUNDA_ARROW.get());
    }

    @Override
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return getAllSupportedProjectiles();
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
        if (!(entity instanceof Player player)) {
            return;
        }

        int chargeDuration = this.getUseDuration(stack, entity) - timeCharged;
        float power = getPowerForTime(chargeDuration);
        if (power < 0.1F) {
            return;
        }

        ItemStack ammo = player.getProjectile(stack);
        if (ammo.isEmpty() || !ammo.is(TcItems.ZUNDA_ARROW.get())) {
            return;
        }

        if (level instanceof ServerLevel serverLevel) {
            ItemStack arrowStack = ammo.copy();
            arrowStack.setCount(1);

            Arrow arrow = new Arrow(level, player, arrowStack, null);
            arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F,
                    power * 3.0F * VELOCITY_MULTIPLIER, 1.0F);

            if (power >= 1.0F) {
                arrow.setCritArrow(true);
            }

            stack.hurtAndBreak(1, serverLevel, player, item -> {});

            level.addFreshEntity(arrow);

            if (!player.getAbilities().instabuild) {
                ammo.shrink(1);
                if (ammo.isEmpty()) {
                    player.getInventory().removeItem(ammo);
                }
            }
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
                1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack ammo = player.getProjectile(stack);

        if (!ammo.isEmpty() && ammo.is(TcItems.ZUNDA_ARROW.get())) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        } else if (!player.getAbilities().instabuild) {
            return InteractionResultHolder.fail(stack);
        } else {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    /**
     * Returns the charge power for a given charge time.
     * Uses a slightly faster charge curve than vanilla bow.
     */
    public static float getPowerForTime(int charge) {
        float f = (float) charge / (float) MAX_CHARGE_TICKS;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }
}
