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

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    bus = EventBusSubscriber.Bus.MOD
)
object ModDataGen {

    @SubscribeEvent
    fun onGatherData(event: GatherDataEvent) {
        val generator: DataGenerator = event.generator
        val output: PackOutput = generator.packOutput
        val existingFileHelper: ExistingFileHelper = event.existingFileHelper
        val lookupProvider: CompletableFuture<HolderLookup.Provider> = event.lookupProvider

        val itemModelProvider = generator.addProvider(
            event.includeClient(),
            ModItemModelProvider(output, existingFileHelper)
        )
        val blockModelProvider = generator.addProvider(
            event.includeClient(),
            ModBlockStateProvider(output, existingFileHelper)
        )

        val recipeProvider = generator.addProvider(
            event.includeServer(),
            ModRecipeProvider(output, lookupProvider)
        )

        val lootTableProvider = generator.addProvider(
            event.includeServer(),
            ModLootTableProvider(output, lookupProvider)
        )

        val globalLootModifierProvider = generator.addProvider(
            event.includeServer(),
            ModGlobalLootModifierProvider(output, lookupProvider)
        )

        val soundDefinitionsProvider = generator.addProvider(
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
        val itemTagProvider = generator.addProvider(
            event.includeServer(),
            ModItemTagsProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
        )
        val fluidTagProvider = generator.addProvider(
            event.includeServer(),
            ModFluidTagsProvider(output, lookupProvider, existingFileHelper)
        )
        val entityTypeTagProvider = generator.addProvider(
            event.includeServer(),
            ModEntityTypeTagsProvider(output, lookupProvider, existingFileHelper)
        )
        val enchantmentTagProvider = generator.addProvider(
            event.includeServer(),
            ModEnchantmentTagsProvider(output, dataPackProvider.registryProvider, existingFileHelper)
        )
        val biomeTagProvider = generator.addProvider(
            event.includeServer(),
            ModBiomeTagsProvider(output, lookupProvider, existingFileHelper)
        )

        val curioProvider = generator.addProvider(
            event.includeServer(),
            ModCurioProvider(output, existingFileHelper, lookupProvider)
        )

        val languageProvider = ModLanguageProvider(output)

        val modonomiconBookProvider = generator.addProvider(
            event.includeClient(),
            NeoBookProvider.of(
                event, lookupProvider, ModModonomiconProvider(languageProvider::add)
            )
        )

        generator.addProvider(event.includeClient(), languageProvider)


    }

}