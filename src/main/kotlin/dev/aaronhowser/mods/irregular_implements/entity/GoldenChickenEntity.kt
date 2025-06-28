package dev.aaronhowser.mods.irregular_implements.entity

import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.ItemTags
import net.minecraft.util.Mth
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.pathfinder.PathType

class GoldenChickenEntity(
	entityType: EntityType<GoldenChickenEntity>,
	level: Level
) : Animal(entityType, level) {

	companion object {
		val BABY_DIMENSIONS: EntityDimensions = ModEntityTypes.GOLDEN_CHICKEN.get().dimensions.scale(0.5f).withEyeHeight(0.2975f)

		fun createAttributes(): AttributeSupplier {
			return createMobAttributes()
				.add(Attributes.MAX_HEALTH, 4.0)
				.add(Attributes.MOVEMENT_SPEED, 0.25)
				.build()
		}
	}

	init {
		this.setPathfindingMalus(PathType.WATER, 0.0f)
	}

	var flap: Float = 0f
	var flapSpeed: Float = 0f
	var oFlapSpeed: Float = 0f
	var oFlap: Float = 0f
	var flapping: Float = 1.0f
	private var nextFlap = 1.0f
	var eggTime: Int = random.nextInt(6000) + 6000

	override fun registerGoals() {
		goalSelector.addGoal(0, FloatGoal(this))
		goalSelector.addGoal(1, PanicGoal(this, 1.4))
		goalSelector.addGoal(2, BreedGoal(this, 1.0))
		goalSelector.addGoal(3, TemptGoal(this, 1.0, { it.`is`(ItemTags.CHICKEN_FOOD) }, false))
		goalSelector.addGoal(4, FollowParentGoal(this, 1.1))
		goalSelector.addGoal(5, WaterAvoidingRandomStrollGoal(this, 1.0))
		goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 6.0f))
		goalSelector.addGoal(7, RandomLookAroundGoal(this))
	}

	public override fun getDefaultDimensions(pose: Pose): EntityDimensions {
		return if (this.isBaby) BABY_DIMENSIONS else super.getDefaultDimensions(pose)
	}

	override fun aiStep() {
		super.aiStep()
		this.oFlap = this.flap
		this.oFlapSpeed = this.flapSpeed
		this.flapSpeed += (if (this.onGround()) -1.0f else 4.0f) * 0.3f
		this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0f, 1.0f)

		if (!this.onGround() && this.flapping < 1.0f) this.flapping = 1.0f

		this.flapping *= 0.9f
		val vec3 = this.deltaMovement
		if (!this.onGround() && vec3.y < 0.0) {
			this.deltaMovement = vec3.multiply(1.0, 0.6, 1.0)
		}

		this.flap += this.flapping * 2.0f
		if ((!level().isClientSide && this.isAlive && !this.isBaby) && --this.eggTime <= 0) {
			this.playSound(SoundEvents.CHICKEN_EGG, 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f)
			this.spawnAtLocation(Items.GOLD_INGOT)
			this.gameEvent(GameEvent.ENTITY_PLACE)
			this.eggTime = random.nextInt(6000) + 6000
		}
	}

	override fun isFlapping(): Boolean {
		return this.flyDist > this.nextFlap
	}

	override fun onFlap() {
		this.nextFlap = this.flyDist + this.flapSpeed / 2.0f
	}

	override fun getAmbientSound(): SoundEvent {
		return SoundEvents.CHICKEN_AMBIENT
	}

	override fun getHurtSound(damageSource: DamageSource): SoundEvent {
		return SoundEvents.CHICKEN_HURT
	}

	override fun getDeathSound(): SoundEvent {
		return SoundEvents.CHICKEN_DEATH
	}

	override fun playStepSound(pos: BlockPos, block: BlockState) {
		this.playSound(SoundEvents.CHICKEN_STEP, 0.15f, 1.0f)
	}

	override fun getBreedOffspring(level: ServerLevel, otherParent: AgeableMob): GoldenChickenEntity? {
		return ModEntityTypes.GOLDEN_CHICKEN.get().create(level)
	}

	override fun isFood(stack: ItemStack): Boolean {
		return stack.`is`(ItemTags.CHICKEN_FOOD)
	}

	override fun readAdditionalSaveData(compound: CompoundTag) {
		super.readAdditionalSaveData(compound)
		if (compound.contains("EggLayTime")) {
			this.eggTime = compound.getInt("EggLayTime")
		}
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		super.addAdditionalSaveData(compound)
		compound.putInt("EggLayTime", this.eggTime)
	}

	override fun positionRider(passenger: Entity, callback: MoveFunction) {
		super.positionRider(passenger, callback)
		if (passenger is LivingEntity) {
			passenger.yBodyRot = this.yBodyRot
		}
	}

}