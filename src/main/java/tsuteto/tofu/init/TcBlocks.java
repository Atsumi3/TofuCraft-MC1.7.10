package tsuteto.tofu.init;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.block.*;

import java.util.function.Supplier;

public class TcBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TofuCraftMod.MOD_ID);

    // === Tofu Blocks ===
    public static final DeferredBlock<Block> TOFU_KINU = registerTofuBlock("tofu_kinu", 0.3f, 0.5f);
    public static final DeferredBlock<Block> TOFU_MOMEN = registerTofuBlock("tofu_momen", 0.5f, 1.0f);
    public static final DeferredBlock<Block> TOFU_ISHI = registerTofuBlock("tofu_ishi", 1.0f, 3.0f);
    public static final DeferredBlock<Block> TOFU_METAL = registerTofuBlock("tofu_metal", 3.0f, 10.0f);
    public static final DeferredBlock<Block> TOFU_GRILLED = registerTofuBlock("tofu_grilled", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_DRIED = registerTofuBlock("tofu_dried", 1.5f, 5.0f);
    public static final DeferredBlock<Block> TOFU_FRIED_POUCH = registerTofuBlock("tofu_fried_pouch", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_FRIED = registerTofuBlock("tofu_fried", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_EGG = registerTofuBlock("tofu_egg", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_ANNIN = registerTofuBlock("tofu_annin", 0.4f, 0.8f);
    public static final DeferredBlock<Block> TOFU_SESAME = registerTofuBlock("tofu_sesame", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_ZUNDA = registerTofuBlock("tofu_zunda", 0.6f, 1.0f);
    public static final DeferredBlock<Block> TOFU_STRAWBERRY = registerTofuBlock("tofu_strawberry", 0.4f, 0.8f);
    public static final DeferredBlock<Block> TOFU_MISO = registerTofuBlock("tofu_miso", 1.0f, 3.0f);
    public static final DeferredBlock<Block> TOFU_HELL = registerTofuBlock("tofu_hell", 1.0f, 5.0f);
    public static final DeferredBlock<Block> TOFU_GLOW = registerTofuBlockGlow("tofu_glow", 0.5f, 1.0f, 15);
    public static final DeferredBlock<Block> TOFU_DIAMOND = registerTofuBlock("tofu_diamond", 5.0f, 30.0f);
    public static final DeferredBlock<Block> TOFU_MINCED = registerTofuBlock("tofu_minced", 0.3f, 0.5f);

    // === Tofu Terrain ===
    public static final DeferredBlock<Block> TOFU_TERRAIN = BLOCKS.register("tofu_terrain",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(0.5f)
                    .sound(SoundType.GRAVEL)));

    public static final DeferredBlock<Block> TOFU_FARMLAND = BLOCKS.register("tofu_farmland",
            () -> new FarmBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(0.6f)
                    .sound(SoundType.GRAVEL)));

    // === Tofu Stairs ===
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_KINU = registerStairs("tofu_stairs_kinu", TOFU_KINU);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_MOMEN = registerStairs("tofu_stairs_momen", TOFU_MOMEN);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_ISHI = registerStairs("tofu_stairs_ishi", TOFU_ISHI);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_METAL = registerStairs("tofu_stairs_metal", TOFU_METAL);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_GRILLED = registerStairs("tofu_stairs_grilled", TOFU_GRILLED);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_DRIED = registerStairs("tofu_stairs_dried", TOFU_DRIED);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_FRIED_POUCH = registerStairs("tofu_stairs_fried_pouch", TOFU_FRIED_POUCH);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_FRIED = registerStairs("tofu_stairs_fried", TOFU_FRIED);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_EGG = registerStairs("tofu_stairs_egg", TOFU_EGG);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_ANNIN = registerStairs("tofu_stairs_annin", TOFU_ANNIN);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_SESAME = registerStairs("tofu_stairs_sesame", TOFU_SESAME);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_ZUNDA = registerStairs("tofu_stairs_zunda", TOFU_ZUNDA);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_STRAWBERRY = registerStairs("tofu_stairs_strawberry", TOFU_STRAWBERRY);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_HELL = registerStairs("tofu_stairs_hell", TOFU_HELL);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_GLOW = registerStairs("tofu_stairs_glow", TOFU_GLOW);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_DIAMOND = registerStairs("tofu_stairs_diamond", TOFU_DIAMOND);
    public static final DeferredBlock<StairBlock> TOFU_STAIRS_MISO = registerStairs("tofu_stairs_miso", TOFU_MISO);

    // === Tofu Slabs ===
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_KINU = registerSlab("tofu_slab_kinu", TOFU_KINU);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_MOMEN = registerSlab("tofu_slab_momen", TOFU_MOMEN);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_ISHI = registerSlab("tofu_slab_ishi", TOFU_ISHI);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_METAL = registerSlab("tofu_slab_metal", TOFU_METAL);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_GRILLED = registerSlab("tofu_slab_grilled", TOFU_GRILLED);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_DRIED = registerSlab("tofu_slab_dried", TOFU_DRIED);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_FRIED_POUCH = registerSlab("tofu_slab_fried_pouch", TOFU_FRIED_POUCH);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_FRIED = registerSlab("tofu_slab_fried", TOFU_FRIED);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_EGG = registerSlab("tofu_slab_egg", TOFU_EGG);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_ANNIN = registerSlab("tofu_slab_annin", TOFU_ANNIN);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_SESAME = registerSlab("tofu_slab_sesame", TOFU_SESAME);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_ZUNDA = registerSlab("tofu_slab_zunda", TOFU_ZUNDA);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_STRAWBERRY = registerSlab("tofu_slab_strawberry", TOFU_STRAWBERRY);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_HELL = registerSlab("tofu_slab_hell", TOFU_HELL);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_GLOW = registerSlab("tofu_slab_glow", TOFU_GLOW);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_DIAMOND = registerSlab("tofu_slab_diamond", TOFU_DIAMOND);
    public static final DeferredBlock<SlabBlock> TOFU_SLAB_MISO = registerSlab("tofu_slab_miso", TOFU_MISO);

    // === Tofu Walls ===
    public static final DeferredBlock<WallBlock> TOFU_WALL_KINU = registerWall("tofu_wall_kinu", TOFU_KINU);
    public static final DeferredBlock<WallBlock> TOFU_WALL_MOMEN = registerWall("tofu_wall_momen", TOFU_MOMEN);
    public static final DeferredBlock<WallBlock> TOFU_WALL_ISHI = registerWall("tofu_wall_ishi", TOFU_ISHI);
    public static final DeferredBlock<WallBlock> TOFU_WALL_METAL = registerWall("tofu_wall_metal", TOFU_METAL);
    public static final DeferredBlock<WallBlock> TOFU_WALL_GRILLED = registerWall("tofu_wall_grilled", TOFU_GRILLED);
    public static final DeferredBlock<WallBlock> TOFU_WALL_DRIED = registerWall("tofu_wall_dried", TOFU_DRIED);
    public static final DeferredBlock<WallBlock> TOFU_WALL_DIAMOND = registerWall("tofu_wall_diamond", TOFU_DIAMOND);

    // === Tofu Doors ===
    public static final DeferredBlock<DoorBlock> TOFU_DOOR_KINU = registerDoor("tofu_door_kinu", TOFU_KINU);
    public static final DeferredBlock<DoorBlock> TOFU_DOOR_MOMEN = registerDoor("tofu_door_momen", TOFU_MOMEN);
    public static final DeferredBlock<DoorBlock> TOFU_DOOR_ISHI = registerDoor("tofu_door_ishi", TOFU_ISHI);
    public static final DeferredBlock<DoorBlock> TOFU_DOOR_METAL = registerDoor("tofu_door_metal", TOFU_METAL);
    public static final DeferredBlock<DoorBlock> TOFU_DOOR_DIAMOND = registerDoor("tofu_door_diamond", TOFU_DIAMOND);

    // === Tofu Fence Gates ===
    public static final DeferredBlock<FenceGateBlock> TOFU_FENCE_GATE_KINU = registerFenceGate("tofu_fence_gate_kinu", TOFU_KINU);
    public static final DeferredBlock<FenceGateBlock> TOFU_FENCE_GATE_MOMEN = registerFenceGate("tofu_fence_gate_momen", TOFU_MOMEN);
    public static final DeferredBlock<FenceGateBlock> TOFU_FENCE_GATE_ISHI = registerFenceGate("tofu_fence_gate_ishi", TOFU_ISHI);
    public static final DeferredBlock<FenceGateBlock> TOFU_FENCE_GATE_METAL = registerFenceGate("tofu_fence_gate_metal", TOFU_METAL);
    public static final DeferredBlock<FenceGateBlock> TOFU_FENCE_GATE_DIAMOND = registerFenceGate("tofu_fence_gate_diamond", TOFU_DIAMOND);

    // === Tofu Trapdoors ===
    public static final DeferredBlock<TrapDoorBlock> TOFU_TRAPDOOR_KINU = registerTrapdoor("tofu_trapdoor_kinu", TOFU_KINU);
    public static final DeferredBlock<TrapDoorBlock> TOFU_TRAPDOOR_MOMEN = registerTrapdoor("tofu_trapdoor_momen", TOFU_MOMEN);
    public static final DeferredBlock<TrapDoorBlock> TOFU_TRAPDOOR_ISHI = registerTrapdoor("tofu_trapdoor_ishi", TOFU_ISHI);

    // === Crops & Plants ===
    public static final DeferredBlock<SoybeanBlock> SOYBEAN = BLOCKS.register("soybean",
            () -> new SoybeanBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<SoybeanHellBlock> SOYBEAN_HELL = BLOCKS.register("soybean_hell",
            () -> new SoybeanHellBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> LEEK = BLOCKS.register("leek",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.5f)
                    .sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> SPROUTS = BLOCKS.register("sprouts",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .pushReaction(PushReaction.DESTROY)));

    // === Ores ===
    public static final DeferredBlock<Block> ORE_TOFU = BLOCKS.register("ore_tofu",
            () -> new DropExperienceBlock(ConstantInt.of(0),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.QUARTZ)
                            .strength(1.5f, 5.0f)
                            .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> ORE_TOFU_DIAMOND = BLOCKS.register("ore_tofu_diamond",
            () -> new DropExperienceBlock(ConstantInt.of(0),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.QUARTZ)
                            .strength(3.0f, 10.0f)
                            .requiresCorrectToolForDrops()));

    // === Functional Blocks ===
    public static final DeferredBlock<SaltPanBlock> SALT_PAN = BLOCKS.register("salt_pan",
            () -> new SaltPanBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(0.5f)
                    .sound(SoundType.WOOD)
                    .randomTicks()
                    .noOcclusion()));

    public static final DeferredBlock<SaltFurnaceBlock> SALT_FURNACE = BLOCKS.register("salt_furnace",
            () -> new SaltFurnaceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(SaltFurnaceBlock.LIT) ? 13 : 0)));

    public static final DeferredBlock<Block> MORIJIO = BLOCKS.register("morijio",
            () -> new MorijioBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(0.5f)
                    .sound(SoundType.SAND)
                    .noOcclusion()));

    public static final DeferredBlock<Block> NATTO_BED = BLOCKS.register("natto_bed",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.5f)
                    .sound(SoundType.GRASS)));

    public static final DeferredBlock<Block> YUBA = BLOCKS.register("yuba",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .strength(0.3f)
                    .sound(SoundType.WOOL)
                    .noOcclusion()));

    public static final DeferredBlock<Block> NATTO = BLOCKS.register("natto",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.5f)
                    .sound(SoundType.SLIME_BLOCK)));

    public static final DeferredBlock<Block> SALT_BLOCK = BLOCKS.register("salt_block",
            () -> new FallingBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(0.5f)
                    .sound(SoundType.SAND)));

    public static final DeferredBlock<Block> ADV_TOFU_GEM = BLOCKS.register("adv_tofu_gem",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .strength(5.0f, 10.0f)
                    .lightLevel(state -> 10)
                    .sound(SoundType.METAL)));

    // === Barrels ===
    public static final DeferredBlock<Block> BARREL_MISO = BLOCKS.register("barrel_miso",
            () -> new BarrelBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(1.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion(), 3));

    public static final DeferredBlock<Block> BARREL_MISO_TOFU = BLOCKS.register("barrel_miso_tofu",
            () -> new BarrelBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(1.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion(), 2));

    public static final DeferredBlock<Block> BARREL_GLOWTOFU = BLOCKS.register("barrel_glowtofu",
            () -> new BarrelBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(1.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion(), 4));

    public static final DeferredBlock<Block> BARREL_ADV_TOFU_GEM = BLOCKS.register("barrel_adv_tofu_gem",
            () -> new BarrelBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(1.0f)
                    .sound(SoundType.WOOD)
                    .noOcclusion(), 4));

    // === Tofu Cake ===
    public static final DeferredBlock<Block> TOFU_CAKE = BLOCKS.register("tofu_cake",
            () -> new TofuCakeBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .strength(0.5f)
                    .sound(SoundType.WOOL)
                    .noOcclusion()));

    // === Portal ===
    public static final DeferredBlock<Block> TOFU_PORTAL = BLOCKS.register("tofu_portal",
            () -> new TofuPortalBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.QUARTZ)
                    .noCollission()
                    .strength(-1.0f)
                    .lightLevel(state -> 11)
                    .noLootTable()));

    // === Machines ===
    public static final DeferredBlock<TfMachineCaseBlock> TF_MACHINE_CASE = BLOCKS.register("tf_machine_case",
            () -> new TfMachineCaseBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<TfStorageBlock> TF_STORAGE = BLOCKS.register("tf_storage",
            () -> new TfStorageBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(TfStorageBlock.ACTIVE) ? 13 : 0)));

    public static final DeferredBlock<TfCondenserBlock> TF_CONDENSER = BLOCKS.register("tf_condenser",
            () -> new TfCondenserBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(TfCondenserBlock.ACTIVE) ? 13 : 0)));

    public static final DeferredBlock<TfOvenBlock> TF_OVEN = BLOCKS.register("tf_oven",
            () -> new TfOvenBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(TfOvenBlock.ACTIVE) ? 13 : 0)));

    public static final DeferredBlock<TfReformerBlock> TF_REFORMER = BLOCKS.register("tf_reformer",
            () -> new TfReformerBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(TfReformerBlock.ACTIVE) ? 13 : 0)));

    public static final DeferredBlock<TfSaturatorBlock> TF_SATURATOR = BLOCKS.register("tf_saturator",
            () -> new TfSaturatorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(TfSaturatorBlock.ACTIVE) ? 13 : 0)));

    public static final DeferredBlock<TfCollectorBlock> TF_COLLECTOR = BLOCKS.register("tf_collector",
            () -> new TfCollectorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<TfAntennaBlock> TF_ANTENNA = BLOCKS.register("tf_antenna",
            () -> new TfAntennaBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(3.5f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    // === Trees ===
    public static final DeferredBlock<Block> TC_LOG = BLOCKS.register("tc_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD)
                    .strength(2.0f)
                    .sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> TC_LEAVES = BLOCKS.register("tc_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .strength(0.2f)
                    .randomTicks()
                    .sound(SoundType.GRASS)
                    .noOcclusion()
                    .isSuffocating((s, g, p) -> false)
                    .isViewBlocking((s, g, p) -> false)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<Block> TC_SAPLING = BLOCKS.register("tc_sapling",
            () -> new SaplingBlock(null,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.PLANT)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .pushReaction(PushReaction.DESTROY)));

    // === Fluid Blocks ===
    public static final DeferredBlock<LiquidBlock> SOYMILK = BLOCKS.register("soymilk",
            () -> new LiquidBlock(TcFluids.SOYMILK_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.QUARTZ)
                            .noCollission()
                            .strength(100f)
                            .noLootTable()
                            .liquid()
                            .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<LiquidBlock> SOYMILK_HELL = BLOCKS.register("soymilk_hell",
            () -> new LiquidBlock(TcFluids.SOYMILK_HELL_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.NETHER)
                            .noCollission()
                            .strength(100f)
                            .noLootTable()
                            .liquid()
                            .pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<LiquidBlock> SOY_SAUCE = BLOCKS.register("soy_sauce",
            () -> new LiquidBlock(TcFluids.SOY_SAUCE_SOURCE.get(),
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .noCollission()
                            .strength(100f)
                            .noLootTable()
                            .liquid()
                            .pushReaction(PushReaction.DESTROY)));

    // === Chikuwa Platform ===
    public static final DeferredBlock<Block> CHIKUWA_PLATFORM = BLOCKS.register("chikuwa_platform",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .strength(0.5f)
                    .sound(SoundType.WOOL)
                    .noOcclusion()));

    // === Helper methods ===
    private static DeferredBlock<Block> registerTofuBlock(String name, float hardness, float resistance) {
        return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(hardness, resistance)
                .sound(SoundType.SNOW)));
    }

    private static DeferredBlock<Block> registerTofuBlockGlow(String name, float hardness, float resistance, int light) {
        return BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(hardness, resistance)
                .lightLevel(state -> light)
                .sound(SoundType.SNOW)));
    }

    private static DeferredBlock<StairBlock> registerStairs(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new StairBlock(base.get().defaultBlockState(),
                BlockBehaviour.Properties.ofFullCopy(base.get())));
    }

    private static DeferredBlock<SlabBlock> registerSlab(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(base.get())));
    }

    private static DeferredBlock<WallBlock> registerWall(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(base.get())));
    }

    private static DeferredBlock<DoorBlock> registerDoor(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new DoorBlock(
                BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get()).noOcclusion().pushReaction(PushReaction.DESTROY)));
    }

    private static DeferredBlock<FenceGateBlock> registerFenceGate(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new FenceGateBlock(
                WoodType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get())));
    }

    private static DeferredBlock<TrapDoorBlock> registerTrapdoor(String name, DeferredBlock<Block> base) {
        return BLOCKS.register(name, () -> new TrapDoorBlock(
                BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get()).noOcclusion()));
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
