package tsuteto.tofu.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.block.*;
import tsuteto.tofu.init.TcBlocks;

public class TcBlockStateProvider extends BlockStateProvider {

    public TcBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TofuCraftMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // === Simple cube tofu blocks ===
        simpleTofuBlock(TcBlocks.TOFU_KINU);
        simpleTofuBlock(TcBlocks.TOFU_MOMEN);
        simpleTofuBlock(TcBlocks.TOFU_ISHI);
        simpleTofuBlock(TcBlocks.TOFU_METAL);
        simpleTofuBlock(TcBlocks.TOFU_GRILLED);
        simpleTofuBlock(TcBlocks.TOFU_DRIED);
        simpleTofuBlock(TcBlocks.TOFU_FRIED_POUCH);
        simpleTofuBlock(TcBlocks.TOFU_FRIED);
        simpleTofuBlock(TcBlocks.TOFU_EGG);
        simpleTofuBlock(TcBlocks.TOFU_ANNIN);
        simpleTofuBlock(TcBlocks.TOFU_SESAME);
        simpleTofuBlock(TcBlocks.TOFU_ZUNDA);
        simpleTofuBlock(TcBlocks.TOFU_STRAWBERRY);
        simpleTofuBlock(TcBlocks.TOFU_MISO);
        simpleTofuBlock(TcBlocks.TOFU_HELL);
        simpleTofuBlock(TcBlocks.TOFU_GLOW);
        simpleTofuBlock(TcBlocks.TOFU_DIAMOND);
        simpleTofuBlock(TcBlocks.TOFU_MINCED);

        // === Terrain & misc simple blocks ===
        simpleBlockWithItem(TcBlocks.TOFU_TERRAIN.get(), cubeAll(TcBlocks.TOFU_TERRAIN.get()));
        simpleBlockWithItem(TcBlocks.SALT_BLOCK.get(), cubeAll(TcBlocks.SALT_BLOCK.get()));
        simpleBlockWithItem(TcBlocks.NATTO_BED.get(), cubeAll(TcBlocks.NATTO_BED.get()));
        simpleBlockWithItem(TcBlocks.NATTO.get(), cubeAll(TcBlocks.NATTO.get()));
        simpleBlockWithItem(TcBlocks.ADV_TOFU_GEM.get(), cubeAll(TcBlocks.ADV_TOFU_GEM.get()));
        simpleBlockWithItem(TcBlocks.CHIKUWA_PLATFORM.get(), cubeAll(TcBlocks.CHIKUWA_PLATFORM.get()));

        // === Tofu Farmland ===
        ResourceLocation tofuTerrainTex = modBlockLoc("tofu_terrain");
        ResourceLocation tofuFarmlandTopTex = modBlockLoc("tofu_farmland_top");
        ModelFile farmlandModel = models().withExistingParent(blockName(TcBlocks.TOFU_FARMLAND.get()), mcLoc("block/farmland"))
                .texture("dirt", tofuTerrainTex)
                .texture("top", tofuFarmlandTopTex);
        simpleBlockWithItem(TcBlocks.TOFU_FARMLAND.get(), farmlandModel);

