package dev.aaronhowser.mods.irregular_implements.datagen.loot

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import java.util.concurrent.CompletableFuture

class ModLootTableProvider(
    output: PackOutput,
    registries: CompletableFuture<HolderLookup.Provider>
) : LootTableProvider(output, setOf(), subProviders, registries) {

    companion object {

        val subProviders = listOf(
            SubProviderEntry(
                ::ModBlockLootTablesSubProvider,
                LootContextParamSets.BLOCK
            ),
            SubProviderEntry(
                ::ModChestLootSubprovider,
                LootContextParamSets.CHEST
            ),
            SubProviderEntry(
                ::ModMobLootSubprovider,
                LootContextParamSets.ENTITY
            )
        )

    }

}