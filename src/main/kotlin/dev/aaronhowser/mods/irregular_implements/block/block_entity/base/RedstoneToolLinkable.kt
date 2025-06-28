package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import dev.aaronhowser.mods.irregular_implements.util.ServerScheduler
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag

interface RedstoneToolLinkable {

	companion object {
		private const val LINKED_POS_NBT = "LinkedPos"
	}

	var linkedPos: BlockPos?

	fun loadFromTag(tag: CompoundTag) {
		if (tag.contains(LINKED_POS_NBT)) {
			ServerScheduler.scheduleTaskInTicks(1) {
				linkedPos = BlockPos.of(tag.getLong(LINKED_POS_NBT))
			}
		}
	}

	fun saveToTag(tag: CompoundTag) {
		val linkedPos = linkedPos
		if (linkedPos != null) {
			tag.putLong(LINKED_POS_NBT, linkedPos.asLong())
		}
	}

}