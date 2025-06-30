package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.core.component.DataComponents
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PortkeyItemEntity : ItemEntity {

	constructor(
		entityType: EntityType<PortkeyItemEntity>,
		level: Level
	) : super(entityType, level)

	constructor(
		level: Level,
		x: Double,
		y: Double,
		z: Double,
		stack: ItemStack
	) : super(level, x, y, z, stack)

	init {
		lifespan = Int.MAX_VALUE
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
		}
	}

	private fun willTeleport(): Boolean {
		if (age <= PORTKEY_PICKUP_DELAY) return false
		val stack = super.getItem()
		return stack.has(ModDataComponents.LOCATION)
	}

	override fun getItem(): ItemStack {
		val original = super.getItem()
		if (!willTeleport()) return original
		val copy = original.copy()
		copy.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, false)
		return copy
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

		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource,)
		entity.teleportTo(teleportLocation.x, teleportLocation.y, teleportLocation.z,)
		level.playSound(null, entity.blockPosition(), SoundEvents.PLAYER_TELEPORT, entity.soundSource,)
	}

	companion object {
		const val PORTKEY_PICKUP_DELAY = 20 * 5
	}

}