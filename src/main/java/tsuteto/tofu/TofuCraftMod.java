package tsuteto.tofu;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsuteto.tofu.eventhandler.*;
import tsuteto.tofu.init.*;
import tsuteto.tofu.network.TcPackets;

@Mod(TofuCraftMod.MOD_ID)
public class TofuCraftMod {
    public static final String MOD_ID = "tofucraft";
    public static final Logger LOGGER = LoggerFactory.getLogger("TofuCraft");

    public TofuCraftMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register deferred registries
        TcArmorMaterials.register(modEventBus);
        TcBlocks.register(modEventBus);
        TcItems.register(modEventBus);
        TcBlockEntities.register(modEventBus);
        TcEntityTypes.register(modEventBus);
        TcFluids.register(modEventBus);
        TcMenuTypes.register(modEventBus);
        TcCreativeTabs.register(modEventBus);
        TcSoundEvents.register(modEventBus);
        TcRecipeTypes.register(modEventBus);
        TcBiomes.register(modEventBus);
        TcPackets.register(modEventBus);

        // Mod lifecycle events
        modEventBus.addListener(this::commonSetup);

        // Game events
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

        // Register event handlers
        NeoForge.EVENT_BUS.register(new BonemealEventHandler());
        NeoForge.EVENT_BUS.register(new EntityLivingEventHandler());
        NeoForge.EVENT_BUS.register(new PlayerInteractEventHandler());
        NeoForge.EVENT_BUS.register(new WorldEventHandler());

        // Config
        modContainer.registerConfig(ModConfig.Type.COMMON, TcConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("TofuCraft common setup");
    }

    private void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("TofuCraft server starting");
    }
}
