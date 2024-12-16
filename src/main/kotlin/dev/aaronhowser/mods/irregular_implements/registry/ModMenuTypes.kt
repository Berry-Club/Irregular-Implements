package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.menu.block_replacer.BlockReplacerMenu
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModMenuTypes {

    val MENU_TYPE_REGISTRY: DeferredRegister<MenuType<*>> =
        DeferredRegister.create(BuiltInRegistries.MENU, IrregularImplements.ID)

    val BLOCK_REPLACER: DeferredHolder<MenuType<*>, MenuType<BlockReplacerMenu>> =
        MENU_TYPE_REGISTRY.register("block_replacer", Supplier {
            IMenuTypeExtension.create { id, inv, buf ->
                BlockReplacerMenu(id, inv, buf)
            }
        })

}