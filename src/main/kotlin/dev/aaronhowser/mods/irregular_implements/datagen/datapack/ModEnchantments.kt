package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMiscLang
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.HolderSet
import net.minecraft.core.RegistryAccess
import net.minecraft.core.component.DataComponentMap
import net.minecraft.core.registries.Registries
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.item.enchantment.Enchantment
import java.util.*

object ModEnchantments {

	//FIXME: The item entity renders as moving towards the local player, rather than the player who's picking it up
	val MAGNETIC: ResourceKey<Enchantment> = ResourceKey.create(
		Registries.ENCHANTMENT,
		OtherUtil.modResource("magnetic")
	)

	fun getHolder(enchantmentRk: ResourceKey<Enchantment>, registryAccess: RegistryAccess): Holder.Reference<Enchantment> {
		return registryAccess
			.registryOrThrow(Registries.ENCHANTMENT)
			.getHolderOrThrow(enchantmentRk)
	}

	fun bootstrap(context: BootstrapContext<Enchantment>) {
		context.register(
			MAGNETIC,
			Enchantment(
				ModMiscLang.MAGNETIC_NAME.toComponent(),
				Enchantment.EnchantmentDefinition(
					context.lookup(Registries.ITEM).getOrThrow(ModItemTagsProvider.ENCHANTABLE_MAGNETIC),
					Optional.empty(),
					1,
					1,
					Enchantment.Cost(15, 0),
					Enchantment.Cost(65, 0),
					2,
					listOf(EquipmentSlotGroup.HAND)
				),
				HolderSet.empty(),
				DataComponentMap
					.builder()
					.build()
			)
		)
	}

}