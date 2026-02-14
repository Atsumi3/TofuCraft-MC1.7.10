package tsuteto.tofu

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.server.ServerStartingEvent
import org.slf4j.LoggerFactory
import tsuteto.tofu.eventhandler.*
import tsuteto.tofu.init.*
import tsuteto.tofu.network.TcPackets

@Mod(TofuCraftMod.MOD_ID)
class TofuCraftMod(modEventBus: IEventBus, modContainer: ModContainer) {

    companion object {
        const val MOD_ID = "tofucraft"

        @JvmField
        val LOGGER = LoggerFactory.getLogger("TofuCraft")
    }

    init {
        // Register deferred registries
        TcArmorMaterials.register(modEventBus)
        TcBlocks.register(modEventBus)
        TcItems.register(modEventBus)
        TcBlockEntities.register(modEventBus)
        TcEntityTypes.register(modEventBus)
        TcFluids.register(modEventBus)
        TcMenuTypes.register(modEventBus)
        TcCreativeTabs.register(modEventBus)
        TcSoundEvents.register(modEventBus)
        TcRecipeTypes.register(modEventBus)
        TcBiomes.register(modEventBus)
        TcPackets.register(modEventBus)

        // Mod lifecycle events
        modEventBus.addListener(::commonSetup)

        // Game events
        NeoForge.EVENT_BUS.addListener(::onServerStarting)

        // Register event handlers
        NeoForge.EVENT_BUS.register(BonemealEventHandler())
        NeoForge.EVENT_BUS.register(EntityLivingEventHandler())
        NeoForge.EVENT_BUS.register(PlayerInteractEventHandler())
        NeoForge.EVENT_BUS.register(WorldEventHandler())

        // Config
        modContainer.registerConfig(ModConfig.Type.COMMON, TcConfig.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        LOGGER.info("TofuCraft common setup")
    }

    private fun onServerStarting(event: ServerStartingEvent) {
        LOGGER.info("TofuCraft server starting")
    }
}
