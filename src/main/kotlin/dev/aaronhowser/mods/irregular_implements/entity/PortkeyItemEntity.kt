package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.aaron.AaronExtensions.isClientSide
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
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

		if (!isClientSide && age == 20 * 5) {
			level().playSound(
				null,
				blockPosition(),
				SoundEvents.BELL_BLOCK,
				soundSource,
				1f,
				0.25f
			)
		}
	}

	private fun shouldTeleport(): Boolean {
		if (age <= PORTKEY_PICKUP_DELAY) return false
		val stack = super.getItem()
		return stack.has(ModDataComponents.GLOBAL_POS)
	}

	override fun playerTouch(entity: Player) {
		if (!shouldTeleport()) {
			super.playerTouch(entity)
			return
		}

		val stack = super.getItem()
		val locationComponent = stack.get(ModDataComponents.GLOBAL_POS) ?: return

		val level = entity.level()

		if (level.dimension() != locationComponent.dimension) return

		val teleportLocation = locationComponent.pos.bottomCenter

		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource)
		entity.teleportTo(teleportLocation.x, teleportLocation.y, teleportLocation.z)
		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource)

		discard()
	}

	override fun copy(): ItemEntity = PortkeyItemEntity(this)

	companion object {
		const val PORTKEY_PICKUP_DELAY = 20 * 5
	}

}