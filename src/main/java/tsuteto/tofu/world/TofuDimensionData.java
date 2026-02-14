package tsuteto.tofu.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import tsuteto.tofu.TofuCraftMod;

/**
 * Holds ResourceKey constants and helper methods for the Tofu dimension.
 */
public class TofuDimensionData {

    /** ResourceKey for the Tofu dimension Level. */
    public static final ResourceKey<Level> TOFU_DIMENSION_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_world"));

    /** ResourceKey for the Tofu dimension DimensionType. */
    public static final ResourceKey<DimensionType> TOFU_DIMENSION_TYPE_KEY =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_world"));

    /**
     * Teleports a player to the Tofu dimension or back to the Overworld.
     * If the player is already in the Tofu dimension, they will be sent back to the Overworld.
     * Otherwise, they will be sent to the Tofu dimension.
     *
     * @param player the server player to teleport
     */
    public static void teleportToTofuDimension(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        ResourceKey<Level> targetKey;
        if (player.level().dimension().equals(TOFU_DIMENSION_KEY)) {
            // Already in Tofu dimension, go back to Overworld
            targetKey = Level.OVERWORLD;
        } else {
            // Teleport to Tofu dimension
            targetKey = TOFU_DIMENSION_KEY;
        }

        ServerLevel targetLevel = server.getLevel(targetKey);
        if (targetLevel == null) {
            TofuCraftMod.LOGGER.warn("Tofu dimension level not found for key: {}", targetKey.location());
            return;
        }

        // Teleport the player to the target dimension at their current coordinates
        player.teleportTo(targetLevel, player.getX(), player.getY(), player.getZ(),
                player.getYRot(), player.getXRot());
    }

    /**
     * Checks whether a given level is the Tofu dimension.
     *
     * @param level the level to check
     * @return true if the level is the Tofu dimension
     */
    public static boolean isTofuDimension(Level level) {
        return level.dimension().equals(TOFU_DIMENSION_KEY);
    }
}
