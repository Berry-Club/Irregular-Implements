package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.google.common.base.Predicate
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceKey
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class BiomeRadarBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.BIOME_RADAR.get(), pos, blockState) {

	private var antennaValid: Boolean = false
	private var biomePos: BlockPos? = null

	private var biomeStack: ItemStack = ItemStack.EMPTY

	fun getBiomeStack(): ItemStack = biomeStack.copy()
	fun setBiomeStack(stack: ItemStack) {
		biomeStack = stack.copy()
		setChanged()
		checkAntenna()

		level?.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)

		updateBiomePosition()
	}

	private fun updateBiomePosition() {
		val level = level as? ServerLevel ?: return

		val targetBiomeKey = biomeStack.get(ModDataComponents.BIOME)?.key

		val pos = if (targetBiomeKey == null) {
			null
		} else {
			locateBiome(targetBiomeKey, blockPos, level)
		}

		if (pos == biomePos) return

		biomePos = pos
		setChanged()
		level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	private fun updateAntenna() {
		val level = level ?: return

		antennaValid = ANTENNA_RELATIVE_POSITIONS.all { relPos ->
			val checkPos = blockPos.offset(relPos)
			val blockState = level.getBlockState(checkPos)

			return@all blockState.`is`(Blocks.IRON_BARS)
		}
	}

	private fun checkAntenna() {
		val wasValid = antennaValid
		updateAntenna()
		val isValid = antennaValid

		if (isValid == wasValid) return

		setChanged()
		level?.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL_IMMEDIATE)
	}

	fun serverTick() {
		val level = level ?: return

		if (level.gameTime % 20 == 0L) {
			checkAntenna()
		}
	}

	fun clientTick() {
		if (!antennaValid) return

		val level = level ?: return
		if (level.gameTime % 3 != 0L) return

		val particlePositions = PARTICLE_POINTS.map { it.offset(blockPos).bottomCenter.add(0.0, 0.2, 0.0) }

		val biomePos = biomePos
		val direction = if (biomePos == null) {
			Vec3.ZERO
		} else {
			this.blockPos.center.vectorTo(biomePos.center).normalize().scale(0.03)
		}

		for (pos in particlePositions) {
			level.addParticle(
				ModParticleTypes.FLOO_FLAME.get(),
				pos.x, pos.y, pos.z,
				direction.x, 0.1, direction.z
			)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		antennaValid = tag.getBoolean(ANTENNA_VALID_NBT)

		if (tag.contains(BIOME_POS_NBT)) {
			biomePos = BlockPos.of(tag.getLong(BIOME_POS_NBT))
		}

		biomeStack = ItemStack.parseOptional(registries, tag.getCompound(BIOME_STACK_NBT))
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putBoolean(ANTENNA_VALID_NBT, antennaValid)

		val bp = biomePos
		if (bp != null) {
			tag.putLong(BIOME_POS_NBT, bp.asLong())
		}

		tag.put(BIOME_STACK_NBT, biomeStack.saveOptional(registries))
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		private const val ANTENNA_VALID_NBT = "AntennaValid"
		private const val BIOME_POS_NBT = "BiomePos"
		private const val BIOME_STACK_NBT = "BiomeStack"

		val ANTENNA_RELATIVE_POSITIONS: List<BlockPos> =
			listOf(
				BlockPos(0, 1, 0),
				BlockPos(0, 2, 0),

				BlockPos(1, 2, 0),
				BlockPos(-1, 2, 0),
				BlockPos(0, 2, 1),
				BlockPos(0, 2, -1),

				BlockPos(1, 3, 0),
				BlockPos(-1, 3, 0),
				BlockPos(0, 3, 1),
				BlockPos(0, 3, -1),
			)

		val PARTICLE_POINTS: List<BlockPos> =
			listOf(
				BlockPos(1, 4, 0),
				BlockPos(-1, 4, 0),
				BlockPos(0, 4, 1),
				BlockPos(0, 4, -1),
			)

		fun locateBiome(
			targetBiome: ResourceKey<Biome>,
			searchFrom: BlockPos,
			level: ServerLevel
		): BlockPos? {
			return level.findClosestBiome3d(
				Predicate { it.`is`(targetBiome) },
				searchFrom,
				6400,
				32,
				64
			)?.first
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: BiomeRadarBlockEntity
		) {
			if (level.isClientSide) {
				blockEntity.clientTick()
			} else {
				blockEntity.serverTick()
			}
		}

	}

}