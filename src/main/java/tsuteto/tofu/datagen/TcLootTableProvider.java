package tsuteto.tofu.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import tsuteto.tofu.block.SoybeanBlock;
import tsuteto.tofu.block.SoybeanHellBlock;
import tsuteto.tofu.init.TcBlocks;
import tsuteto.tofu.init.TcItems;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TcLootTableProvider extends LootTableProvider {

    public TcLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(TcBlockLoot::new, net.minecraft.world.level.storage.loot.parameters.LootContextParamSets.BLOCK)
        ), registries);
    }

    public static class TcBlockLoot extends BlockLootSubProvider {

        protected TcBlockLoot(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected void generate() {
            HolderLookup.RegistryLookup<Enchantment> enchantmentLookup =
                    this.registries.lookupOrThrow(Registries.ENCHANTMENT);

            // === Tofu blocks drop 4 of their food item ===
            tofuBlockDrop(TcBlocks.TOFU_KINU.get(), TcItems.TOFU_KINU_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_MOMEN.get(), TcItems.TOFU_MOMEN_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_ISHI.get(), TcItems.TOFU_ISHI_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_GRILLED.get(), TcItems.TOFU_GRILLED_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_FRIED_POUCH.get(), TcItems.TOFU_FRIED_POUCH_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_FRIED.get(), TcItems.TOFU_FRIED_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_EGG.get(), TcItems.TOFU_EGG_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_ANNIN.get(), TcItems.TOFU_ANNIN_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_SESAME.get(), TcItems.TOFU_SESAME_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_ZUNDA.get(), TcItems.TOFU_ZUNDA_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_STRAWBERRY.get(), TcItems.TOFU_STRAWBERRY_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_MISO.get(), TcItems.TOFU_MISO_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_HELL.get(), TcItems.TOFU_HELL_FOOD.get());
            tofuBlockDrop(TcBlocks.TOFU_GLOW.get(), TcItems.TOFU_GLOW_FOOD.get());

            // Tofu diamond and metal drop themselves
            dropSelf(TcBlocks.TOFU_DIAMOND.get());
            dropSelf(TcBlocks.TOFU_METAL.get());
            dropSelf(TcBlocks.TOFU_MINCED.get());
            dropSelf(TcBlocks.TOFU_DRIED.get());

            // === Terrain & misc ===
            dropSelf(TcBlocks.TOFU_TERRAIN.get());
            dropSelf(TcBlocks.SALT_BLOCK.get());
            dropSelf(TcBlocks.NATTO_BED.get());
            dropSelf(TcBlocks.NATTO.get());
            dropSelf(TcBlocks.ADV_TOFU_GEM.get());
            dropSelf(TcBlocks.CHIKUWA_PLATFORM.get());
            dropSelf(TcBlocks.TOFU_FARMLAND.get());
            dropSelf(TcBlocks.LEEK.get());

            // === Stairs drop themselves ===
            dropSelf(TcBlocks.TOFU_STAIRS_KINU.get());
            dropSelf(TcBlocks.TOFU_STAIRS_MOMEN.get());
            dropSelf(TcBlocks.TOFU_STAIRS_ISHI.get());
            dropSelf(TcBlocks.TOFU_STAIRS_METAL.get());
            dropSelf(TcBlocks.TOFU_STAIRS_GRILLED.get());
            dropSelf(TcBlocks.TOFU_STAIRS_DRIED.get());
            dropSelf(TcBlocks.TOFU_STAIRS_FRIED_POUCH.get());
            dropSelf(TcBlocks.TOFU_STAIRS_FRIED.get());
            dropSelf(TcBlocks.TOFU_STAIRS_EGG.get());
            dropSelf(TcBlocks.TOFU_STAIRS_ANNIN.get());
            dropSelf(TcBlocks.TOFU_STAIRS_SESAME.get());
            dropSelf(TcBlocks.TOFU_STAIRS_ZUNDA.get());
            dropSelf(TcBlocks.TOFU_STAIRS_STRAWBERRY.get());
            dropSelf(TcBlocks.TOFU_STAIRS_HELL.get());
            dropSelf(TcBlocks.TOFU_STAIRS_GLOW.get());
            dropSelf(TcBlocks.TOFU_STAIRS_DIAMOND.get());
            dropSelf(TcBlocks.TOFU_STAIRS_MISO.get());

            // === Slabs ===
            add(TcBlocks.TOFU_SLAB_KINU.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_KINU.get()));
            add(TcBlocks.TOFU_SLAB_MOMEN.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_MOMEN.get()));
            add(TcBlocks.TOFU_SLAB_ISHI.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_ISHI.get()));
            add(TcBlocks.TOFU_SLAB_METAL.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_METAL.get()));
            add(TcBlocks.TOFU_SLAB_GRILLED.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_GRILLED.get()));
            add(TcBlocks.TOFU_SLAB_DRIED.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_DRIED.get()));
            add(TcBlocks.TOFU_SLAB_FRIED_POUCH.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_FRIED_POUCH.get()));
            add(TcBlocks.TOFU_SLAB_FRIED.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_FRIED.get()));
            add(TcBlocks.TOFU_SLAB_EGG.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_EGG.get()));
            add(TcBlocks.TOFU_SLAB_ANNIN.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_ANNIN.get()));
            add(TcBlocks.TOFU_SLAB_SESAME.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_SESAME.get()));
            add(TcBlocks.TOFU_SLAB_ZUNDA.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_ZUNDA.get()));
            add(TcBlocks.TOFU_SLAB_STRAWBERRY.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_STRAWBERRY.get()));
            add(TcBlocks.TOFU_SLAB_HELL.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_HELL.get()));
            add(TcBlocks.TOFU_SLAB_GLOW.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_GLOW.get()));
            add(TcBlocks.TOFU_SLAB_DIAMOND.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_DIAMOND.get()));
            add(TcBlocks.TOFU_SLAB_MISO.get(), createSlabItemTable(TcBlocks.TOFU_SLAB_MISO.get()));

            // === Walls ===
            dropSelf(TcBlocks.TOFU_WALL_KINU.get());
            dropSelf(TcBlocks.TOFU_WALL_MOMEN.get());
            dropSelf(TcBlocks.TOFU_WALL_ISHI.get());
            dropSelf(TcBlocks.TOFU_WALL_METAL.get());
            dropSelf(TcBlocks.TOFU_WALL_GRILLED.get());
            dropSelf(TcBlocks.TOFU_WALL_DRIED.get());
            dropSelf(TcBlocks.TOFU_WALL_DIAMOND.get());

            // === Doors ===
            add(TcBlocks.TOFU_DOOR_KINU.get(), createDoorTable(TcBlocks.TOFU_DOOR_KINU.get()));
            add(TcBlocks.TOFU_DOOR_MOMEN.get(), createDoorTable(TcBlocks.TOFU_DOOR_MOMEN.get()));
            add(TcBlocks.TOFU_DOOR_ISHI.get(), createDoorTable(TcBlocks.TOFU_DOOR_ISHI.get()));
            add(TcBlocks.TOFU_DOOR_METAL.get(), createDoorTable(TcBlocks.TOFU_DOOR_METAL.get()));
            add(TcBlocks.TOFU_DOOR_DIAMOND.get(), createDoorTable(TcBlocks.TOFU_DOOR_DIAMOND.get()));

            // === Trapdoors ===
            dropSelf(TcBlocks.TOFU_TRAPDOOR_KINU.get());
            dropSelf(TcBlocks.TOFU_TRAPDOOR_MOMEN.get());
            dropSelf(TcBlocks.TOFU_TRAPDOOR_ISHI.get());

            // === Fence gates ===
            dropSelf(TcBlocks.TOFU_FENCE_GATE_KINU.get());
            dropSelf(TcBlocks.TOFU_FENCE_GATE_MOMEN.get());
            dropSelf(TcBlocks.TOFU_FENCE_GATE_ISHI.get());
            dropSelf(TcBlocks.TOFU_FENCE_GATE_METAL.get());
            dropSelf(TcBlocks.TOFU_FENCE_GATE_DIAMOND.get());

            // === Ore blocks with fortune ===
            add(TcBlocks.ORE_TOFU.get(), createOreDrop(TcBlocks.ORE_TOFU.get(), TcItems.TOFU_KINU_FOOD.get()));
            add(TcBlocks.ORE_TOFU_DIAMOND.get(), createOreDrop(TcBlocks.ORE_TOFU_DIAMOND.get(), TcItems.TOFU_DIAMOND_NUGGET.get()));

            // === Crop blocks with stage-dependent drops ===
            soybeanCropDrops(TcBlocks.SOYBEAN.get(), TcItems.SOYBEANS.get(), TcItems.EDAMAME.get(), SoybeanBlock.AGE, enchantmentLookup);
            soybeanCropDrops(TcBlocks.SOYBEAN_HELL.get(), TcItems.SOYBEANS_HELL.get(), TcItems.EDAMAME.get(), SoybeanHellBlock.AGE, enchantmentLookup);

            // === Functional blocks drop themselves ===
            dropSelf(TcBlocks.SALT_PAN.get());
            dropSelf(TcBlocks.SALT_FURNACE.get());
            dropSelf(TcBlocks.MORIJIO.get());

            // === Machine blocks ===
            dropSelf(TcBlocks.TF_MACHINE_CASE.get());
            dropSelf(TcBlocks.TF_STORAGE.get());
            dropSelf(TcBlocks.TF_CONDENSER.get());
            dropSelf(TcBlocks.TF_OVEN.get());
            dropSelf(TcBlocks.TF_REFORMER.get());
            dropSelf(TcBlocks.TF_SATURATOR.get());
            dropSelf(TcBlocks.TF_COLLECTOR.get());
            dropSelf(TcBlocks.TF_ANTENNA.get());

            // === Barrel blocks ===
            dropSelf(TcBlocks.BARREL_MISO.get());
            dropSelf(TcBlocks.BARREL_MISO_TOFU.get());
            dropSelf(TcBlocks.BARREL_GLOWTOFU.get());
            dropSelf(TcBlocks.BARREL_ADV_TOFU_GEM.get());

            // === Tofu cake (no drop, consumed) ===
            dropOther(TcBlocks.TOFU_CAKE.get(), TcItems.TOFU_CAKE_ITEM.get());

            // === Tree blocks ===
            dropSelf(TcBlocks.TC_LOG.get());
            add(TcBlocks.TC_LEAVES.get(), createLeavesDrops(TcBlocks.TC_LEAVES.get(), TcBlocks.TC_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            dropSelf(TcBlocks.TC_SAPLING.get());

            // === Yuba & sprouts ===
            dropSelf(TcBlocks.YUBA.get());
            dropSelf(TcBlocks.SPROUTS.get());

            // === Portal (no loot table) ===
            // TcBlocks.TOFU_PORTAL has noLootTable() in its properties

            // === Fluid blocks (no loot) ===
            add(TcBlocks.SOYMILK.get(), noDrop());
            add(TcBlocks.SOYMILK_HELL.get(), noDrop());
            add(TcBlocks.SOY_SAUCE.get(), noDrop());
        }

        private void tofuBlockDrop(Block block, Item food) {
            add(block, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(food)
                                    .apply(SetItemCountFunction.setCount(ConstantValue.exactly(4.0f))))));
        }

        private void soybeanCropDrops(Block crop, Item seeds, Item bonusDrop,
                                       IntegerProperty ageProperty,
                                       HolderLookup.RegistryLookup<Enchantment> enchantmentLookup) {
            LootItemBlockStatePropertyCondition.Builder fullyGrown =
                    LootItemBlockStatePropertyCondition.hasBlockStateProperties(crop)
                            .setProperties(net.minecraft.world.level.block.state.predicate.BlockStatePredicate
                                    .forBlock(crop).where(ageProperty, p -> p == 7));

            add(crop, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .add(LootItem.lootTableItem(seeds)))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .when(fullyGrown)
                            .add(LootItem.lootTableItem(seeds)
                                    .apply(ApplyBonusCount.addBonusBinomialDistributionCount(
                                            enchantmentLookup.getOrThrow(Enchantments.FORTUNE), 0.5714286f, 3))))
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0f))
                            .when(fullyGrown)
                            .add(LootItem.lootTableItem(bonusDrop)
                                    .apply(SetItemCountFunction.setCount(
                                            UniformGenerator.between(1.0f, 2.0f))))));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return TcBlocks.BLOCKS.getEntries().stream()
                    .map(e -> (Block) e.get())
                    .filter(b -> b != TcBlocks.TOFU_PORTAL.get()) // Portal has noLootTable
                    .toList();
        }
    }
}
