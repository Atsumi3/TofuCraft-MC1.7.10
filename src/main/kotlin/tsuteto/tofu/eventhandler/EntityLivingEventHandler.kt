package tsuteto.tofu.eventhandler

import net.minecraft.world.entity.player.Player
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.init.TcEntityTypes

/**
 * Handles entity living events for TofuCraft.
 * Primarily listens for mob death events to trigger achievement/advancement
 * checks when the player kills TofuCraft-specific entities.
 */
class EntityLivingEventHandler {

    @SubscribeEvent
    fun onLivingDeath(event: LivingDeathEvent) {
        val source = event.source.entity
        if (source !is Player) return

        if (source.level().isClientSide) return

        val killed = event.entity

        // Track kills of TofuCraft mobs for advancement triggers
        when (killed.type) {
            TcEntityTypes.TOFU_SLIME.get() -> onTofuSlimeKilled(source)
            TcEntityTypes.TOFU_CREEPER.get() -> onTofuCreeperKilled(source)
        }
    }

    /**
     * Called when a player kills a Tofu Slime.
     * Triggers related advancement criteria.
     */
    private fun onTofuSlimeKilled(player: Player) {
        TofuCraftMod.LOGGER.debug("Player {} killed a Tofu Slime", player.name.string)
        // Advancement triggers are handled by the datapack advancement system.
        // Custom criteria triggers can be fired here if registered.
    }

    /**
     * Called when a player kills a Tofu Creeper.
     * Triggers related advancement criteria.
     */
    private fun onTofuCreeperKilled(player: Player) {
        TofuCraftMod.LOGGER.debug("Player {} killed a Tofu Creeper", player.name.string)
        // Advancement triggers are handled by the datapack advancement system.
        // Custom criteria triggers can be fired here if registered.
    }
}
