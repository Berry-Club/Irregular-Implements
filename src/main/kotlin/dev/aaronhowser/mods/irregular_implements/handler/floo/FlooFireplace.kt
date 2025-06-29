package dev.aaronhowser.mods.irregular_implements.handler.floo

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import java.util.*

class FlooFireplace(
	val masterUuid: UUID,
	val name: String?,
	val blockPos: BlockPos
) {

	fun toTag(): CompoundTag {
		val tag = CompoundTag()
		tag.putUUID("masterUuid", masterUuid)
		if (name != null) tag.putString("name", name)
		tag.putLong("blockPos", blockPos.asLong())

		return tag
	}

	companion object {
		const val NBT_MASTER_UUID = "MasterUUID"
		const val NBT_NAME = "Name"
		const val NBT_BLOCK_POS = "BlockPos"

		fun fromTag(tag: CompoundTag): FlooFireplace {
			val uuid = tag.getUUID(NBT_MASTER_UUID)
			val name = tag.getString(NBT_NAME).ifBlank { null }
			val blockPos = BlockPos.of(tag.getLong(NBT_BLOCK_POS))

			return FlooFireplace(uuid, name, blockPos)
		}

	}

}