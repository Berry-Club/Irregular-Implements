package dev.aaronhowser.mods.irregular_implements.datagen

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModBiomeModifiers
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModConfiguredFeatures
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModPlacedFeatures
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.core.registries.Registries
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import net.neoforged.neoforge.registries.NeoForgeRegistries
import java.util.concurrent.CompletableFuture

class ModDataPackProvider(
	output: PackOutput,
	registries: CompletableFuture<HolderLookup.Provider>
) : DatapackBuiltinEntriesProvider(output, registries, BUILDER, setOf(IrregularImplements.ID)) {

	companion object {
		val BUILDER: RegistrySetBuilder = RegistrySetBuilder()
			.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
			.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrap)
			.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap)
	}

}