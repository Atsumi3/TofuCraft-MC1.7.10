package tsuteto.tofu.item

import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import tsuteto.tofu.init.TcSoundEvents

/**
 * A bugle instrument item that plays the tofu bugle sound when used.
 * The player holds the item for a short duration (like a horn/instrument),
 * and the sound plays when use begins.
 */
class TofuBugleItem(properties: Properties) : Item(properties) {

    companion object {
        /** Duration the player holds the bugle to their mouth, in ticks (2 seconds) */
        private const val USE_DURATION = 40

        /** Cooldown applied after playing the bugle, in ticks (3 seconds) */
        private const val COOLDOWN_TICKS = 60

        /** Sound audible range in blocks */
        private const val SOUND_VOLUME = 3.0f
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)

        if (!level.isClientSide) {
            level.playSound(
                null, player.x, player.y, player.z,
                TcSoundEvents.TOFU_BUGLE.get(), SoundSource.PLAYERS,
                SOUND_VOLUME, 1.0f
            )
        }

        player.startUsingItem(hand)
        player.cooldowns.addCooldown(this, COOLDOWN_TICKS)
        player.awardStat(Stats.ITEM_USED.get(this))

        return InteractionResultHolder.consume(stack)
    }

    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = USE_DURATION

    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.TOOT_HORN

    override fun finishUsingItem(stack: ItemStack, level: Level, entity: LivingEntity): ItemStack = stack
}
