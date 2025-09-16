package dev.aaronhowser.mods.irregular_implements.datagen

import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.loot.ModGlobalLootModifierProvider
import dev.aaronhowser.mods.irregular_implements.datagen.loot.ModLootTableProvider
import dev.aaronhowser.mods.irregular_implements.datagen.model.ModBlockStateProvider
import dev.aaronhowser.mods.irregular_implements.datagen.model.ModItemModelProvider
import dev.aaronhowser.mods.irregular_implements.datagen.modonomicon.ModModonomiconProvider
import dev.aaronhowser.mods.irregular_implements.datagen.tag.*
import net.minecraft.core.HolderLookup
import net.minecraft.data.DataGenerator
import net.minecraft.data.PackOutput
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent
import java.util.concurrent.CompletableFuture

@EventBusSubscriber(modid = IrregularImplements.ID)
object ModDataGen {

	@SubscribeEvent
	fun onGatherData(event: GatherDataEvent) {
		val generator: DataGenerator = event.generator
		val output: PackOutput = generator.packOutput
		val existingFileHelper: ExistingFileHelper = event.existingFileHelper
		val lookupProvider: CompletableFuture<HolderLookup.Provider> = event.lookupProvider

		generator.addProvider(
			event.includeClient(),
			ModItemModelProvider(output, existingFileHelper)
		)
		generator.addProvider(
			event.includeClient(),
			ModBlockStateProvider(output, existingFileHelper)
		)

		generator.addProvider(
			event.includeServer(),
			ModRecipeProvider(output, lookupProvider)
		)

		generator.addProvider(
			event.includeServer(),
			ModLootTableProvider(output, lookupProvider)
		)

		generator.addProvider(
			event.includeServer(),
			ModGlobalLootModifierProvider(output, lookupProvider)
		)

		generator.addProvider(
			event.includeClient(),
			ModSoundDefinitionsProvider(output, existingFileHelper)
		)

		val dataPackProvider = generator.addProvider(
			event.includeServer(),
			ModDataPackProvider(output, lookupProvider)
		)

		val blockTagProvider = generator.addProvider(
			event.includeServer(),
			ModBlockTagsProvider(output, lookupProvider, existingFileHelper)
		)
		generator.addProvider(
			event.includeServer(),
			ModItemTagsProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
		)
		generator.addProvider(
			event.includeServer(),
			ModFluidTagsProvider(output, lookupProvider, existingFileHelper)
		)
		generator.addProvider(
			event.includeServer(),
			ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper)
		)
		generator.addProvider(
			event.includeServer(),
			ModEnchantmentTagsProvider(output, dataPackProvider.registryProvider, existingFileHelper)
		)
		generator.addProvider(
			event.includeServer(),
			ModBiomeTagsProvider(output, lookupProvider, existingFileHelper)
		)

		generator.addProvider(
			event.includeServer(),
			ModCurioProvider(output, existingFileHelper, lookupProvider)
		)

		val languageProvider = ModLanguageProvider(output)

		generator.addProvider(
			event.includeClient(),
			NeoBookProvider.of(
				event, lookupProvider, ModModonomiconProvider(languageProvider::add)
			)
		)

		generator.addProvider(event.includeClient(), languageProvider)

		generator.addProvider(
			event.includeClient(),
			ModParticleDescriptionProvider(output, existingFileHelper)
		)

	}

}