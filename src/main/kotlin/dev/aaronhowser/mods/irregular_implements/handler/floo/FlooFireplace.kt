package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import java.util.*

class FlooFireplace(
	var masterUuid: UUID,
	var name: String,
	var blockPos: BlockPos
) {

	constructor(tag: CompoundTag) : this(
		masterUuid = tag.getUUID(NBT_MASTER_UUID),
		name = tag.getString(NBT_NAME),
		blockPos = BlockPos.of(tag.getLong(NBT_BLOCK_POS))
	)

	fun toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID("masterUuid", masterUuid)
		tag.putString("name", name)
		tag.putLong("blockPos", blockPos.asLong())

		return tag
	}

	companion object {
		const val NBT_MASTER_UUID = "MasterUUID"
		const val NBT_NAME = "Name"
		const val NBT_BLOCK_POS = "BlockPos"
	}

}