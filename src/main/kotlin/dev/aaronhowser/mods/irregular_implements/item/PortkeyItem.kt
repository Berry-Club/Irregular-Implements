package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.PortkeyItemEntity
import dev.aaronhowser.mods.irregular_implements.item.component.LocationDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.neoforged.neoforge.common.util.TriState
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent

//TODO: Ability to hide it as other items
class PortkeyItem(properties: Properties) : Item(properties) {

	override fun useOn(context: UseOnContext): InteractionResult {
		val level = context.level
		val clickedPos = context.clickedPos
		val clickedFace = context.clickedFace

		val posToTeleportTo = if (level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty) {
			clickedPos
		} else {
			clickedPos.relative(clickedFace)
		}

		val usedStack = context.itemInHand

		usedStack.set(
			ModDataComponents.LOCATION,
			LocationDataComponent(level, posToTeleportTo)
		)

		return InteractionResult.SUCCESS
	}


	override fun isFoil(stack: ItemStack): Boolean {
		return stack.has(ModDataComponents.LOCATION)
	}

	//TODO: Tooltip

	override fun hasCustomEntity(stack: ItemStack): Boolean = true
	override fun createEntity(level: Level, location: Entity, stack: ItemStack): Entity {
		return PortkeyItemEntity(level, location.x, location.y, location.z, stack)
	}

	companion object {

		val DEFAULT_PROPERTIES: Properties = Properties().stacksTo(1)

		fun pickUpPortkey(event: ItemEntityPickupEvent.Pre) {
			if (event.canPickup().isFalse) return

			val itemEntity = event.itemEntity
			val itemStack = itemEntity.item

			if (!itemStack.`is`(ModItems.PORTKEY)) return

			val locationComponent = itemStack.get(ModDataComponents.LOCATION) ?: return
			if (!itemStack.has(ModDataComponents.IS_ENABLED)) return

			val player = event.player
			val level = player.level() as? ServerLevel ?: return

			if (level.dimension() != locationComponent.dimension) return

			val teleportLocation = locationComponent.blockPos.bottomCenter

			player.teleportTo(
				teleportLocation.x,
				teleportLocation.y,
				teleportLocation.z,
			)

			level.playSound(
				null,
				player.blockPosition(),
				SoundEvents.PLAYER_TELEPORT,
				player.soundSource,
			)

			event.setCanPickup(TriState.FALSE)
			itemEntity.discard()
		}
	}

}