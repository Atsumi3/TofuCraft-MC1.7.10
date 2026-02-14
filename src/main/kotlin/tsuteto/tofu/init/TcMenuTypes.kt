package tsuteto.tofu.init

import net.minecraft.core.registries.Registries
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.network.IContainerFactory
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import tsuteto.tofu.TofuCraftMod
import tsuteto.tofu.menu.*

object TcMenuTypes {
    @JvmField
    val MENU_TYPES: DeferredRegister<MenuType<*>> =
        DeferredRegister.create(Registries.MENU, TofuCraftMod.MOD_ID)

    @JvmField
    val SALT_FURNACE: DeferredHolder<MenuType<*>, MenuType<SaltFurnaceMenu>> =
        registerMenu("salt_furnace", ::SaltFurnaceMenu)

    @JvmField
    val TF_STORAGE: DeferredHolder<MenuType<*>, MenuType<TfStorageMenu>> =
        registerMenu("tf_storage", ::TfStorageMenu)

    @JvmField
    val TF_CONDENSER: DeferredHolder<MenuType<*>, MenuType<TfCondenserMenu>> =
        registerMenu("tf_condenser", ::TfCondenserMenu)

    @JvmField
    val TF_OVEN: DeferredHolder<MenuType<*>, MenuType<TfOvenMenu>> =
        registerMenu("tf_oven", ::TfOvenMenu)

    @JvmField
    val TF_REFORMER: DeferredHolder<MenuType<*>, MenuType<TfReformerMenu>> =
        registerMenu("tf_reformer", ::TfReformerMenu)

    @JvmField
    val TF_SATURATOR: DeferredHolder<MenuType<*>, MenuType<TfSaturatorMenu>> =
        registerMenu("tf_saturator", ::TfSaturatorMenu)

    private fun <T : AbstractContainerMenu> registerMenu(
        name: String, factory: IContainerFactory<T>
    ): DeferredHolder<MenuType<*>, MenuType<T>> =
        MENU_TYPES.register(name) { IMenuTypeExtension.create(factory) }

    @JvmStatic
    fun register(modEventBus: IEventBus) {
        MENU_TYPES.register(modEventBus)
    }
}