        // === Stairs ===
        tofuStairs(TcBlocks.TOFU_STAIRS_KINU, TcBlocks.TOFU_KINU);
        tofuStairs(TcBlocks.TOFU_STAIRS_MOMEN, TcBlocks.TOFU_MOMEN);
        tofuStairs(TcBlocks.TOFU_STAIRS_ISHI, TcBlocks.TOFU_ISHI);
        tofuStairs(TcBlocks.TOFU_STAIRS_METAL, TcBlocks.TOFU_METAL);
        tofuStairs(TcBlocks.TOFU_STAIRS_GRILLED, TcBlocks.TOFU_GRILLED);
        tofuStairs(TcBlocks.TOFU_STAIRS_DRIED, TcBlocks.TOFU_DRIED);
        tofuStairs(TcBlocks.TOFU_STAIRS_FRIED_POUCH, TcBlocks.TOFU_FRIED_POUCH);
        tofuStairs(TcBlocks.TOFU_STAIRS_FRIED, TcBlocks.TOFU_FRIED);
        tofuStairs(TcBlocks.TOFU_STAIRS_EGG, TcBlocks.TOFU_EGG);
        tofuStairs(TcBlocks.TOFU_STAIRS_ANNIN, TcBlocks.TOFU_ANNIN);
        tofuStairs(TcBlocks.TOFU_STAIRS_SESAME, TcBlocks.TOFU_SESAME);
        tofuStairs(TcBlocks.TOFU_STAIRS_ZUNDA, TcBlocks.TOFU_ZUNDA);
        tofuStairs(TcBlocks.TOFU_STAIRS_STRAWBERRY, TcBlocks.TOFU_STRAWBERRY);
        tofuStairs(TcBlocks.TOFU_STAIRS_HELL, TcBlocks.TOFU_HELL);
        tofuStairs(TcBlocks.TOFU_STAIRS_GLOW, TcBlocks.TOFU_GLOW);
        tofuStairs(TcBlocks.TOFU_STAIRS_DIAMOND, TcBlocks.TOFU_DIAMOND);
        tofuStairs(TcBlocks.TOFU_STAIRS_MISO, TcBlocks.TOFU_MISO);

        // === Slabs ===
        tofuSlab(TcBlocks.TOFU_SLAB_KINU, TcBlocks.TOFU_KINU);
        tofuSlab(TcBlocks.TOFU_SLAB_MOMEN, TcBlocks.TOFU_MOMEN);
        tofuSlab(TcBlocks.TOFU_SLAB_ISHI, TcBlocks.TOFU_ISHI);
        tofuSlab(TcBlocks.TOFU_SLAB_METAL, TcBlocks.TOFU_METAL);
        tofuSlab(TcBlocks.TOFU_SLAB_GRILLED, TcBlocks.TOFU_GRILLED);
        tofuSlab(TcBlocks.TOFU_SLAB_DRIED, TcBlocks.TOFU_DRIED);
        tofuSlab(TcBlocks.TOFU_SLAB_FRIED_POUCH, TcBlocks.TOFU_FRIED_POUCH);
        tofuSlab(TcBlocks.TOFU_SLAB_FRIED, TcBlocks.TOFU_FRIED);
        tofuSlab(TcBlocks.TOFU_SLAB_EGG, TcBlocks.TOFU_EGG);
        tofuSlab(TcBlocks.TOFU_SLAB_ANNIN, TcBlocks.TOFU_ANNIN);
        tofuSlab(TcBlocks.TOFU_SLAB_SESAME, TcBlocks.TOFU_SESAME);
        tofuSlab(TcBlocks.TOFU_SLAB_ZUNDA, TcBlocks.TOFU_ZUNDA);
        tofuSlab(TcBlocks.TOFU_SLAB_STRAWBERRY, TcBlocks.TOFU_STRAWBERRY);
        tofuSlab(TcBlocks.TOFU_SLAB_HELL, TcBlocks.TOFU_HELL);
        tofuSlab(TcBlocks.TOFU_SLAB_GLOW, TcBlocks.TOFU_GLOW);
        tofuSlab(TcBlocks.TOFU_SLAB_DIAMOND, TcBlocks.TOFU_DIAMOND);
        tofuSlab(TcBlocks.TOFU_SLAB_MISO, TcBlocks.TOFU_MISO);

        // === Walls ===
        tofuWall(TcBlocks.TOFU_WALL_KINU, TcBlocks.TOFU_KINU);
        tofuWall(TcBlocks.TOFU_WALL_MOMEN, TcBlocks.TOFU_MOMEN);
        tofuWall(TcBlocks.TOFU_WALL_ISHI, TcBlocks.TOFU_ISHI);
        tofuWall(TcBlocks.TOFU_WALL_METAL, TcBlocks.TOFU_METAL);
        tofuWall(TcBlocks.TOFU_WALL_GRILLED, TcBlocks.TOFU_GRILLED);
        tofuWall(TcBlocks.TOFU_WALL_DRIED, TcBlocks.TOFU_DRIED);
        tofuWall(TcBlocks.TOFU_WALL_DIAMOND, TcBlocks.TOFU_DIAMOND);

