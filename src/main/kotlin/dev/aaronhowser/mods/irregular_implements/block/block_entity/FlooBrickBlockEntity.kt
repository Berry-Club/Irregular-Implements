package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.LongArrayTag
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class FlooBrickBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.FLOO_BRICK.get(), pos, blockState) {

	private var isMaster: Boolean = false

	// Master properties
	private var uuid: UUID = UUID.randomUUID()
	var facing: Direction = Direction.NORTH
		private set
	var children: List<BlockPos> = listOf()
		private set

	// Child properties
	private var masterUUID: UUID? = null

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putBoolean(IS_MASTER_TAG, isMaster)

		if (isMaster) {
			tag.putUUID(UUID_TAG, uuid)
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
	}

}