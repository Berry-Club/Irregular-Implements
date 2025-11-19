package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag

interface RedstoneToolLinkable {

	fun getLinkedPos(): BlockPos?
	fun setLinkedPos(pos: BlockPos?)

	fun loadFromTag(tag: CompoundTag) {
		if (tag.contains(LINKED_POS_NBT)) {
			setLinkedPos(BlockPos.of(tag.getLong(LINKED_POS_NBT)))
		}
	}

	fun saveToTag(tag: CompoundTag) {
		val linkedPos = getLinkedPos()
		if (linkedPos != null) {
			tag.putLong(LINKED_POS_NBT, linkedPos.asLong())
		}
	}

	companion object {
		private const val LINKED_POS_NBT = "LinkedPos"
	}

}