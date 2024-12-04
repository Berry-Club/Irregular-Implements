package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.BiomeCrystalItem
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
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
            .icon { (ModItems.ITEM_REGISTRY.entries.random() as DeferredItem).toStack() }
            .displayItems { displayContext: CreativeModeTab.ItemDisplayParameters, output: CreativeModeTab.Output ->
                val itemsToSkip = setOf(
                    ModItems.BIOME_CRYSTAL.get()
                )

                val regularItems: Set<DeferredHolder<Item, out Item>> = ModItems.ITEM_REGISTRY.entries.toSet()

                output.acceptAll(
                    (regularItems - itemsToSkip).map { (it as DeferredItem<*>).toStack() }
                )

                output.acceptAll(BiomeCrystalItem.getAllCrystals(displayContext.holders))
            }
            .build()
    })

}