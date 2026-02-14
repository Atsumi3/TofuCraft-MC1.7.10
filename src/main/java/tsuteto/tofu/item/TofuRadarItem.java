package tsuteto.tofu.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import tsuteto.tofu.init.TcEntityTypes;

import java.util.List;

/**
 * A radar device that detects nearby Tofu Slime entities within a configurable range.
 * When used, it scans for Tofu Slimes and reports the distance and direction
 * to the nearest one via the action bar.
 */
public class TofuRadarItem extends Item {

    /** Maximum detection range in blocks */
    private static final double SCAN_RANGE = 64.0;

    /** Cooldown between scans in ticks (1 second) */
    private static final int COOLDOWN_TICKS = 20;

    public TofuRadarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            AABB searchArea = player.getBoundingBox().inflate(SCAN_RANGE);
            List<? extends Entity> tofuSlimes = level.getEntities(
                    TcEntityTypes.TOFU_SLIME.get(),
                    searchArea,
                    entity -> entity.isAlive()
            );

            if (tofuSlimes.isEmpty()) {
                player.displayClientMessage(
                        Component.translatable("item.tofucraft.tofu_radar.not_found")
                                .withStyle(ChatFormatting.GRAY),
                        true);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.NOTE_BLOCK_BASS.value(), SoundSource.PLAYERS, 0.5F, 0.5F);
            } else {
                Entity nearest = null;
                double nearestDist = Double.MAX_VALUE;

                for (Entity entity : tofuSlimes) {
                    double dist = player.distanceTo(entity);
                    if (dist < nearestDist) {
                        nearestDist = dist;
                        nearest = entity;
                    }
                }

                String direction = getCardinalDirection(player, nearest);
                int distRounded = (int) Math.round(nearestDist);

                player.displayClientMessage(
                        Component.translatable("item.tofucraft.tofu_radar.found",
                                        distRounded, direction)
                                .withStyle(ChatFormatting.GREEN),
                        true);

                float pitch = 1.0F + (float) (1.0 - Math.min(nearestDist / SCAN_RANGE, 1.0));
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.NOTE_BLOCK_CHIME.value(), SoundSource.PLAYERS, 1.0F, pitch);
            }
        }

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    /**
     * Determines the cardinal direction from the player to the target entity.
     *
     * @param player the player performing the scan
     * @param target the detected entity
     * @return a cardinal direction string (N, NE, E, SE, S, SW, W, NW)
     */
    private static String getCardinalDirection(Player player, Entity target) {
        double dx = target.getX() - player.getX();
        double dz = target.getZ() - player.getZ();
        double angle = Math.toDegrees(Math.atan2(-dx, dz));
        if (angle < 0) {
            angle += 360.0;
        }

        if (angle >= 337.5 || angle < 22.5) return "S";
        if (angle < 67.5) return "SW";
        if (angle < 112.5) return "W";
        if (angle < 157.5) return "NW";
        if (angle < 202.5) return "N";
        if (angle < 247.5) return "NE";
        if (angle < 292.5) return "E";
        return "SE";
    }
}
