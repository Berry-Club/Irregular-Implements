package dev.aaronhowser.mods.irregular_implements.registries

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTab
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModCreativeModeTabs {

    val TABS_REGISTRY: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, IrregularImplements.ID)

    val MOD_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = TABS_REGISTRY.register("creative_tab", Supplier {
        CreativeModeTab.builder()
            .title(ModLanguageProvider.Items.CREATIVE_TAB.toComponent())
            .icon { ModItems.SPECTRE_PICKAXE.toStack() }
            .displayItems { displayContext: CreativeModeTab.ItemDisplayParameters, output: CreativeModeTab.Output ->
                val regularItems =
                    ModItems.ITEM_REGISTRY.entries

                val itemsToDisplay = buildList {
                    addAll(regularItems.map { (it as DeferredItem).toStack() })
                }

                output.acceptAll(itemsToDisplay)
            }
            .build()
    })

}