package tsuteto.tofu.init

import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.MapColor
import net.minecraft.world.level.material.PushReaction
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.block.*

object TcBlocks {
    @JvmField
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(TofuCraftMod.MOD_ID)

    // === Tofu Blocks ===
    @JvmField val TOFU_KINU = registerTofuBlock("tofu_kinu", 0.3f, 0.5f)
    @JvmField val TOFU_MOMEN = registerTofuBlock("tofu_momen", 0.5f, 1.0f)
    @JvmField val TOFU_ISHI = registerTofuBlock("tofu_ishi", 1.0f, 3.0f)
    @JvmField val TOFU_METAL = registerTofuBlock("tofu_metal", 3.0f, 10.0f)
    @JvmField val TOFU_GRILLED = registerTofuBlock("tofu_grilled", 0.6f, 1.0f)
    @JvmField val TOFU_DRIED = registerTofuBlock("tofu_dried", 1.5f, 5.0f)
    @JvmField val TOFU_FRIED_POUCH = registerTofuBlock("tofu_fried_pouch", 0.6f, 1.0f)
    @JvmField val TOFU_FRIED = registerTofuBlock("tofu_fried", 0.6f, 1.0f)
    @JvmField val TOFU_EGG = registerTofuBlock("tofu_egg", 0.6f, 1.0f)
    @JvmField val TOFU_ANNIN = registerTofuBlock("tofu_annin", 0.4f, 0.8f)
    @JvmField val TOFU_SESAME = registerTofuBlock("tofu_sesame", 0.6f, 1.0f)
    @JvmField val TOFU_ZUNDA = registerTofuBlock("tofu_zunda", 0.6f, 1.0f)
    @JvmField val TOFU_STRAWBERRY = registerTofuBlock("tofu_strawberry", 0.4f, 0.8f)
    @JvmField val TOFU_MISO = registerTofuBlock("tofu_miso", 1.0f, 3.0f)
    @JvmField val TOFU_HELL = registerTofuBlock("tofu_hell", 1.0f, 5.0f)
    @JvmField val TOFU_GLOW = registerTofuBlockGlow("tofu_glow", 0.5f, 1.0f, 15)
    @JvmField val TOFU_DIAMOND = registerTofuBlock("tofu_diamond", 5.0f, 30.0f)
    @JvmField val TOFU_MINCED = registerTofuBlock("tofu_minced", 0.3f, 0.5f)

