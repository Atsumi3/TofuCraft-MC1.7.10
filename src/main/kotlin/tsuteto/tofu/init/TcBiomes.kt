package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.biome.Biome
import net.neoforged.bus.api.IEventBus
import tsuteto.tofu.TofuCraftMod

object TcBiomes {
    @JvmField
    val TOFU_PLAINS: ResourceKey<Biome> =
        ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_plains"))

    @JvmField
    val TOFU_FOREST: ResourceKey<Biome> =
        ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_forest"))

    @JvmField
    val TOFU_RIVER: ResourceKey<Biome> =
        ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_river"))

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        // Biomes are registered via datapacks / JSON in modern NeoForge.
        // ResourceKeys are defined here for reference in code.
        // Biome JSON files should be placed under:
        //   data/tofucraft/worldgen/biome/
    }
}
