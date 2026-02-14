package tsuteto.tofu.eventhandler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import tsuteto.tofu.TofuCraftMod;

/**
 * Handles world/level lifecycle events for TofuCraft.
 * Performs initialization tasks when worlds are loaded, such as
 * registering custom world data or preparing dimension-specific features.
 */
public class WorldEventHandler {

    @SubscribeEvent
    public void onWorldLoad(LevelEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();

        if (levelAccessor.isClientSide()) {
            return;
        }

        if (levelAccessor instanceof ServerLevel serverLevel) {
            onServerLevelLoad(serverLevel);
        }
    }

    /**
     * Called when a server-side level finishes loading.
     * Initializes TofuCraft world data and dimension-specific features.
     */
    private void onServerLevelLoad(ServerLevel serverLevel) {
        TofuCraftMod.LOGGER.debug("TofuCraft initializing for dimension: {}",
                serverLevel.dimension().location());

        // Initialize TofuCraft saved data for this level if needed.
        // This is where custom SavedData or dimension-specific setup would occur.
        // For example, tracking salt pan evaporation rates or TF energy networks.
    }
}
