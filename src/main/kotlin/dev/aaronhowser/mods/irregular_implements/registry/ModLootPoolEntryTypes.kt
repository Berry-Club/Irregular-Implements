package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.loot.BiomeCrystalLootEntry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

object ModLootPoolEntryTypes {

	val LOOT_POOL_ENTRY_TYPE_REGISTRY: DeferredRegister<LootPoolEntryType> =
		DeferredRegister.create(BuiltInRegistries.LOOT_POOL_ENTRY_TYPE, IrregularImplements.MOD_ID)

	val BIOME_CRYSTAL: DeferredHolder<LootPoolEntryType, LootPoolEntryType> =
		LOOT_POOL_ENTRY_TYPE_REGISTRY.register("biome_crystal", Supplier { LootPoolEntryType(BiomeCrystalLootEntry.CODEC) })

}