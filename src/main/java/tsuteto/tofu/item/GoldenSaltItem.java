package tsuteto.tofu.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * A rare golden salt item with 180 durability that grants powerful
 * beneficial effects when used. Each use consumes one durability point
 * and bestows the player with temporary buffs.
 */
public class GoldenSaltItem extends Item {

    /** Duration of applied effects in ticks (30 seconds) */
    private static final int EFFECT_DURATION = 600;

    /** Duration of the use animation in ticks */
    private static final int USE_DURATION = 32;

    public GoldenSaltItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player)) {
            return stack;
        }

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            // Apply beneficial effects
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, EFFECT_DURATION, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_DURATION, 0));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_DURATION, 1));

            // Consume durability
            stack.hurtAndBreak(1, serverLevel, player, item -> {});

            // Play activation sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);

            // Spawn golden particles around the player
            serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT,
                    player.getX(), player.getY() + 1.0, player.getZ(),
                    20, 0.5, 0.5, 0.5, 0.1);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        return stack;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
