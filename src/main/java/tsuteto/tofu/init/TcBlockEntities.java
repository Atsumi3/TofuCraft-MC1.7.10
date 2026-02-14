package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.blockentity.*;

public class TcBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SaltFurnaceBlockEntity>> SALT_FURNACE =
            BLOCK_ENTITIES.register("salt_furnace",
                    () -> BlockEntityType.Builder.of(SaltFurnaceBlockEntity::new,
                            TcBlocks.SALT_FURNACE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MorijioBlockEntity>> MORIJIO =
            BLOCK_ENTITIES.register("morijio",
                    () -> BlockEntityType.Builder.of(MorijioBlockEntity::new,
                            TcBlocks.MORIJIO.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfStorageBlockEntity>> TF_STORAGE =
            BLOCK_ENTITIES.register("tf_storage",
                    () -> BlockEntityType.Builder.of(TfStorageBlockEntity::new,
                            TcBlocks.TF_STORAGE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfCondenserBlockEntity>> TF_CONDENSER =
            BLOCK_ENTITIES.register("tf_condenser",
                    () -> BlockEntityType.Builder.of(TfCondenserBlockEntity::new,
                            TcBlocks.TF_CONDENSER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfOvenBlockEntity>> TF_OVEN =
            BLOCK_ENTITIES.register("tf_oven",
                    () -> BlockEntityType.Builder.of(TfOvenBlockEntity::new,
                            TcBlocks.TF_OVEN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfReformerBlockEntity>> TF_REFORMER =
            BLOCK_ENTITIES.register("tf_reformer",
                    () -> BlockEntityType.Builder.of(TfReformerBlockEntity::new,
                            TcBlocks.TF_REFORMER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfSaturatorBlockEntity>> TF_SATURATOR =
            BLOCK_ENTITIES.register("tf_saturator",
                    () -> BlockEntityType.Builder.of(TfSaturatorBlockEntity::new,
                            TcBlocks.TF_SATURATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfCollectorBlockEntity>> TF_COLLECTOR =
            BLOCK_ENTITIES.register("tf_collector",
                    () -> BlockEntityType.Builder.of(TfCollectorBlockEntity::new,
                            TcBlocks.TF_COLLECTOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TfAntennaBlockEntity>> TF_ANTENNA =
            BLOCK_ENTITIES.register("tf_antenna",
                    () -> BlockEntityType.Builder.of(TfAntennaBlockEntity::new,
                            TcBlocks.TF_ANTENNA.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BarrelBlockEntity>> BARREL =
            BLOCK_ENTITIES.register("barrel",
                    () -> BlockEntityType.Builder.of(BarrelBlockEntity::new,
                            TcBlocks.BARREL_MISO.get(),
                            TcBlocks.BARREL_MISO_TOFU.get(),
                            TcBlocks.BARREL_GLOWTOFU.get(),
                            TcBlocks.BARREL_ADV_TOFU_GEM.get()).build(null));

    public static void register(IEventBus modEventBus) {
        BLOCK_ENTITIES.register(modEventBus);
    }
}
