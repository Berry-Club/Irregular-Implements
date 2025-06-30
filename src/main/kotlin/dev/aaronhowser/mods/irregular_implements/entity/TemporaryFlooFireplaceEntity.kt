package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

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
}