package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod

object TcCreativeTabs {
    @JvmField
    val CREATIVE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TofuCraftMod.MOD_ID)

    @JvmField
    val TOFUCRAFT_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> =
        CREATIVE_TABS.register("tofucraft_tab") {
            CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.${TofuCraftMod.MOD_ID}"))
                .icon { ItemStack(TcBlocks.TOFU_MOMEN.get()) }
                .displayItems { parameters, output ->
                    // Blocks
                    TcBlocks.BLOCKS.entries.forEach { entry -> output.accept(ItemStack(entry.get())) }
                    // Items (excluding block items already added via blocks)
                    TcItems.ITEMS.entries.forEach { entry -> output.accept(ItemStack(entry.get())) }
                }
                .build()
        }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        CREATIVE_TABS.register(modEventBus)
    }
}
