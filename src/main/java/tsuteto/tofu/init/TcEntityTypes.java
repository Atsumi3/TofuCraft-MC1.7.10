package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.entity.*;

public class TcEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityTofuSlime>> TOFU_SLIME =
            ENTITY_TYPES.register("tofu_slime",
                    () -> EntityType.Builder.of(EntityTofuSlime::new, MobCategory.MONSTER)
                            .sized(2.04f, 2.04f)
                            .clientTrackingRange(10)
                            .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_slime").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityTofuCreeper>> TOFU_CREEPER =
            ENTITY_TYPES.register("tofu_creeper",
                    () -> EntityType.Builder.of(EntityTofuCreeper::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.7f)
                            .clientTrackingRange(8)
                            .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_creeper").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<EntityTofunian>> TOFUNIAN =
            ENTITY_TYPES.register("tofunian",
                    () -> EntityType.Builder.of(EntityTofunian::new, MobCategory.CREATURE)
                            .sized(0.6f, 1.95f)
                            .clientTrackingRange(10)
                            .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofunian").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ZundaArrowEntity>> ZUNDA_ARROW =
            ENTITY_TYPES.register("zunda_arrow",
                    () -> EntityType.Builder.<ZundaArrowEntity>of(ZundaArrowEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "zunda_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<FukumameEntity>> FUKUMAME =
            ENTITY_TYPES.register("fukumame",
                    () -> EntityType.Builder.<FukumameEntity>of(FukumameEntity::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "fukumame").toString()));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPES.register(modEventBus);
    }
}
