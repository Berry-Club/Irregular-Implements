package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.aaron.AaronExtensions.asIngredient
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.item.*
import net.neoforged.neoforge.common.SimpleTier
import net.neoforged.neoforge.registries.DeferredItem

object ModToolItems {

	val SPECTRE_TIER = SimpleTier(
		Tiers.DIAMOND.incorrectBlocksForDrops,
		2000,
		Tiers.DIAMOND.speed,
		Tiers.DIAMOND.attackDamageBonus,
		Tiers.GOLD.enchantmentValue
	) { ModItems.SPECTRE_INGOT.asIngredient() }

	private val BLOCK_RANGE_INCREASE_RL = OtherUtil.modResource("block_range_increase")
	private val ENTITY_RANGE_INCREASE_RL = OtherUtil.modResource("entity_range_increase")

	fun registerSword(
		id: String,
		tier: Tier,
		properties: Item.Properties
	): DeferredItem<SwordItem> {
		return ModItems.ITEM_REGISTRY.registerItem(id) { SwordItem(tier, properties) }
	}

	fun registerPickaxe(
		id: String,
		tier: Tier,
		properties: Item.Properties
	): DeferredItem<PickaxeItem> {
		return ModItems.ITEM_REGISTRY.registerItem(id) { PickaxeItem(tier, properties) }
	}

	fun registerShovel(
		id: String,
		tier: Tier,
		properties: Item.Properties
	): DeferredItem<ShovelItem> {
		return ModItems.ITEM_REGISTRY.registerItem(id) { ShovelItem(tier, properties) }
	}

	fun registerAxe(
		id: String,
		tier: Tier,
		properties: Item.Properties
	): DeferredItem<AxeItem> {
		return ModItems.ITEM_REGISTRY.registerItem(id) { AxeItem(tier, properties) }
	}

	val SPECTRE_SWORD_DEFAULT_PROPERTIES: Item.Properties =
		Item
			.Properties()
			.attributes(
				SwordItem
					.createAttributes(SPECTRE_TIER, 3, -2.4f)
					.withModifierAdded(
						Attributes.ENTITY_INTERACTION_RANGE,
						AttributeModifier(
							ENTITY_RANGE_INCREASE_RL,
							3.0,
							AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.HAND
					)
			)

	val SPECTRE_PICKAXE_DEFAULT_PROPERTIES: Item.Properties =
		Item
			.Properties()
			.attributes(
				PickaxeItem
					.createAttributes(SPECTRE_TIER, 1f, -2.8f)
					.withModifierAdded(
						Attributes.BLOCK_INTERACTION_RANGE,
						AttributeModifier(
							BLOCK_RANGE_INCREASE_RL,
							3.0,
							AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.HAND
					)
			)

	val SPECTRE_SHOVEL_DEFAULT_PROPERTIES: Item.Properties =
		Item
			.Properties()
			.attributes(
				ShovelItem
					.createAttributes(SPECTRE_TIER, 1.5f, -3.0f)
					.withModifierAdded(
						Attributes.BLOCK_INTERACTION_RANGE,
						AttributeModifier(
							BLOCK_RANGE_INCREASE_RL,
							3.0,
							AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.HAND
					)
			)

	val SPECTRE_AXE_DEFAULT_PROPERTIES: Item.Properties =
		Item
			.Properties()
			.attributes(
				AxeItem
					.createAttributes(SPECTRE_TIER, 6.0f, -3.0f)
					.withModifierAdded(
						Attributes.BLOCK_INTERACTION_RANGE,
						AttributeModifier(
							BLOCK_RANGE_INCREASE_RL,
							3.0,
							AttributeModifier.Operation.ADD_VALUE
						),
						EquipmentSlotGroup.HAND
					)
			)

}