package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.event.ServerChatEvent

class TemporaryFlooFireplaceEntity(
	entityType: EntityType<*>,
	level: Level
) : Entity(entityType, level) {

	constructor(level: Level, pos: Vec3) : this(
		ModEntityTypes.TEMPORARY_FLOO_FIREPLACE.get(),
		level
	) {
		setPos(pos)
	}

	override fun defineSynchedData(builder: SynchedEntityData.Builder) {}
	override fun readAdditionalSaveData(compound: CompoundTag) {}
	override fun addAdditionalSaveData(compound: CompoundTag) {}

	companion object {
		fun processMessage(event: ServerChatEvent) {
			if (event.isCanceled) return

			val player = event.player
			val level = player.serverLevel()
			val tempFireplace = level.getEntitiesOfClass(
				TemporaryFlooFireplaceEntity::class.java,
				player.boundingBox.inflate(0.5)
			).firstOrNull()

			if (tempFireplace == null) return

			val message = event.message.string
			val network = FlooNetworkSavedData.get(level)
			val destination = network.findFireplace(message)

			if (destination == null) {
				player.displayClientMessage(
					Component.literal("Fireplace not found"),
					true
				)

				return
			}

			tempFireplace.discard()
			destination.teleportToThis(player)

			event.isCanceled = true
		}
	}

}