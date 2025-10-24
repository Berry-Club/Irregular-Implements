package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.loot.BiomeCrystalLootEntry
import net.minecraft.core.registries.Registries
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.RegistryObject
import java.util.function.Supplier

object ModLootPoolEntryTypes {

	val LOOT_POOL_ENTRY_TYPE_REGISTRY: DeferredRegister<LootPoolEntryType> =
		DeferredRegister.create(Registries.LOOT_POOL_ENTRY_TYPE, IrregularImplements.ID)

	val BIOME_CRYSTAL: RegistryObject<LootPoolEntryType> =
		LOOT_POOL_ENTRY_TYPE_REGISTRY.register("biome_crystal", Supplier { LootPoolEntryType(BiomeCrystalLootEntry.CODEC) })

}