        // === Doors ===
        tofuDoor(TcBlocks.TOFU_DOOR_KINU);
        tofuDoor(TcBlocks.TOFU_DOOR_MOMEN);
        tofuDoor(TcBlocks.TOFU_DOOR_ISHI);
        tofuDoor(TcBlocks.TOFU_DOOR_METAL);
        tofuDoor(TcBlocks.TOFU_DOOR_DIAMOND);

        // === Trapdoors ===
        tofuTrapdoor(TcBlocks.TOFU_TRAPDOOR_KINU);
        tofuTrapdoor(TcBlocks.TOFU_TRAPDOOR_MOMEN);
        tofuTrapdoor(TcBlocks.TOFU_TRAPDOOR_ISHI);

        // === Fence Gates ===
        tofuFenceGate(TcBlocks.TOFU_FENCE_GATE_KINU, TcBlocks.TOFU_KINU);
        tofuFenceGate(TcBlocks.TOFU_FENCE_GATE_MOMEN, TcBlocks.TOFU_MOMEN);
        tofuFenceGate(TcBlocks.TOFU_FENCE_GATE_ISHI, TcBlocks.TOFU_ISHI);
        tofuFenceGate(TcBlocks.TOFU_FENCE_GATE_METAL, TcBlocks.TOFU_METAL);
        tofuFenceGate(TcBlocks.TOFU_FENCE_GATE_DIAMOND, TcBlocks.TOFU_DIAMOND);

        // === Crop blocks ===
        cropBlock(TcBlocks.SOYBEAN.get(), SoybeanBlock.AGE, 7, "soybean");
        cropBlock(TcBlocks.SOYBEAN_HELL.get(), SoybeanHellBlock.AGE, 7, "soybean_hell");

        // === Ores ===
        simpleBlockWithItem(TcBlocks.ORE_TOFU.get(), cubeAll(TcBlocks.ORE_TOFU.get()));
        simpleBlockWithItem(TcBlocks.ORE_TOFU_DIAMOND.get(), cubeAll(TcBlocks.ORE_TOFU_DIAMOND.get()));

        // === Directional machine blocks with FACING + LIT ===
        horizontalMachineBlock(TcBlocks.SALT_FURNACE.get(), "salt_furnace");

        // === Directional machine blocks with FACING + ACTIVE ===
        horizontalActiveMachineBlock(TcBlocks.TF_STORAGE.get(), "tf_storage");
        horizontalActiveMachineBlock(TcBlocks.TF_CONDENSER.get(), "tf_condenser");
        horizontalActiveMachineBlock(TcBlocks.TF_OVEN.get(), "tf_oven");
        horizontalActiveMachineBlock(TcBlocks.TF_REFORMER.get(), "tf_reformer");
        horizontalActiveMachineBlock(TcBlocks.TF_SATURATOR.get(), "tf_saturator");

        // Collector (FACING only, no ACTIVE)
        horizontalBlock(TcBlocks.TF_COLLECTOR.get(),
                modBlockLoc("tf_collector_side"),
                modBlockLoc("tf_collector_front"),
                modBlockLoc("tf_collector_top"));
        simpleBlockItem(TcBlocks.TF_COLLECTOR.get(), models().getExistingFile(modLoc("block/tf_collector")));

        // Machine case (simple cube)
        simpleBlockWithItem(TcBlocks.TF_MACHINE_CASE.get(), cubeAll(TcBlocks.TF_MACHINE_CASE.get()));

