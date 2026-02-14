package tsuteto.tofu.item

import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import tsuteto.tofu.init.TcEntityTypes
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.toDegrees

/**
 * A radar device that detects nearby Tofu Slime entities within a configurable range.
 * When used, it scans for Tofu Slimes and reports the distance and direction
 * to the nearest one via the action bar.
 */
class TofuRadarItem(properties: Properties) : Item(properties) {

    companion object {
        /** Maximum detection range in blocks */
        private const val SCAN_RANGE = 64.0

        /** Cooldown between scans in ticks (1 second) */
        private const val COOLDOWN_TICKS = 20

        /**
         * Determines the cardinal direction from the player to the target entity.
         *
         * @param player the player performing the scan
         * @param target the detected entity
         * @return a cardinal direction string (N, NE, E, SE, S, SW, W, NW)
         */
        private fun getCardinalDirection(player: Player, target: Entity): String {
            val dx = target.x - player.x
            val dz = target.z - player.z
            var angle = toDegrees(atan2(-dx, dz))
            if (angle < 0) {
                angle += 360.0
            }

            return when {
                angle >= 337.5 || angle < 22.5 -> "S"
                angle < 67.5 -> "SW"
                angle < 112.5 -> "W"
                angle < 157.5 -> "NW"
                angle < 202.5 -> "N"
                angle < 247.5 -> "NE"
                angle < 292.5 -> "E"
                else -> "SE"
            }
        }
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        val stack = player.getItemInHand(hand)

        if (!level.isClientSide) {
            val searchArea = player.boundingBox.inflate(SCAN_RANGE)
            val tofuSlimes = level.getEntities(
                TcEntityTypes.TOFU_SLIME.get(),
                searchArea
            ) { entity -> entity.isAlive }

            if (tofuSlimes.isEmpty()) {
                player.displayClientMessage(
                    Component.translatable("item.tofucraft.tofu_radar.not_found")
                        .withStyle(ChatFormatting.GRAY),
                    true
                )
                level.playSound(
                    null, player.x, player.y, player.z,
                    SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 0.5f, 0.5f
                )
            } else {
                var nearest: Entity? = null
                var nearestDist = Double.MAX_VALUE

                for (entity in tofuSlimes) {
                    val dist = player.distanceTo(entity).toDouble()
                    if (dist < nearestDist) {
                        nearestDist = dist
                        nearest = entity
                    }
                }

                val direction = getCardinalDirection(player, nearest!!)
                val distRounded = nearestDist.roundToInt()

                player.displayClientMessage(
                    Component.translatable("item.tofucraft.tofu_radar.found", distRounded, direction)
                        .withStyle(ChatFormatting.GREEN),
                    true
                )

                val pitch = 1.0f + (1.0 - minOf(nearestDist / SCAN_RANGE, 1.0)).toFloat()
                level.playSound(
                    null, player.x, player.y, player.z,
                    SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 1.0f, pitch
                )
            }
        }

        player.cooldowns.addCooldown(this, COOLDOWN_TICKS)
        player.awardStat(Stats.ITEM_USED.get(this))

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide)
    }
}
