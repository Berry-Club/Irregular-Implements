package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.menu.IronDropperMenu
import dev.aaronhowser.mods.irregular_implements.menu.IronDropperScreen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModMenuTypes {

    val MENU_TYPE_REGISTRY: DeferredRegister<MenuType<*>> =
        DeferredRegister.create(BuiltInRegistries.MENU, IrregularImplements.ID)

    val IRON_DROPPER: DeferredHolder<MenuType<*>, MenuType<IronDropperMenu>> =
        MENU_TYPE_REGISTRY.register("iron_dropper", Supplier {
            MenuType(::IronDropperMenu, FeatureFlags.DEFAULT_FLAGS)
        })

    fun registerScreens(event: RegisterMenuScreensEvent) {
        event.register(IRON_DROPPER.get(), ::IronDropperScreen)
    }

}