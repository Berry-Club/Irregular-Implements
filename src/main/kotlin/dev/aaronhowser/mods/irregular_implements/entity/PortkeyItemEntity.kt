package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.core.component.DataComponents
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

class PortkeyItemEntity(
	entityType: EntityType<PortkeyItemEntity>,
	level: Level
) : ItemEntity(entityType, level) {

	constructor(itemEntity: ItemEntity) : this(
		ModEntityTypes.PORTKEY_ITEM.get(),
		itemEntity.level()
	) {
		this.item = itemEntity.item
		this.setPos(itemEntity.position())
		this.deltaMovement = itemEntity.deltaMovement
	}

	init {
		lifespan = Int.MAX_VALUE
		setPickUpDelay(40)
	}

	override fun tick() {
		super.tick()

		if (age == 20 * 5) {
			level().playSound(
				null,
				blockPosition(),
				SoundEvents.BELL_BLOCK,
				soundSource,
				1f,
				0.25f
			)

			this.item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false)
		}
	}

	private fun willTeleport(): Boolean {
		if (age <= PORTKEY_PICKUP_DELAY) return false
		val stack = super.getItem()
		return stack.has(ModDataComponents.LOCATION)
	}

	override fun playerTouch(entity: Player) {
		if (!willTeleport()) {
			super.playerTouch(entity)
			return
		}

		val stack = super.getItem()
		val locationComponent = stack.get(ModDataComponents.LOCATION) ?: return

		val level = entity.level()

		if (level.dimension() != locationComponent.dimension) return

		val teleportLocation = locationComponent.blockPos.bottomCenter

		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource)
		entity.teleportTo(teleportLocation.x, teleportLocation.y, teleportLocation.z)
		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource)

		discard()
	}

	companion object {
		const val PORTKEY_PICKUP_DELAY = 20 * 5
	}

}