        // === Non-full blocks ===
        // Salt pan
        ModelFile saltPanModel = models().withExistingParent("salt_pan", mcLoc("block/block"))
                .texture("particle", modBlockLoc("salt_pan_side"))
                .texture("bottom", modBlockLoc("salt_pan_bottom"))
                .texture("top", modBlockLoc("salt_pan_top"))
                .texture("side", modBlockLoc("salt_pan_side"))
                .element()
                    .from(0, 0, 0).to(16, 6, 16)
                    .face(net.minecraft.core.Direction.DOWN).uvs(0, 0, 16, 16).texture("#bottom").cullface(net.minecraft.core.Direction.DOWN).end()
                    .face(net.minecraft.core.Direction.UP).uvs(0, 0, 16, 16).texture("#top").end()
                    .face(net.minecraft.core.Direction.NORTH).uvs(0, 10, 16, 16).texture("#side").cullface(net.minecraft.core.Direction.NORTH).end()
                    .face(net.minecraft.core.Direction.SOUTH).uvs(0, 10, 16, 16).texture("#side").cullface(net.minecraft.core.Direction.SOUTH).end()
                    .face(net.minecraft.core.Direction.WEST).uvs(0, 10, 16, 16).texture("#side").cullface(net.minecraft.core.Direction.WEST).end()
                    .face(net.minecraft.core.Direction.EAST).uvs(0, 10, 16, 16).texture("#side").cullface(net.minecraft.core.Direction.EAST).end()
                .end();
        simpleBlockWithItem(TcBlocks.SALT_PAN.get(), saltPanModel);

        // Morijio
        ModelFile morijioModel = models().withExistingParent("morijio", mcLoc("block/block"))
                .texture("particle", modBlockLoc("morijio"))
                .texture("all", modBlockLoc("morijio"))
                .element()
                    .from(4, 0, 4).to(12, 10, 12)
                    .allFaces((dir, face) -> face.uvs(4, 6, 12, 16).texture("#all"))
                .end();
        simpleBlock(TcBlocks.MORIJIO.get(), morijioModel);
        simpleBlockItem(TcBlocks.MORIJIO.get(), morijioModel);

        // Antenna (non-full block)
        ModelFile antennaModel = models().withExistingParent("tf_antenna", mcLoc("block/block"))
                .texture("particle", modBlockLoc("tf_antenna"))
                .texture("all", modBlockLoc("tf_antenna"))
                .element()
                    .from(6, 0, 6).to(10, 16, 10)
                    .allFaces((dir, face) -> face.uvs(6, 0, 10, 16).texture("#all"))
                .end()
                .element()
                    .from(2, 10, 7).to(14, 12, 9)
                    .allFaces((dir, face) -> face.uvs(2, 10, 14, 12).texture("#all"))
                .end()
                .element()
                    .from(7, 10, 2).to(9, 12, 14)
                    .allFaces((dir, face) -> face.uvs(2, 10, 14, 12).texture("#all"))
                .end()
                .element()
                    .from(4, 14, 7).to(12, 16, 9)
                    .allFaces((dir, face) -> face.uvs(4, 14, 12, 16).texture("#all"))
                .end()
                .element()
                    .from(7, 14, 4).to(9, 16, 12)
                    .allFaces((dir, face) -> face.uvs(4, 14, 12, 16).texture("#all"))
                .end();
        simpleBlock(TcBlocks.TF_ANTENNA.get(), antennaModel);
        simpleBlockItem(TcBlocks.TF_ANTENNA.get(), antennaModel);

        // === Barrel blocks ===
        barrelBlock(TcBlocks.BARREL_MISO, "barrel_miso");
        barrelBlock(TcBlocks.BARREL_MISO_TOFU, "barrel_miso_tofu");
        barrelBlock(TcBlocks.BARREL_GLOWTOFU, "barrel_glowtofu");
        barrelBlock(TcBlocks.BARREL_ADV_TOFU_GEM, "barrel_adv_tofu_gem");

        // === Yuba (non-full thin block) ===
        ModelFile yubaModel = models().withExistingParent("yuba", mcLoc("block/block"))
                .texture("particle", modBlockLoc("yuba"))
                .texture("all", modBlockLoc("yuba"))
                .element()
                    .from(0, 0, 0).to(16, 1, 16)
                    .allFaces((dir, face) -> face.uvs(0, 0, 16, 16).texture("#all"))
                .end();
        simpleBlock(TcBlocks.YUBA.get(), yubaModel);
        simpleBlockItem(TcBlocks.YUBA.get(), yubaModel);

