package dev.aaronhowser.mods.irregular_implements.entity

import com.google.common.collect.HashMultimap
import dev.aaronhowser.mods.irregular_implements.registry.ModEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.ChunkPos
import net.minecraft.world.level.CommonLevelAccessor
import net.minecraft.world.level.Level
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraft.world.phys.Vec3
import kotlin.math.pow
import kotlin.random.Random

class SpectreIlluminatorEntity(
	entityType: EntityType<*>,
	level: Level
) : Entity(entityType, level) {

	override fun isPickable(): Boolean {
		return true
	}

	override fun hurt(source: DamageSource, amount: Float): Boolean {
		val entity = source.entity ?: return false
		removedByEntity(entity)
		return false
	}

	override fun interact(player: Player, hand: InteractionHand): InteractionResult {
		removedByEntity(player)
		return InteractionResult.SUCCESS
	}

	private fun removedByEntity(entity: Entity) {
		if (entity is Player) {
			if (entity.hasInfiniteMaterials()) {
				this.discard()
			} else {
				this.kill()
			}
		} else {
			this.kill()
		}
	}

	override fun getPickResult(): ItemStack? {
		return ModItems.SPECTRE_ILLUMINATOR.toStack()
	}

	override fun onAddedToLevel() {
		super.onAddedToLevel()

		val chunkPos = ChunkPos(this.blockPosition())

		illuminatedChunks[level()].add(chunkPos.toLong())

		forceLightUpdates(level(), chunkPos)
	}

	//FIXME: Not being called on client from `/kill`?
	override fun remove(reason: RemovalReason) {
		super.remove(reason)

		val chunkPos = ChunkPos(this.blockPosition())
		illuminatedChunks[level()].remove(chunkPos.toLong())

		forceLightUpdates(level(), chunkPos)

		if (removalReason == RemovalReason.KILLED) {
			OtherUtil.dropStackAt(ModItems.SPECTRE_ILLUMINATOR.toStack(), this)
		}
	}

	private var destination: Vec3 = Vec3.ZERO

	override fun tick() {
		super.tick()

		if (this.actionTimer < TICKS_TO_MAX_SIZE) {
			this.actionTimer++
		}

		moveToDestination()
	}

	private fun moveToDestination() {
		if (destination == Vec3.ZERO || this.tickCount % (20 * 60) == 0) recalculateDestination()

		if (this.position() == destination) return

		val distance = this.position().distanceTo(destination)
		if (distance < 0.1) {
			this.setPos(destination)
			return
		}

		val newPos = this.position().lerp(destination, 0.001)

		this.setPos(newPos)
	}

	private fun recalculateDestination() {
		val chunkPos = ChunkPos(this.blockPosition())
		val chunk = level().getChunk(chunkPos.x, chunkPos.z)

		var highestBlock = level().minBuildHeight
		for (dX in 0..15) for (dZ in 0..15) {
			val height = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, dX, dZ)

			if (height > highestBlock) {
				highestBlock = height
			}

			if (highestBlock >= level().maxBuildHeight) break
		}

		val random = Random(chunkPos.toLong())

		val x = chunkPos.middleBlockX.toDouble() + random.nextDouble(-MAX_VARIATION, MAX_VARIATION)
		val y = highestBlock.toDouble() + HEIGHT_ABOVE_MAX_BLOCK + random.nextDouble(-MAX_VARIATION, 0.0)
		val z = chunkPos.middleBlockZ.toDouble() + random.nextDouble(-MAX_VARIATION, MAX_VARIATION)

		destination = Vec3(x, y, z)
	}

	constructor(level: Level) : this(ModEntityTypes.SPECTRE_ILLUMINATOR.get(), level)

	var actionTimer: Int
		get() = this.entityData.get(ACTION_TIMER)
		private set(value) {
			this.entityData.set(ACTION_TIMER, value)
		}

	override fun defineSynchedData(builder: SynchedEntityData.Builder) {
		builder.define(ACTION_TIMER, 0)
	}

	override fun readAdditionalSaveData(compound: CompoundTag) {
		this.actionTimer = compound.getInt(ACTION_TIMER_NBT)
	}

	override fun addAdditionalSaveData(compound: CompoundTag) {
		compound.putInt(ACTION_TIMER_NBT, this.actionTimer)
	}

	override fun shouldRenderAtSqrDistance(distance: Double): Boolean {
		return distance < (64.0 * getViewScale()).pow(4)
	}

	companion object {
		const val HEIGHT_ABOVE_MAX_BLOCK = 50
		const val MAX_VARIATION = 5.0

		const val TICKS_TO_MAX_SIZE = 20 * 60

		val ACTION_TIMER: EntityDataAccessor<Int> = SynchedEntityData.defineId(SpectreIlluminatorEntity::class.java, EntityDataSerializers.INT)
		const val ACTION_TIMER_NBT = "ActionTimer"

		private val illuminatedChunks: HashMultimap<Level, Long> = HashMultimap.create()

		//TODO: Check if is RenderChunkRegion
		@JvmStatic
		fun isChunkIlluminated(blockPos: BlockPos, blockAndTintGetter: BlockAndTintGetter): Boolean {
			val level: Level = when (blockAndTintGetter) {
				is Level -> blockAndTintGetter

				// If it's something that can be accessed on server, but isn't a Level, return false before it tries to load client-only class
				is CommonLevelAccessor -> return false

				else -> ClientUtil.levelFromBlockAndTintGetter(blockAndTintGetter) ?: return false
			}

			val chunkPos = ChunkPos(blockPos)

			return illuminatedChunks[level].contains(chunkPos.toLong())
		}

		//FIXME: For some reason it doesn't work super well in chunks that are mostly empty (possibly only effects superflat levels?)
		//TODO: Study effect on lag, possibly only when the chunk loads the first time?
		private fun forceLightUpdates(level: Level, chunkPos: ChunkPos) {
			if (!level.isLoaded(chunkPos.worldPosition)) return

			// +- 1 to also check edges
			val minX = chunkPos.minBlockX - 1
			val maxX = chunkPos.maxBlockX + 1
			val minZ = chunkPos.minBlockZ - 1
			val maxZ = chunkPos.maxBlockZ + 1
			val minY = level.minBuildHeight
			val maxY = level.maxBuildHeight

			for (x in minX..maxX) for (z in minZ..maxZ) for (y in minY..maxY) {
				val pos = BlockPos(x, y, z)

				level.chunkSource.lightEngine.checkBlock(pos)
			}
		}

	}

}