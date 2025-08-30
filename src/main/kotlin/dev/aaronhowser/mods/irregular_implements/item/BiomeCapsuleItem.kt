package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.BiomePointsDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.biome.Biome

class BiomeCapsuleItem(properties: Properties) : Item(properties) {

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
		val onBlockPos = entity.blockPosBelowThatAffectsMyMovement

		if (entity.level().getBlockState(onBlockPos).isAir) return super.onEntityItemUpdate(stack, entity)

		val biome = entity.level().getBiome(onBlockPos)
		val component = stack.get(ModDataComponents.BIOME_POINTS) ?: BiomePointsDataComponent(biome, 0)

		if (component.biome != biome) return super.onEntityItemUpdate(stack, entity)

		stack.set(
			ModDataComponents.BIOME_POINTS,
			component.withMorePoints(1)
		)

		return super.onEntityItemUpdate(stack, entity)
	}

	//TODO: Improve this
	override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
		val biomePointsComponent = stack.get(ModDataComponents.BIOME_POINTS) ?: return

		val biomeComponent = OtherUtil.getBiomeComponent(biomePointsComponent.biome)
		val biomePoints = biomePointsComponent.points

		tooltipComponents.add(biomeComponent)
		tooltipComponents.add(biomePoints.toString().toComponent())
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
			val foliageColor = stack.get(ModDataComponents.BIOME)?.value()?.foliageColor ?: 0xFFFFFFFF.toInt()

			return (foliageColor or 0xFF000000.toInt())
		}

		fun getFirstNonEmptyCapsule(playerInventory: Inventory, excludeBiome: Holder<Biome>? = null): ItemStack? {
			val stacks = playerInventory.items

			for (stack in stacks) {
				val component = stack.get(ModDataComponents.BIOME_POINTS) ?: continue
				if (component.points <= 0 || component.biome == excludeBiome) continue
				return stack
			}

			return null
		}

		fun getBiomeToPaint(player: Player): Holder<Biome>? {
			return getFirstNonEmptyCapsule(player.inventory)
				?.get(ModDataComponents.BIOME_POINTS)
				?.biome
		}

		fun getFirstCapsuleWithBiome(playerInventory: Inventory, biome: Holder<Biome>): ItemStack? {
			val stacks = playerInventory.items

			for (stack in stacks) {
				val component = stack.get(ModDataComponents.BIOME_POINTS) ?: continue
				if (component.points <= 0 || component.biome != biome) continue
				return stack
			}

			return null
		}
	}

}