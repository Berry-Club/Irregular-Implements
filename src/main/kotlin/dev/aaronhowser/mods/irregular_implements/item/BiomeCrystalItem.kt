package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBiomeTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.GrassColor
import net.minecraft.world.level.biome.Biome

class BiomeCrystalItem : Item(
	Properties()
		.rarity(Rarity.UNCOMMON)
) {

	override fun appendHoverText(
		stack: ItemStack,
		context: TooltipContext,
		tooltipComponents: MutableList<Component>,
		tooltipFlag: TooltipFlag
	) {
		val biomeHolder = stack.get(ModDataComponents.BIOME) ?: return

		val component = OtherUtil.getBiomeComponent(biomeHolder)

		tooltipComponents.add(component)
	}

	companion object {
		fun getAllCrystals(registries: HolderLookup.Provider): List<ItemStack> {
			return registries
				.lookupOrThrow(Registries.BIOME)
				.listElements()
				.toList()
				.mapNotNull { if (it.`is`(ModBiomeTagsProvider.BIOME_CRYSTAL_BLACKLIST)) null else getCrystal(it) }
		}

		fun getCrystal(biomeHolder: Holder<Biome>): ItemStack {
			val stack = ModItems.BIOME_CRYSTAL.toStack()
			stack.set(ModDataComponents.BIOME, biomeHolder)
			return stack
		}

		fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
			val foliageColor = stack.get(ModDataComponents.BIOME)?.value()?.foliageColor ?: GrassColor.getDefaultColor()

			return (foliageColor or 0xFF000000.toInt())
		}
	}

}