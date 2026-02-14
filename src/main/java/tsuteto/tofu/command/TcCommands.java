package tsuteto.tofu.command;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import tsuteto.tofu.util.ModLog;

/**
 * Event handler that registers all TofuCraft Brigadier commands
 * when the server command dispatcher is initialised.
 *
 * <p>Register an instance on the NeoForge event bus:</p>
 * <pre>
 *   NeoForge.EVENT_BUS.register(new TcCommands());
 * </pre>
 */
public class TcCommands {

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModLog.info("Registering TofuCraft commands");
        TofuSlimeCheckCommand.register(event.getDispatcher());
    }
}
