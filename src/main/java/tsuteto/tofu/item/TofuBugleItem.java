package tsuteto.tofu.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import tsuteto.tofu.init.TcSoundEvents;

/**
 * A bugle instrument item that plays the tofu bugle sound when used.
 * The player holds the item for a short duration (like a horn/instrument),
 * and the sound plays when use begins.
 */
public class TofuBugleItem extends Item {

    /** Duration the player holds the bugle to their mouth, in ticks (2 seconds) */
    private static final int USE_DURATION = 40;

    /** Cooldown applied after playing the bugle, in ticks (3 seconds) */
    private static final int COOLDOWN_TICKS = 60;

    /** Sound audible range in blocks */
    private static final float SOUND_VOLUME = 3.0F;

    public TofuBugleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    TcSoundEvents.TOFU_BUGLE.get(), SoundSource.PLAYERS,
                    SOUND_VOLUME, 1.0F);
        }

        player.startUsingItem(hand);
        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return stack;
    }
}
