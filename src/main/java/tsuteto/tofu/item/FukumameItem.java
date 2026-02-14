package tsuteto.tofu.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tsuteto.tofu.entity.FukumameEntity;

/**
 * A throwable bean item inspired by the Japanese Setsubun tradition.
 * When used, the player throws a Fukumame (fortune bean) projectile
 * that deals damage to undead and other evil mobs.
 */
public class FukumameItem extends Item {

    public FukumameItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Play throw sound
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL,
                0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

        if (!level.isClientSide()) {
            FukumameEntity projectile = new FukumameEntity(level, player);
            projectile.setItem(stack);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(),
                    0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }

        // Consume one bean unless in creative
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
