package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.datagen.ModRecipeProvider.Companion.asIngredient
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.TagKey
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.registries.DeferredRegister
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

// Inspired largely by Advent of Ascension's class
object ModArmorMaterials {

	val ARMOR_MATERIAL_REGISTRY: DeferredRegister<ArmorMaterial> =
		DeferredRegister.create(Registries.ARMOR_MATERIAL, IrregularImplements.ID)

	val WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		register(
			"water_walking",
			Builder()
				.repair(Tags.Items.INGOTS_IRON)
				.armor(1)
		)

	val OBSIDIAN_WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		register(
			"obsidian_water_walking",
			Builder()
				.repair(Tags.Items.INGOTS_IRON)
				.armor(1)
		)

	val LAVA_WADERS: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		register(
			"lava_waders",
			Builder()
				.repair(Tags.Items.INGOTS_IRON)
				.armor(1)
		)

	val MAGIC: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		register(
			"magic",
			Builder()
				.repair(Tags.Items.LEATHERS)
				.armor(1)
		)

	val SPECTRE: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		register(
			"spectre",
			Builder()
				.repair(ModItems.SPECTRE_INGOT)
				.boot(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.BOOTS))
				.leg(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.LEGGINGS))
				.chestplate(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.CHESTPLATE))
				.helmet(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.HELMET))
				.enchantValue(ArmorMaterials.GOLD.value().enchantmentValue)
		)


	private fun register(id: String, builder: Builder): DeferredHolder<ArmorMaterial, ArmorMaterial> {
		val defaultLayer = ArmorMaterial.Layer(OtherUtil.modResource(id))

		return ARMOR_MATERIAL_REGISTRY.register(id, Supplier { builder.addLayer(defaultLayer).build() })
	}

	private class Builder {
		private var bootsArmour = 0
		private var legsArmour = 0
		private var chestplateArmour = 0
		private var helmetArmour = 0
		private var toughness = 0f
		private var knockbackResist = 0f
		private var enchantValue = 10
		private var equipSound: Holder<SoundEvent> = SoundEvents.ARMOR_EQUIP_GENERIC
		private var repairIngredient = Supplier { Ingredient.EMPTY }
		private val layers = mutableListOf<ArmorMaterial.Layer>()

		fun enchantValue(value: Int): Builder {
			enchantValue = value
			return this
		}

		fun boot(armorAmount: Int): Builder {
			bootsArmour = armorAmount
			return this
		}

		fun leg(armorAmount: Int): Builder {
			legsArmour = armorAmount
			return this
		}

		fun chestplate(armorAmount: Int): Builder {
			chestplateArmour = armorAmount
			return this
		}

		fun helmet(armorAmount: Int): Builder {
			helmetArmour = armorAmount
			return this
		}

		fun armor(armorAmount: Int): Builder {
			bootsArmour = armorAmount
			legsArmour = armorAmount
			chestplateArmour = armorAmount
			helmetArmour = armorAmount
			return this
		}

		fun repair(tag: TagKey<Item>): Builder {
			repairIngredient = Supplier { tag.asIngredient() }
			return this
		}

		fun repair(itemHolder: Holder<Item>): Builder {
			repairIngredient = Supplier { itemHolder.value().asIngredient() }
			return this
		}

		fun addLayer(layer: ArmorMaterial.Layer): Builder {
			layers.add(layer)
			return this
		}

		fun build(): ArmorMaterial {
			return ArmorMaterial(
				mapOf(
					ArmorItem.Type.BOOTS to bootsArmour,
					ArmorItem.Type.LEGGINGS to legsArmour,
					ArmorItem.Type.CHESTPLATE to chestplateArmour,
					ArmorItem.Type.HELMET to helmetArmour
				),
				enchantValue,
				equipSound,
				repairIngredient,
				layers,
				toughness,
				knockbackResist
			)
		}
	}

}