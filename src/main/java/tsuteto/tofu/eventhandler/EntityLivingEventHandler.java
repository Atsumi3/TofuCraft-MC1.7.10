package tsuteto.tofu.eventhandler;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.init.TcEntityTypes;

/**
 * Handles entity living events for TofuCraft.
 * Primarily listens for mob death events to trigger achievement/advancement
 * checks when the player kills TofuCraft-specific entities.
 */
public class EntityLivingEventHandler {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player player)) {
            return;
        }

        if (player.level().isClientSide()) {
            return;
        }

        Entity killed = event.getEntity();

        // Track kills of TofuCraft mobs for advancement triggers
        if (killed.getType() == TcEntityTypes.TOFU_SLIME.get()) {
            onTofuSlimeKilled(player);
        } else if (killed.getType() == TcEntityTypes.TOFU_CREEPER.get()) {
            onTofuCreeperKilled(player);
        }
    }

    /**
     * Called when a player kills a Tofu Slime.
     * Triggers related advancement criteria.
     */
    private void onTofuSlimeKilled(Player player) {
        TofuCraftMod.LOGGER.debug("Player {} killed a Tofu Slime", player.getName().getString());
        // Advancement triggers are handled by the datapack advancement system.
        // Custom criteria triggers can be fired here if registered.
    }

    /**
     * Called when a player kills a Tofu Creeper.
     * Triggers related advancement criteria.
     */
    private void onTofuCreeperKilled(Player player) {
        TofuCraftMod.LOGGER.debug("Player {} killed a Tofu Creeper", player.getName().getString());
        // Advancement triggers are handled by the datapack advancement system.
        // Custom criteria triggers can be fired here if registered.
    }
}