        // === Sprouts ===
        ModelFile sproutsModel = models().cross("sprouts", modBlockLoc("sprouts")).renderType("cutout");
        simpleBlock(TcBlocks.SPROUTS.get(), sproutsModel);

        // === Leek ===
        simpleBlockWithItem(TcBlocks.LEEK.get(), cubeAll(TcBlocks.LEEK.get()));

        // === Trees ===
        logBlock((RotatedPillarBlock) TcBlocks.TC_LOG.get());
        simpleBlockItem(TcBlocks.TC_LOG.get(), models().getExistingFile(modLoc("block/tc_log")));

        simpleBlockWithItem(TcBlocks.TC_LEAVES.get(),
                models().cubeAll("tc_leaves", modBlockLoc("tc_leaves")).renderType("cutout"));

        ModelFile saplingModel = models().cross("tc_sapling", modBlockLoc("tc_sapling")).renderType("cutout");
        simpleBlock(TcBlocks.TC_SAPLING.get(), saplingModel);

        // === Tofu Cake (non-full block) ===
        ModelFile tofuCakeModel = models().withExistingParent("tofu_cake", mcLoc("block/block"))
                .texture("particle", modBlockLoc("tofu_cake_side"))
                .texture("bottom", modBlockLoc("tofu_cake_bottom"))
                .texture("top", modBlockLoc("tofu_cake_top"))
                .texture("side", modBlockLoc("tofu_cake_side"))
                .element()
                    .from(1, 0, 1).to(15, 8, 15)
                    .face(net.minecraft.core.Direction.DOWN).uvs(1, 1, 15, 15).texture("#bottom").end()
                    .face(net.minecraft.core.Direction.UP).uvs(1, 1, 15, 15).texture("#top").end()
                    .face(net.minecraft.core.Direction.NORTH).uvs(1, 8, 15, 16).texture("#side").end()
                    .face(net.minecraft.core.Direction.SOUTH).uvs(1, 8, 15, 16).texture("#side").end()
                    .face(net.minecraft.core.Direction.WEST).uvs(1, 8, 15, 16).texture("#side").end()
                    .face(net.minecraft.core.Direction.EAST).uvs(1, 8, 15, 16).texture("#side").end()
                .end();
        simpleBlock(TcBlocks.TOFU_CAKE.get(), tofuCakeModel);

