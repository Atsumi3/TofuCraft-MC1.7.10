package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.entity.*

object TcEntityTypes {
    @JvmField
    val ENTITY_TYPES: DeferredRegister<EntityType<*>> =
        DeferredRegister.create(Registries.ENTITY_TYPE, TofuCraftMod.MOD_ID)

    @JvmField
    val TOFU_SLIME: DeferredHolder<EntityType<*>, EntityType<EntityTofuSlime>> =
        ENTITY_TYPES.register("tofu_slime") {
            EntityType.Builder.of(::EntityTofuSlime, MobCategory.MONSTER)
                .sized(2.04f, 2.04f)
                .clientTrackingRange(10)
                .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_slime").toString())
        }

    @JvmField
    val TOFU_CREEPER: DeferredHolder<EntityType<*>, EntityType<EntityTofuCreeper>> =
        ENTITY_TYPES.register("tofu_creeper") {
            EntityType.Builder.of(::EntityTofuCreeper, MobCategory.MONSTER)
                .sized(0.6f, 1.7f)
                .clientTrackingRange(8)
                .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofu_creeper").toString())
        }

    @JvmField
    val TOFUNIAN: DeferredHolder<EntityType<*>, EntityType<EntityTofunian>> =
        ENTITY_TYPES.register("tofunian") {
            EntityType.Builder.of(::EntityTofunian, MobCategory.CREATURE)
                .sized(0.6f, 1.95f)
                .clientTrackingRange(10)
                .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "tofunian").toString())
        }

    @JvmField
    val ZUNDA_ARROW: DeferredHolder<EntityType<*>, EntityType<ZundaArrowEntity>> =
        ENTITY_TYPES.register("zunda_arrow") {
            EntityType.Builder.of<ZundaArrowEntity>(::ZundaArrowEntity, MobCategory.MISC)
                .sized(0.5f, 0.5f)
                .clientTrackingRange(4)
                .updateInterval(20)
                .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "zunda_arrow").toString())
        }

    @JvmField
    val FUKUMAME: DeferredHolder<EntityType<*>, EntityType<FukumameEntity>> =
        ENTITY_TYPES.register("fukumame") {
            EntityType.Builder.of<FukumameEntity>(::FukumameEntity, MobCategory.MISC)
                .sized(0.25f, 0.25f)
                .clientTrackingRange(4)
                .updateInterval(10)
                .build(ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "fukumame").toString())
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        ENTITY_TYPES.register(modEventBus)
    }
}
