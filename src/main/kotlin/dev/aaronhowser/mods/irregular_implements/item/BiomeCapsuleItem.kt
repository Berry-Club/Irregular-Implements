package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.item.component.BiomePointsDataComponent
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome

class BiomeCapsuleItem(properties: Properties) : Item(properties) {

	override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
		val onBlockPos = entity.getOnPos(0.999f)

		if (entity.level().getBlockState(onBlockPos).isAir) return super.onEntityItemUpdate(stack, entity)

		val biome = entity.level().getBiome(onBlockPos)
		val component = BiomePointsDataComponent.getFromStack(stack) ?: BiomePointsDataComponent(biome, 0)

		if (component.biome != biome) return super.onEntityItemUpdate(stack, entity)

		stack.tag?.put(
			BiomePointsDataComponent.NAME,
			component.withMorePoints(1).save()
		)

		return super.onEntityItemUpdate(stack, entity)
	}

	//TODO: Improve this
	override fun appendHoverText(pStack: ItemStack, pLevel: Level?, pTooltipComponents: MutableList<Component>, pIsAdvanced: TooltipFlag) {
		val component = BiomePointsDataComponent.getFromStack(pStack) ?: return

		val biomeComponent = OtherUtil.getBiomeComponent(component.biome)
		val biomePoints = component.points

		pTooltipComponents.add(biomeComponent)
		pTooltipComponents.add(biomePoints.toString().toComponent())
	}

	companion object {
		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun getItemColor(stack: ItemStack, tintIndex: Int): Int {
			val component = BiomePointsDataComponent.getFromStack(stack)
			val biome = component?.biome

			val foliageColor = biome?.value()?.foliageColor ?: 0xFFFFFFFF.toInt()

			return (foliageColor or 0xFF000000.toInt())
		}

		fun getFirstNonEmptyCapsule(playerInventory: Inventory, excludeBiome: Holder<Biome>? = null): ItemStack? {
			val stacks = playerInventory.items

			for (stack in stacks) {
				val component = BiomePointsDataComponent.getFromStack(stack) ?: continue
				if (component.points <= 0 || component.biome == excludeBiome) continue
				return stack
			}

			return null
		}

		fun getBiomeToPaint(player: Player): Holder<Biome>? {
			val stack = getFirstNonEmptyCapsule(player.inventory) ?: return null
			val component = BiomePointsDataComponent.getFromStack(stack) ?: return null
			return component.biome
		}

		fun getFirstCapsuleWithBiome(playerInventory: Inventory, biome: Holder<Biome>): ItemStack? {
			val stacks = playerInventory.items

			for (stack in stacks) {
				val component = BiomePointsDataComponent.getFromStack(stack) ?: continue
				if (component.points <= 0 || component.biome != biome) continue
				return stack
			}

			return null
		}
	}

}