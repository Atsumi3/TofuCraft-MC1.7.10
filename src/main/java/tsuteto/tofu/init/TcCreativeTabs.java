package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;

public class TcCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOFUCRAFT_TAB =
            CREATIVE_TABS.register("tofucraft_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup." + TofuCraftMod.MOD_ID))
                            .icon(() -> new ItemStack(TcBlocks.TOFU_MOMEN.get()))
                            .displayItems((parameters, output) -> {
                                // Blocks
                                TcBlocks.BLOCKS.getEntries().forEach(entry -> output.accept(new ItemStack(entry.get())));
                                // Items (excluding block items already added via blocks)
                                TcItems.ITEMS.getEntries().forEach(entry -> output.accept(new ItemStack(entry.get())));
                            })
                            .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_TABS.register(modEventBus);
    }
}
