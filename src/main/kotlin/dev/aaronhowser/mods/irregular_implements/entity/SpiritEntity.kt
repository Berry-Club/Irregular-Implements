package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.FlyingMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.level.Level

class SpiritEntity(
	entityType: EntityType<out FlyingMob>,
	level: Level
) : FlyingMob(entityType, level) {

	var age: Int = 0
	var changePositionCounter: Int = 0
	var spawnPosition: BlockPos = this.blockPosition()

	override fun tick() {
		super.tick()

		age++
		if (age > ServerConfig.SPIRIT_MAX_AGE.get()) {
			kill()
		}
	}

	override fun readAdditionalSaveData(compound: CompoundTag) {
		super.readAdditionalSaveData(compound)
		age = compound.getInt(AGE_NBT)
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		super.addAdditionalSaveData(compound)
		compound.putInt(AGE_NBT, age)
	}

	override fun knockback(strength: Double, x: Double, z: Double) {}
	override fun canBeCollidedWith(): Boolean = false
	override fun isPushable(): Boolean = false
	override fun doPush(entity: Entity) {}
	override fun pushEntities() {}
	override fun isIgnoringBlockTriggers(): Boolean = true

	companion object {
		const val AGE_NBT = "Age"

		fun createAttributes(): AttributeSupplier {
			return createMobAttributes()
				.add(Attributes.MAX_HEALTH, 1.0)
				.build()
		}
	}

}