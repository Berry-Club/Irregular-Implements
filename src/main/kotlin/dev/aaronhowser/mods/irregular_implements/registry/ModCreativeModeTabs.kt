package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isHolder
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.withComponent
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModItemLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.item.BiomeCrystalItem
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.item.WeatherEggItem
import dev.aaronhowser.mods.irregular_implements.item.WhiteStoneItem
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModCreativeModeTabs {

	val TABS_REGISTRY: DeferredRegister<CreativeModeTab> =
		DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, IrregularImplements.MOD_ID)

	val MOD_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = TABS_REGISTRY.register("creative_tab", Supplier {
		CreativeModeTab.builder()
			.title(ModItemLang.CREATIVE_TAB.toComponent())
			.icon { (ModItems.ITEM_REGISTRY.entries.random() as DeferredItem).toStack() }
			.displayItems { displayContext: CreativeModeTab.ItemDisplayParameters, output: CreativeModeTab.Output ->

				val regularItems = mutableListOf<Item>()
				val blockItems = mutableListOf<BlockItem>()

				for (deferred in ModItems.ITEM_REGISTRY.entries) {
					if (deferred.isHolder(ModItemTagsProvider.NOT_YET_IMPLEMENTED)) continue

					val item = deferred.get()
					if (item is BlockItem) {
						blockItems.add(item)
					} else {
						regularItems.add(item)
					}
				}

				for (item in regularItems) {
					if (item == ModItems.WEATHER_EGG.get()) {
						output.acceptAll(WeatherEggItem.Weather.getAllStacks())
						continue
					}

					if (item == ModItems.DIVINING_ROD.get()) {
						output.acceptAll(DiviningRodItem.getAllOreRods())
						continue
					}

					if (item == ModItems.WHITE_STONE.get()) {
						output.accept(item)
						output.accept(
							item.withComponent(
								ModDataComponents.CHARGE.get(),
								WhiteStoneItem.MAX_CHARGE
							)
						)
					}

					if (item == ModItems.BIOME_CRYSTAL.get()) {
						continue
					}

					output.accept(item)
				}

				for (blockItem in blockItems) {
					output.accept(blockItem)
				}

				output.acceptAll(BiomeCrystalItem.getAllCrystals(displayContext.holders))

			}
			.build()
	})

}