        // === Portal (not rendered as model, but register a blockstate) ===
        simpleBlock(TcBlocks.TOFU_PORTAL.get(),
                models().withExistingParent("tofu_portal", mcLoc("block/block"))
                        .texture("particle", modBlockLoc("tofu_portal"))
                        .texture("all", modBlockLoc("tofu_portal"))
                        .renderType("translucent"));
    }

    // === Helper methods ===

    private void simpleTofuBlock(DeferredBlock<Block> block) {
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    private <T extends StairBlock> void tofuStairs(DeferredBlock<T> stairsBlock, DeferredBlock<Block> baseBlock) {
        ResourceLocation texture = modBlockLoc(blockName(baseBlock.get()));
        stairsBlock(stairsBlock.get(), texture);
        simpleBlockItem(stairsBlock.get(), models().getExistingFile(
                modLoc("block/" + blockName(stairsBlock.get()))));
    }

    private <T extends SlabBlock> void tofuSlab(DeferredBlock<T> slabBlock, DeferredBlock<Block> baseBlock) {
        ResourceLocation texture = modBlockLoc(blockName(baseBlock.get()));
        slabBlock(slabBlock.get(), modLoc("block/" + blockName(baseBlock.get())), texture);
        simpleBlockItem(slabBlock.get(), models().getExistingFile(
                modLoc("block/" + blockName(slabBlock.get()))));
    }

    private <T extends WallBlock> void tofuWall(DeferredBlock<T> wallBlock, DeferredBlock<Block> baseBlock) {
        ResourceLocation texture = modBlockLoc(blockName(baseBlock.get()));
        wallBlock(wallBlock.get(), texture);
        itemModels().wallInventory(blockName(wallBlock.get()), texture);
    }

    private <T extends DoorBlock> void tofuDoor(DeferredBlock<T> doorBlock) {
        String name = blockName(doorBlock.get());
        ResourceLocation bottom = modBlockLoc(name + "_bottom");
        ResourceLocation top = modBlockLoc(name + "_top");
        doorBlockWithRenderType(doorBlock.get(), bottom, top, "cutout");
        itemModels().basicItem(doorBlock.get().asItem());
    }

    private <T extends TrapDoorBlock> void tofuTrapdoor(DeferredBlock<T> trapdoorBlock) {
        String name = blockName(trapdoorBlock.get());
        ResourceLocation texture = modBlockLoc(name);
        trapdoorBlockWithRenderType(trapdoorBlock.get(), texture, true, "cutout");
        simpleBlockItem(trapdoorBlock.get(), models().getExistingFile(
                modLoc("block/" + name + "_bottom")));
    }

    private <T extends FenceGateBlock> void tofuFenceGate(DeferredBlock<T> gateBlock, DeferredBlock<Block> baseBlock) {
        ResourceLocation texture = modBlockLoc(blockName(baseBlock.get()));
        fenceGateBlock(gateBlock.get(), texture);
        simpleBlockItem(gateBlock.get(), models().getExistingFile(
                modLoc("block/" + blockName(gateBlock.get()))));
    }

    private void cropBlock(Block block, net.minecraft.world.level.block.state.properties.IntegerProperty ageProperty, int maxAge, String baseName) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(ageProperty);
            String textureName = baseName + "_stage" + age;
            ModelFile model = models().crop(textureName, modBlockLoc(textureName)).renderType("cutout");
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    private void horizontalMachineBlock(Block block, String name) {
        ModelFile offModel = models().orientable(name,
                modBlockLoc(name + "_side"),
                modBlockLoc(name + "_front"),
                modBlockLoc(name + "_top"));
        ModelFile onModel = models().orientable(name + "_on",
                modBlockLoc(name + "_side"),
                modBlockLoc(name + "_front_on"),
                modBlockLoc(name + "_top"));

        getVariantBuilder(block).forAllStates(state -> {
            boolean lit = state.getValue(BlockStateProperties.LIT);
            net.minecraft.core.Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return ConfiguredModel.builder()
                    .modelFile(lit ? onModel : offModel)
                    .rotationY(((int) facing.toYRot() + 180) % 360)
                    .build();
        });
        simpleBlockItem(block, offModel);
    }

    private void horizontalActiveMachineBlock(Block block, String name) {
        ModelFile offModel = models().orientable(name,
                modBlockLoc(name + "_side"),
                modBlockLoc(name + "_front"),
                modBlockLoc(name + "_top"));
        ModelFile onModel = models().orientable(name + "_on",
                modBlockLoc(name + "_side"),
                modBlockLoc(name + "_front_on"),
                modBlockLoc(name + "_top"));

        getVariantBuilder(block).forAllStates(state -> {
            boolean active = state.getValue(net.minecraft.world.level.block.state.properties.BooleanProperty.create("active"));
            net.minecraft.core.Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return ConfiguredModel.builder()
                    .modelFile(active ? onModel : offModel)
                    .rotationY(((int) facing.toYRot() + 180) % 360)
                    .build();
        });
        simpleBlockItem(block, offModel);
    }

    private void barrelBlock(DeferredBlock<Block> block, String name) {
        ModelFile barrelModel = models().cubeBottomTop(name,
                modBlockLoc(name + "_side"),
                modBlockLoc(name + "_bottom"),
                modBlockLoc(name + "_top"));
        simpleBlockWithItem(block.get(), barrelModel);
    }

    private String blockName(Block block) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private ResourceLocation modBlockLoc(String name) {
        return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, "block/" + name);
    }

    private ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(TofuCraftMod.MOD_ID, path);
    }

    private ResourceLocation mcLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}
