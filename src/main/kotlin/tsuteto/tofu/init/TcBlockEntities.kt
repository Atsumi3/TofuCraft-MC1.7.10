package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.blockentity.*

object TcBlockEntities {
    @JvmField
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TofuCraftMod.MOD_ID)

    @JvmField
    val SALT_FURNACE: DeferredHolder<BlockEntityType<*>, BlockEntityType<SaltFurnaceBlockEntity>> =
        BLOCK_ENTITIES.register("salt_furnace") {
            BlockEntityType.Builder.of(::SaltFurnaceBlockEntity,
                TcBlocks.SALT_FURNACE.get()).build(null)
        }

    @JvmField
    val MORIJIO: DeferredHolder<BlockEntityType<*>, BlockEntityType<MorijioBlockEntity>> =
        BLOCK_ENTITIES.register("morijio") {
            BlockEntityType.Builder.of(::MorijioBlockEntity,
                TcBlocks.MORIJIO.get()).build(null)
        }

    @JvmField
    val TF_STORAGE: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfStorageBlockEntity>> =
        BLOCK_ENTITIES.register("tf_storage") {
            BlockEntityType.Builder.of(::TfStorageBlockEntity,
                TcBlocks.TF_STORAGE.get()).build(null)
        }

    @JvmField
    val TF_CONDENSER: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfCondenserBlockEntity>> =
        BLOCK_ENTITIES.register("tf_condenser") {
            BlockEntityType.Builder.of(::TfCondenserBlockEntity,
                TcBlocks.TF_CONDENSER.get()).build(null)
        }

    @JvmField
    val TF_OVEN: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfOvenBlockEntity>> =
        BLOCK_ENTITIES.register("tf_oven") {
            BlockEntityType.Builder.of(::TfOvenBlockEntity,
                TcBlocks.TF_OVEN.get()).build(null)
        }

    @JvmField
    val TF_REFORMER: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfReformerBlockEntity>> =
        BLOCK_ENTITIES.register("tf_reformer") {
            BlockEntityType.Builder.of(::TfReformerBlockEntity,
                TcBlocks.TF_REFORMER.get()).build(null)
        }

    @JvmField
    val TF_SATURATOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfSaturatorBlockEntity>> =
        BLOCK_ENTITIES.register("tf_saturator") {
            BlockEntityType.Builder.of(::TfSaturatorBlockEntity,
                TcBlocks.TF_SATURATOR.get()).build(null)
        }

    @JvmField
    val TF_COLLECTOR: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfCollectorBlockEntity>> =
        BLOCK_ENTITIES.register("tf_collector") {
            BlockEntityType.Builder.of(::TfCollectorBlockEntity,
                TcBlocks.TF_COLLECTOR.get()).build(null)
        }

    @JvmField
    val TF_ANTENNA: DeferredHolder<BlockEntityType<*>, BlockEntityType<TfAntennaBlockEntity>> =
        BLOCK_ENTITIES.register("tf_antenna") {
            BlockEntityType.Builder.of(::TfAntennaBlockEntity,
                TcBlocks.TF_ANTENNA.get()).build(null)
        }

    @JvmField
    val BARREL: DeferredHolder<BlockEntityType<*>, BlockEntityType<BarrelBlockEntity>> =
        BLOCK_ENTITIES.register("barrel") {
            BlockEntityType.Builder.of(::BarrelBlockEntity,
                TcBlocks.BARREL_MISO.get(),
                TcBlocks.BARREL_MISO_TOFU.get(),
                TcBlocks.BARREL_GLOWTOFU.get(),
                TcBlocks.BARREL_ADV_TOFU_GEM.get()).build(null)
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        BLOCK_ENTITIES.register(modEventBus)
    }
}
