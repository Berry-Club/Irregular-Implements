package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.handler.floo.FlooNetworkSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.LongArrayTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.event.ServerChatEvent
import java.util.*
import kotlin.jvm.optionals.getOrNull

class FlooBrickBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.FLOO_BRICK.get(), pos, blockState) {

	private var isMaster: Boolean = false

	// Master properties
	var uuid: UUID? = null
		private set
	var facing: Direction = Direction.NORTH
		private set
	var children: List<BlockPos> = listOf()
		private set

	// Child properties
	var masterUUID: UUID? = null
		private set

	fun setupMaster(uuid: UUID, facing: Direction, children: List<BlockPos>) {
		this.isMaster = true
		this.uuid = uuid
		this.facing = facing
		this.children = children
		setChanged()
	}

	fun setupChild(masterUUID: UUID) {
		this.isMaster = false
		this.masterUUID = masterUUID
		setChanged()
	}

	fun blockBroken() {
		val level = level as? ServerLevel ?: return

		if (isMaster) {
			for (pos in children) {
				level.setBlockAndUpdate(pos, Blocks.BRICKS.defaultBlockState())
			}

			level.setBlockAndUpdate(worldPosition, Blocks.BRICKS.defaultBlockState())

			val uuid = this.uuid
			if (uuid != null) {
				val network = FlooNetworkSavedData.get(level)
				network.removeFireplace(uuid)
			}
		} else {
			val master = masterUUID ?: return
			val network = FlooNetworkSavedData.get(level)
			val fireplace = network.findFireplace(master) ?: return

			val be = level.getBlockEntity(fireplace.masterBlockPos) as? FlooBrickBlockEntity ?: return
			be.blockBroken()
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putBoolean(IS_MASTER_TAG, isMaster)

		if (isMaster) {
			val uuid = this.uuid
			if (uuid != null) tag.putUUID(UUID_TAG, uuid)
			tag.putInt(FACING_TAG, facing.ordinal)

			val childrenList = LongArrayTag(children.map(BlockPos::asLong))
			tag.put(CHILDREN_TAG, childrenList)
		} else {
			val master = masterUUID
			if (master != null) tag.putUUID(MASTER_UUID_TAG, master)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		isMaster = tag.getBoolean(IS_MASTER_TAG)

		if (isMaster) {
			uuid = tag.getUuidOrNull(UUID_TAG) ?: UUID.randomUUID()

			val directionOrdinal = tag.getInt(FACING_TAG)
			facing = Direction.entries[directionOrdinal]

			children = tag.getLongArray(CHILDREN_TAG).map(BlockPos::of)
		} else {
			masterUUID = tag.getUuidOrNull(MASTER_UUID_TAG)
		}
	}


	companion object {
		const val IS_MASTER_TAG = "IsMaster"
		const val UUID_TAG = "UUID"
		const val FACING_TAG = "Facing"
		const val CHILDREN_TAG = "Children"
		const val MASTER_UUID_TAG = "MasterUUID"

		fun processMessage(event: ServerChatEvent): Boolean {
			val player = event.player

			val isHoldingFlooPowder = player.isHolding(ModItems.FLOO_POWDER.get())
			val hasFlooPouch = player.inventory.contains { it.`is`(ModItems.FLOO_POUCH) }

			if (!player.isCreative && !isHoldingFlooPowder) {
				//TODO: Make it also check if the floo pouch has powder in it
				if (!hasFlooPouch) return false
			}

			val level = player.serverLevel()
			val standingOnPos = player.mainSupportingBlockPos.getOrNull() ?: return false
			val standingOnBE = level.getBlockEntity(standingOnPos) as? FlooBrickBlockEntity ?: return false

			val standingOnFireplace = FlooNetworkSavedData.get(level)
				.findFireplace(standingOnBE)
				?: return false

			val message = event.message.string
			val success = standingOnFireplace.teleportFromThis(player, message)

			if (success) {
				if (!player.hasInfiniteMaterials()) {
					if (isHoldingFlooPowder) {
						val flooPowderStack = if (player.mainHandItem.`is`(ModItems.FLOO_POWDER.get())) {
							player.mainHandItem
						} else {
							player.offhandItem
						}

						flooPowderStack.shrink(1)
					} else if (hasFlooPouch) {
						//TODO: Implement floo pouch usage
					}
				}
			}

			return success
		}

	}

}