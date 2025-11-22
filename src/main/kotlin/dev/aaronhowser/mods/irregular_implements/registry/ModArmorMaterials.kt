package dev.aaronhowser.mods.irregular_implements.registry

import dev.aaronhowser.mods.aaron.registry.AaronArmorMaterialRegistry
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ArmorMaterials
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister

object ModArmorMaterials : AaronArmorMaterialRegistry() {

	val ARMOR_MATERIAL_REGISTRY: DeferredRegister<ArmorMaterial> =
		DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, IrregularImplements.MOD_ID)

	override fun getArmorMaterialRegistry(): DeferredRegister<ArmorMaterial> = ARMOR_MATERIAL_REGISTRY

	val WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		Builder("water_walking")
			.repair(Tags.Items.INGOTS_IRON)
			.armor(1)
			.register()

	val OBSIDIAN_WATER_WALKING: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		Builder("obsidian_water_walking")
			.repair(Tags.Items.INGOTS_IRON)
			.armor(1)
			.register()

	val LAVA_WADERS: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		Builder("lava_waders")
			.repair(Tags.Items.INGOTS_IRON)
			.armor(1)
			.register()

	val MAGIC: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		Builder("magic")
			.repair(Tags.Items.LEATHERS)
			.armor(1)
			.register()

	val SPECTRE: DeferredHolder<ArmorMaterial, ArmorMaterial> =
		Builder("spectre")
			.repair(ModItems.SPECTRE_INGOT)
			.boot(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.BOOTS))
			.leg(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.LEGGINGS))
			.chestplate(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.CHESTPLATE))
			.helmet(ArmorMaterials.DIAMOND.value().getDefense(ArmorItem.Type.HELMET))
			.enchantValue(ArmorMaterials.GOLD.value().enchantmentValue)
			.register()

}