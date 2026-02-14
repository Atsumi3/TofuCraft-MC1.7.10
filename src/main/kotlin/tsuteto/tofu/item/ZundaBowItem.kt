package tsuteto.tofu.item

import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.projectile.Arrow
import net.minecraft.world.item.BowItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import tsuteto.tofu.init.TcItems
import java.util.function.Predicate

/**
 * A special bow that exclusively uses Zunda Arrows as ammunition.
 * Features a custom charging animation and enhanced arrow velocity.
 */
class ZundaBowItem(properties: Properties) : BowItem(properties) {

    companion object {
        /** Maximum charge time in ticks (1 second) */
        private const val MAX_CHARGE_TICKS = 20

        /** Arrow velocity multiplier compared to vanilla bow */
        private const val VELOCITY_MULTIPLIER = 1.2f

        /**
         * Returns the charge power for a given charge time.
         * Uses a slightly faster charge curve than vanilla bow.
         */
        @JvmStatic
        fun getPowerForTime(charge: Int): Float {
            var f = charge.toFloat() / MAX_CHARGE_TICKS.toFloat()
            f = (f * f + f * 2.0f) / 3.0f
            if (f > 1.0f) {
                f = 1.0f
            }
            return f
        }
    }

    override fun getAllSupportedProjectiles(): Predicate<ItemStack> =
        Predicate { stack -> stack.`is`(TcItems.ZUNDA_ARROW.get()) }

    override fun getSupportedHeldProjectiles(): Predicate<ItemStack> = allSupportedProjectiles

    override fun releaseUsing(stack: ItemStack, level: Level, entity: LivingEntity, timeCharged: Int) {
        if (entity !is Player) return

        val chargeDuration = getUseDuration(stack, entity) - timeCharged
        val power = getPowerForTime(chargeDuration)
        if (power < 0.1f) return

        val ammo = entity.getProjectile(stack)
        if (ammo.isEmpty || !ammo.`is`(TcItems.ZUNDA_ARROW.get())) return

        if (level is ServerLevel) {
            val arrowStack = ammo.copy()
            arrowStack.count = 1

            val arrow = Arrow(level, entity, arrowStack, null)
            arrow.shootFromRotation(
                entity, entity.xRot, entity.yRot, 0.0f,
                power * 3.0f * VELOCITY_MULTIPLIER, 1.0f
            )

            if (power >= 1.0f) {
                arrow.isCritArrow = true
            }

            stack.hurtAndBreak(1, level, entity) {}

            level.addFreshEntity(arrow)

            if (!entity.abilities.instabuild) {
                ammo.shrink(1)
                if (ammo.isEmpty) {
                    entity.inventory.removeItem(ammo)
                }
            }
        }

        level.playSound(
            null, entity.x, entity.y, entity.z,
            SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS,
            1.0f, 1.0f / (level.getRandom().nextFloat() * 0.4f + 1.2f) + power * 0.5f
        )

        entity.awardStat(Stats.ITEM_USED.get(this))
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)
        val ammo = player.getProjectile(stack)

        return if (!ammo.isEmpty && ammo.`is`(TcItems.ZUNDA_ARROW.get())) {
            player.startUsingItem(hand)
            InteractionResultHolder.consume(stack)
        } else if (!player.abilities.instabuild) {
            InteractionResultHolder.fail(stack)
        } else {
            player.startUsingItem(hand)
            InteractionResultHolder.consume(stack)
        }
    }

    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.BOW

    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 72000
}
