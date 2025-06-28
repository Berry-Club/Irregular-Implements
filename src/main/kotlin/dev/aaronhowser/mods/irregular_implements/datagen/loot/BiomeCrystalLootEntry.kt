package dev.aaronhowser.mods.irregular_implements.datagen.loot

import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.aaronhowser.mods.irregular_implements.item.BiomeCrystalItem
import dev.aaronhowser.mods.irregular_implements.registry.ModLootPoolEntryTypes
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.storage.loot.LootContext
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer
import net.minecraft.world.level.storage.loot.functions.LootItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition
import java.util.function.Consumer

class BiomeCrystalLootEntry(
	weight: Int,
	quality: Int,
	conditions: MutableList<LootItemCondition>,
	functions: MutableList<LootItemFunction>
) : LootPoolSingletonContainer(weight, quality, conditions, functions) {

	companion object {
		fun get(): Builder<*> {
			return simpleBuilder(::BiomeCrystalLootEntry)
		}

		val CODEC: MapCodec<BiomeCrystalLootEntry> =
			RecordCodecBuilder.mapCodec { instance ->
				singletonFields(instance)
					.apply(instance, ::BiomeCrystalLootEntry)
			}

	}

	override fun getType(): LootPoolEntryType {
		return ModLootPoolEntryTypes.BIOME_CRYSTAL.get()
	}

	override fun createItemStack(stackConsumer: Consumer<ItemStack>, lootContext: LootContext) {
		val allCrystals = BiomeCrystalItem.getAllCrystals(lootContext.level.registryAccess())
		val randomStack = allCrystals[lootContext.random.nextInt(allCrystals.size)]

		stackConsumer.accept(randomStack)
	}
}