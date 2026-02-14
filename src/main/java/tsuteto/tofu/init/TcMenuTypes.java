package tsuteto.tofu.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import tsuteto.tofu.TofuCraftMod;
import tsuteto.tofu.menu.*;

public class TcMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(Registries.MENU, TofuCraftMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<SaltFurnaceMenu>> SALT_FURNACE =
            registerMenu("salt_furnace", SaltFurnaceMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<TfStorageMenu>> TF_STORAGE =
            registerMenu("tf_storage", TfStorageMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<TfCondenserMenu>> TF_CONDENSER =
            registerMenu("tf_condenser", TfCondenserMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<TfOvenMenu>> TF_OVEN =
            registerMenu("tf_oven", TfOvenMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<TfReformerMenu>> TF_REFORMER =
            registerMenu("tf_reformer", TfReformerMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<TfSaturatorMenu>> TF_SATURATOR =
            registerMenu("tf_saturator", TfSaturatorMenu::new);

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> registerMenu(
            String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
