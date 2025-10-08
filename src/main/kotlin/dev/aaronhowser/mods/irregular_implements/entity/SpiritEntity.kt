package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModItemTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isClientSide
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.isTrue
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.nextRange
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.FlyingMob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.FlyingMoveControl
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.Tags
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent

//TODO: They only move a little bit and then stop
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

	override fun isInvulnerableTo(source: DamageSource): Boolean {
		if (super.isInvulnerableTo(source)) return true

		val usedSpecialWeapon = source.weaponItem?.`is`(ModItemTagsProvider.DAMAGES_SPIRITS).isTrue
		val usedEnchantedWeapon = source.weaponItem?.isEnchanted.isTrue
		val usedMagic = source.`is`(Tags.DamageTypes.IS_MAGIC)

		return !usedSpecialWeapon && !usedEnchantedWeapon && !usedMagic
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
		if (age > ServerConfig.CONFIG.spiritMaxAge.get()) {
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

		fun trySpawn(event: LivingDeathEvent) {
			if (event.isCanceled) return
			val entity = event.entity
			val level = entity.level() as? ServerLevel ?: return

			if (level.random.nextDouble() < getSpawnChance(level)) {
				ModEntityTypes.SPIRIT.get()
					.spawn(level, entity.blockPosition(), MobSpawnType.TRIGGERED)
			}
		}

		fun getSpawnChance(level: ServerLevel): Double {
			var chance = ServerConfig.CONFIG.spiritBaseSpawnChance.get()

			if (level.server.worldData.endDragonFightData().previouslyKilled) {
				chance += ServerConfig.CONFIG.spiritSpawnChanceDragonKilledBonus.get()
			}

			val moonPhase = level.moonPhase
			val distToFull = minOf(moonPhase, 8 - moonPhase)
			val percentToFull = 1 - distToFull / 4.0
			chance += (percentToFull * ServerConfig.CONFIG.spiritSpawnFullMoonBonus.get())

			return chance.coerceIn(0.0, 1.0)
		}
	}

}