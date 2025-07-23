package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.nextRange
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.FlyingMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.FlyingMoveControl
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

class SpiritEntity(
	entityType: EntityType<out FlyingMob>,
	level: Level
) : FlyingMob(entityType, level) {

	var age: Int = 0
	private var spawnPosition: BlockPos? = null
	private var changePosCounter: Int = 0

	init {
		moveControl = FlyingMoveControl(this, 20, true)
	}

	override fun registerGoals() {
		goalSelector.addGoal(0, RandomLookAroundGoal(this))
		goalSelector.addGoal(9, FloatGoal(this))
	}

	override fun tick() {
		super.tick()
		ageAndDie()
		spawnParticles()
	}

	private fun spawnParticles() {
		if (!this.isClientSide) return

		// Only spawn particles if it moved
		if (this.x == this.xOld && this.y == this.yOld && this.z == this.zOld) return

		if (random.nextFloat() < 0.5) {
			level().addParticle(
				ParticleTypes.ENCHANT,
				this.x, this.y, this.z,
				random.nextRange(-0.01, 0.01),
				random.nextRange(0.0, 0.02),
				random.nextRange(-0.01, 0.01)
			)
		}
	}

	override fun aiStep() {
		super.aiStep()

		if (spawnPosition == null) spawnPosition = blockPosition()
		val spawnPos = spawnPosition ?: return

		changePosCounter++

		if (changePosCounter >= 60) {
			changePosCounter = 0

			var tries = 0
			var newTarget: Vec3? = null

			while (newTarget == null && tries < 10) {
				tries++

				val modX = random.nextRange(-5.0, 5.0)
				val modY = random.nextRange(-5.0, 5.0)
				val modZ = random.nextRange(-5.0, 5.0)

				val modVec = spawnPos.center.add(modX, modZ, modY)
				val modPos = BlockPos.containing(modVec)

				if (this.level().getBlockState(modPos).isAir) {
					newTarget = modVec
				}
			}

			if (newTarget != null) {
				moveControl.setWantedPosition(
					newTarget.x + 0.5,
					newTarget.y + 0.5,
					newTarget.z + 0.5,
					1.0
				)
			}
		}
	}

	override fun createNavigation(level: Level): PathNavigation {
		val flyingNavigation = FlyingPathNavigation(this, level)
		flyingNavigation.setCanOpenDoors(false)
		flyingNavigation.setCanFloat(true)
		flyingNavigation.setCanPassDoors(false)
		return flyingNavigation
	}

	private fun ageAndDie() {
		age++
		if (age > ServerConfig.SPIRIT_MAX_AGE.get()) {
//			kill()
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
				.add(Attributes.FLYING_SPEED, 1.0)
				.build()
		}
	}

}