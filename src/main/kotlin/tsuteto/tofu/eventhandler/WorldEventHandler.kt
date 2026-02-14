package tsuteto.tofu.eventhandler

import net.minecraft.server.level.ServerLevel
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.level.LevelEvent
import tsuteto.tofu.TofuCraftMod

/**
 * Handles world/level lifecycle events for TofuCraft.
 * Performs initialization tasks when worlds are loaded, such as
 * registering custom world data or preparing dimension-specific features.
 */
class WorldEventHandler {

    @SubscribeEvent
    fun onWorldLoad(event: LevelEvent.Load) {
        val levelAccessor = event.level

        if (levelAccessor.isClientSide) return

        if (levelAccessor is ServerLevel) {
            onServerLevelLoad(levelAccessor)
        }
    }

    /**
     * Called when a server-side level finishes loading.
     * Initializes TofuCraft world data and dimension-specific features.
     */
    private fun onServerLevelLoad(serverLevel: ServerLevel) {
        TofuCraftMod.LOGGER.debug(
            "TofuCraft initializing for dimension: {}",
            serverLevel.dimension().location()
        )

        // Initialize TofuCraft saved data for this level if needed.
        // This is where custom SavedData or dimension-specific setup would occur.
        // For example, tracking salt pan evaporation rates or TF energy networks.
    }
}