    // === Tofu Terrain ===
    @JvmField
    val TOFU_TERRAIN: DeferredBlock<Block> = BLOCKS.register("tofu_terrain") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .strength(0.5f)
            .sound(SoundType.GRAVEL))
    }

    @JvmField
    val TOFU_FARMLAND: DeferredBlock<Block> = BLOCKS.register("tofu_farmland") {
        FarmBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .strength(0.6f)
            .sound(SoundType.GRAVEL))
    }

    // === Tofu Stairs ===
    @JvmField val TOFU_STAIRS_KINU = registerStairs("tofu_stairs_kinu", TOFU_KINU)
    @JvmField val TOFU_STAIRS_MOMEN = registerStairs("tofu_stairs_momen", TOFU_MOMEN)
    @JvmField val TOFU_STAIRS_ISHI = registerStairs("tofu_stairs_ishi", TOFU_ISHI)
    @JvmField val TOFU_STAIRS_METAL = registerStairs("tofu_stairs_metal", TOFU_METAL)
    @JvmField val TOFU_STAIRS_GRILLED = registerStairs("tofu_stairs_grilled", TOFU_GRILLED)
    @JvmField val TOFU_STAIRS_DRIED = registerStairs("tofu_stairs_dried", TOFU_DRIED)
    @JvmField val TOFU_STAIRS_FRIED_POUCH = registerStairs("tofu_stairs_fried_pouch", TOFU_FRIED_POUCH)
    @JvmField val TOFU_STAIRS_FRIED = registerStairs("tofu_stairs_fried", TOFU_FRIED)
    @JvmField val TOFU_STAIRS_EGG = registerStairs("tofu_stairs_egg", TOFU_EGG)
    @JvmField val TOFU_STAIRS_ANNIN = registerStairs("tofu_stairs_annin", TOFU_ANNIN)
    @JvmField val TOFU_STAIRS_SESAME = registerStairs("tofu_stairs_sesame", TOFU_SESAME)
    @JvmField val TOFU_STAIRS_ZUNDA = registerStairs("tofu_stairs_zunda", TOFU_ZUNDA)
    @JvmField val TOFU_STAIRS_STRAWBERRY = registerStairs("tofu_stairs_strawberry", TOFU_STRAWBERRY)
    @JvmField val TOFU_STAIRS_HELL = registerStairs("tofu_stairs_hell", TOFU_HELL)
    @JvmField val TOFU_STAIRS_GLOW = registerStairs("tofu_stairs_glow", TOFU_GLOW)
    @JvmField val TOFU_STAIRS_DIAMOND = registerStairs("tofu_stairs_diamond", TOFU_DIAMOND)
    @JvmField val TOFU_STAIRS_MISO = registerStairs("tofu_stairs_miso", TOFU_MISO)

    // === Tofu Slabs ===
    @JvmField val TOFU_SLAB_KINU = registerSlab("tofu_slab_kinu", TOFU_KINU)
    @JvmField val TOFU_SLAB_MOMEN = registerSlab("tofu_slab_momen", TOFU_MOMEN)
    @JvmField val TOFU_SLAB_ISHI = registerSlab("tofu_slab_ishi", TOFU_ISHI)
    @JvmField val TOFU_SLAB_METAL = registerSlab("tofu_slab_metal", TOFU_METAL)
    @JvmField val TOFU_SLAB_GRILLED = registerSlab("tofu_slab_grilled", TOFU_GRILLED)
    @JvmField val TOFU_SLAB_DRIED = registerSlab("tofu_slab_dried", TOFU_DRIED)
    @JvmField val TOFU_SLAB_FRIED_POUCH = registerSlab("tofu_slab_fried_pouch", TOFU_FRIED_POUCH)
    @JvmField val TOFU_SLAB_FRIED = registerSlab("tofu_slab_fried", TOFU_FRIED)
    @JvmField val TOFU_SLAB_EGG = registerSlab("tofu_slab_egg", TOFU_EGG)
    @JvmField val TOFU_SLAB_ANNIN = registerSlab("tofu_slab_annin", TOFU_ANNIN)
    @JvmField val TOFU_SLAB_SESAME = registerSlab("tofu_slab_sesame", TOFU_SESAME)
    @JvmField val TOFU_SLAB_ZUNDA = registerSlab("tofu_slab_zunda", TOFU_ZUNDA)
    @JvmField val TOFU_SLAB_STRAWBERRY = registerSlab("tofu_slab_strawberry", TOFU_STRAWBERRY)
    @JvmField val TOFU_SLAB_HELL = registerSlab("tofu_slab_hell", TOFU_HELL)
    @JvmField val TOFU_SLAB_GLOW = registerSlab("tofu_slab_glow", TOFU_GLOW)
    @JvmField val TOFU_SLAB_DIAMOND = registerSlab("tofu_slab_diamond", TOFU_DIAMOND)
    @JvmField val TOFU_SLAB_MISO = registerSlab("tofu_slab_miso", TOFU_MISO)

    // === Tofu Walls ===
    @JvmField val TOFU_WALL_KINU = registerWall("tofu_wall_kinu", TOFU_KINU)
    @JvmField val TOFU_WALL_MOMEN = registerWall("tofu_wall_momen", TOFU_MOMEN)
    @JvmField val TOFU_WALL_ISHI = registerWall("tofu_wall_ishi", TOFU_ISHI)
    @JvmField val TOFU_WALL_METAL = registerWall("tofu_wall_metal", TOFU_METAL)
    @JvmField val TOFU_WALL_GRILLED = registerWall("tofu_wall_grilled", TOFU_GRILLED)
    @JvmField val TOFU_WALL_DRIED = registerWall("tofu_wall_dried", TOFU_DRIED)
    @JvmField val TOFU_WALL_DIAMOND = registerWall("tofu_wall_diamond", TOFU_DIAMOND)

    // === Tofu Doors ===
    @JvmField val TOFU_DOOR_KINU = registerDoor("tofu_door_kinu", TOFU_KINU)
    @JvmField val TOFU_DOOR_MOMEN = registerDoor("tofu_door_momen", TOFU_MOMEN)
    @JvmField val TOFU_DOOR_ISHI = registerDoor("tofu_door_ishi", TOFU_ISHI)
    @JvmField val TOFU_DOOR_METAL = registerDoor("tofu_door_metal", TOFU_METAL)
    @JvmField val TOFU_DOOR_DIAMOND = registerDoor("tofu_door_diamond", TOFU_DIAMOND)

    // === Tofu Fence Gates ===
    @JvmField val TOFU_FENCE_GATE_KINU = registerFenceGate("tofu_fence_gate_kinu", TOFU_KINU)
    @JvmField val TOFU_FENCE_GATE_MOMEN = registerFenceGate("tofu_fence_gate_momen", TOFU_MOMEN)
    @JvmField val TOFU_FENCE_GATE_ISHI = registerFenceGate("tofu_fence_gate_ishi", TOFU_ISHI)
    @JvmField val TOFU_FENCE_GATE_METAL = registerFenceGate("tofu_fence_gate_metal", TOFU_METAL)
    @JvmField val TOFU_FENCE_GATE_DIAMOND = registerFenceGate("tofu_fence_gate_diamond", TOFU_DIAMOND)

    // === Tofu Trapdoors ===
    @JvmField val TOFU_TRAPDOOR_KINU = registerTrapdoor("tofu_trapdoor_kinu", TOFU_KINU)
    @JvmField val TOFU_TRAPDOOR_MOMEN = registerTrapdoor("tofu_trapdoor_momen", TOFU_MOMEN)
    @JvmField val TOFU_TRAPDOOR_ISHI = registerTrapdoor("tofu_trapdoor_ishi", TOFU_ISHI)

    // === Crops & Plants ===
    @JvmField
    val SOYBEAN: DeferredBlock<SoybeanBlock> = BLOCKS.register("soybean") {
        SoybeanBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY))
    }

    @JvmField
    val SOYBEAN_HELL: DeferredBlock<SoybeanHellBlock> = BLOCKS.register("soybean_hell") {
        SoybeanHellBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .randomTicks()
            .instabreak()
            .sound(SoundType.CROP)
            .pushReaction(PushReaction.DESTROY))
    }

    @JvmField
    val LEEK: DeferredBlock<Block> = BLOCKS.register("leek") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.5f)
            .sound(SoundType.GRASS))
    }

    @JvmField
    val SPROUTS: DeferredBlock<Block> = BLOCKS.register("sprouts") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .pushReaction(PushReaction.DESTROY))
    }

    // === Ores ===
    @JvmField
    val ORE_TOFU: DeferredBlock<Block> = BLOCKS.register("ore_tofu") {
        DropExperienceBlock(ConstantInt.of(0),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(1.5f, 5.0f)
                .requiresCorrectToolForDrops())
    }

    @JvmField
    val ORE_TOFU_DIAMOND: DeferredBlock<Block> = BLOCKS.register("ore_tofu_diamond") {
        DropExperienceBlock(ConstantInt.of(0),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(3.0f, 10.0f)
                .requiresCorrectToolForDrops())
    }

    // === Functional Blocks ===
    @JvmField
    val SALT_PAN: DeferredBlock<SaltPanBlock> = BLOCKS.register("salt_pan") {
        SaltPanBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(0.5f)
            .sound(SoundType.WOOD)
            .randomTicks()
            .noOcclusion())
    }

    @JvmField
    val SALT_FURNACE: DeferredBlock<SaltFurnaceBlock> = BLOCKS.register("salt_furnace") {
        SaltFurnaceBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(SaltFurnaceBlock.LIT)) 13 else 0 })
    }

    @JvmField
    val MORIJIO: DeferredBlock<Block> = BLOCKS.register("morijio") {
        MorijioBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .strength(0.5f)
            .sound(SoundType.SAND)
            .noOcclusion())
    }

    @JvmField
    val NATTO_BED: DeferredBlock<Block> = BLOCKS.register("natto_bed") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .strength(0.5f)
            .sound(SoundType.GRASS))
    }

    @JvmField
    val YUBA: DeferredBlock<Block> = BLOCKS.register("yuba") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.SAND)
            .strength(0.3f)
            .sound(SoundType.WOOL)
            .noOcclusion())
    }

    @JvmField
    val NATTO: DeferredBlock<Block> = BLOCKS.register("natto") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .strength(0.5f)
            .sound(SoundType.SLIME_BLOCK))
    }

    @JvmField
    val SALT_BLOCK: DeferredBlock<Block> = BLOCKS.register("salt_block") {
        FallingBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .strength(0.5f)
            .sound(SoundType.SAND))
    }

    @JvmField
    val ADV_TOFU_GEM: DeferredBlock<Block> = BLOCKS.register("adv_tofu_gem") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.DIAMOND)
            .strength(5.0f, 10.0f)
            .lightLevel { 10 }
            .sound(SoundType.METAL))
    }

    // === Barrels ===
    @JvmField
    val BARREL_MISO: DeferredBlock<Block> = BLOCKS.register("barrel_miso") {
        BarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(1.0f)
            .sound(SoundType.WOOD)
            .noOcclusion(), 3)
    }

    @JvmField
    val BARREL_MISO_TOFU: DeferredBlock<Block> = BLOCKS.register("barrel_miso_tofu") {
        BarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(1.0f)
            .sound(SoundType.WOOD)
            .noOcclusion(), 2)
    }

    @JvmField
    val BARREL_GLOWTOFU: DeferredBlock<Block> = BLOCKS.register("barrel_glowtofu") {
        BarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(1.0f)
            .sound(SoundType.WOOD)
            .noOcclusion(), 4)
    }

    @JvmField
    val BARREL_ADV_TOFU_GEM: DeferredBlock<Block> = BLOCKS.register("barrel_adv_tofu_gem") {
        BarrelBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(1.0f)
            .sound(SoundType.WOOD)
            .noOcclusion(), 4)
    }

    // === Tofu Cake ===
    @JvmField
    val TOFU_CAKE: DeferredBlock<Block> = BLOCKS.register("tofu_cake") {
        TofuCakeBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .strength(0.5f)
            .sound(SoundType.WOOL)
            .noOcclusion())
    }

    // === Portal ===
    @JvmField
    val TOFU_PORTAL: DeferredBlock<Block> = BLOCKS.register("tofu_portal") {
        TofuPortalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.QUARTZ)
            .noCollission()
            .strength(-1.0f)
            .lightLevel { 11 }
            .noLootTable())
    }

    // === Machines ===
    @JvmField
    val TF_MACHINE_CASE: DeferredBlock<TfMachineCaseBlock> = BLOCKS.register("tf_machine_case") {
        TfMachineCaseBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops())
    }

    @JvmField
    val TF_STORAGE: DeferredBlock<TfStorageBlock> = BLOCKS.register("tf_storage") {
        TfStorageBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(TfStorageBlock.ACTIVE)) 13 else 0 })
    }

    @JvmField
    val TF_CONDENSER: DeferredBlock<TfCondenserBlock> = BLOCKS.register("tf_condenser") {
        TfCondenserBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(TfCondenserBlock.ACTIVE)) 13 else 0 })
    }

    @JvmField
    val TF_OVEN: DeferredBlock<TfOvenBlock> = BLOCKS.register("tf_oven") {
        TfOvenBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(TfOvenBlock.ACTIVE)) 13 else 0 })
    }

    @JvmField
    val TF_REFORMER: DeferredBlock<TfReformerBlock> = BLOCKS.register("tf_reformer") {
        TfReformerBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(TfReformerBlock.ACTIVE)) 13 else 0 })
    }

    @JvmField
    val TF_SATURATOR: DeferredBlock<TfSaturatorBlock> = BLOCKS.register("tf_saturator") {
        TfSaturatorBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .lightLevel { state -> if (state.getValue(TfSaturatorBlock.ACTIVE)) 13 else 0 })
    }

    @JvmField
    val TF_COLLECTOR: DeferredBlock<TfCollectorBlock> = BLOCKS.register("tf_collector") {
        TfCollectorBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops())
    }

    @JvmField
    val TF_ANTENNA: DeferredBlock<TfAntennaBlock> = BLOCKS.register("tf_antenna") {
        TfAntennaBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
            .noOcclusion())
    }

    // === Trees ===
    @JvmField
    val TC_LOG: DeferredBlock<Block> = BLOCKS.register("tc_log") {
        RotatedPillarBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0f)
            .sound(SoundType.WOOD))
    }

    @JvmField
    val TC_LEAVES: DeferredBlock<Block> = BLOCKS.register("tc_leaves") {
        LeavesBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.PLANT)
            .strength(0.2f)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isSuffocating { _, _, _ -> false }
            .isViewBlocking { _, _, _ -> false }
            .pushReaction(PushReaction.DESTROY))
    }

    @JvmField
    val TC_SAPLING: DeferredBlock<Block> = BLOCKS.register("tc_sapling") {
        SaplingBlock(null,
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .noCollission()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS)
                .pushReaction(PushReaction.DESTROY))
    }

    // === Fluid Blocks ===
    @JvmField
    val SOYMILK: DeferredBlock<LiquidBlock> = BLOCKS.register("soymilk") {
        LiquidBlock(TcFluids.SOYMILK_SOURCE.get(),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .noCollission()
                .strength(100f)
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY))
    }

    @JvmField
    val SOYMILK_HELL: DeferredBlock<LiquidBlock> = BLOCKS.register("soymilk_hell") {
        LiquidBlock(TcFluids.SOYMILK_HELL_SOURCE.get(),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.NETHER)
                .noCollission()
                .strength(100f)
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY))
    }

    @JvmField
    val SOY_SAUCE: DeferredBlock<LiquidBlock> = BLOCKS.register("soy_sauce") {
        LiquidBlock(TcFluids.SOY_SAUCE_SOURCE.get(),
            BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .noCollission()
                .strength(100f)
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY))
    }

    // === Chikuwa Platform ===
    @JvmField
    val CHIKUWA_PLATFORM: DeferredBlock<Block> = BLOCKS.register("chikuwa_platform") {
        Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.SAND)
            .strength(0.5f)
            .sound(SoundType.WOOL)
            .noOcclusion())
    }

    // === Helper methods ===
    private fun registerTofuBlock(name: String, hardness: Float, resistance: Float): DeferredBlock<Block> =
        BLOCKS.register(name) {
            Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(hardness, resistance)
                .sound(SoundType.SNOW))
        }

    private fun registerTofuBlockGlow(name: String, hardness: Float, resistance: Float, light: Int): DeferredBlock<Block> =
        BLOCKS.register(name) {
            Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.QUARTZ)
                .strength(hardness, resistance)
                .lightLevel { light }
                .sound(SoundType.SNOW))
        }

    private fun registerStairs(name: String, base: DeferredBlock<Block>): DeferredBlock<StairBlock> =
        BLOCKS.register(name) {
            StairBlock(base.get().defaultBlockState(),
                BlockBehaviour.Properties.ofFullCopy(base.get()))
        }

    private fun registerSlab(name: String, base: DeferredBlock<Block>): DeferredBlock<SlabBlock> =
        BLOCKS.register(name) {
            SlabBlock(BlockBehaviour.Properties.ofFullCopy(base.get()))
        }

    private fun registerWall(name: String, base: DeferredBlock<Block>): DeferredBlock<WallBlock> =
        BLOCKS.register(name) {
            WallBlock(BlockBehaviour.Properties.ofFullCopy(base.get()))
        }

    private fun registerDoor(name: String, base: DeferredBlock<Block>): DeferredBlock<DoorBlock> =
        BLOCKS.register(name) {
            DoorBlock(BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get()).noOcclusion().pushReaction(PushReaction.DESTROY))
        }

    private fun registerFenceGate(name: String, base: DeferredBlock<Block>): DeferredBlock<FenceGateBlock> =
        BLOCKS.register(name) {
            FenceGateBlock(WoodType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get()))
        }

    private fun registerTrapdoor(name: String, base: DeferredBlock<Block>): DeferredBlock<TrapDoorBlock> =
        BLOCKS.register(name) {
            TrapDoorBlock(BlockSetType.OAK,
                BlockBehaviour.Properties.ofFullCopy(base.get()).noOcclusion())
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        BLOCKS.register(modEventBus)
    }
}
