package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import tsuteto.tofu.TofuCraftMod;

public class TcBiomes {

    public static final ResourceKey<Biome> TOFU_PLAINS =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_plains"));

    public static final ResourceKey<Biome> TOFU_FOREST =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_forest"));

    public static final ResourceKey<Biome> TOFU_RIVER =
            ResourceKey.create(Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_river"));

    public static void register(IEventBus modEventBus) {
        // Biomes are registered via datapacks / JSON in modern NeoForge.
        // ResourceKeys are defined here for reference in code.
        // Biome JSON files should be placed under:
        //   data/tofucraft/worldgen/biome/
    }
}
