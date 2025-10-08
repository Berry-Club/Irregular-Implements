package dev.aaronhowser.mods.irregular_implements.datagen.loot

import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.HolderLookup
import net.minecraft.data.loot.EntityLootSubProvider
import net.minecraft.world.entity.EntityType
import net.minecraft.world.flag.FeatureFlags
import net.minecraft.world.item.Items
import net.minecraft.world.level.storage.loot.LootPool
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator
import java.util.stream.Stream

class ModMobLootSubprovider(
	registries: HolderLookup.Provider
) : EntityLootSubProvider(FeatureFlags.REGISTRY.allFlags(), registries) {

	override fun generate() {
		this.add(
			ModEntityTypes.GOLDEN_CHICKEN.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0f))
						.add(
							LootItem.lootTableItem(Items.FEATHER)
								.apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0f, 2.0f)))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0f, 1.0f)))
						)
				)
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1.0f))
						.add(
							LootItem.lootTableItem(Items.CHICKEN)
								.apply(SmeltItemFunction.smelted().`when`(this.shouldSmeltLoot()))
								.apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0f, 1.0f)))
						)
				)
		)

		this.add(
			ModEntityTypes.SPIRIT.get(),
			LootTable.lootTable()
				.withPool(
					LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1f))
						.`when`(LootItemKilledByPlayerCondition.killedByPlayer())
						.add(
							LootItem.lootTableItem(ModItems.ECTOPLASM)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
						)
						.add(
							LootItem.lootTableItem(ModItems.ECTOPLASM)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
								.`when`(LootItemRandomChanceCondition.randomChance(0.2f))
						)
				)
		)

	}

	@Suppress("UNCHECKED_CAST")
	override fun getKnownEntityTypes(): Stream<EntityType<*>> {
		return listOf(
			ModEntityTypes.GOLDEN_CHICKEN.get(),
			ModEntityTypes.SPIRIT.get()
		).stream() as Stream<EntityType<*>>
	}

}