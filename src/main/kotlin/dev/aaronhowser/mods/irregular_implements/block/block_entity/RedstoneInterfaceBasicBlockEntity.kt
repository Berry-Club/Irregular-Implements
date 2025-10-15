package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneInterfaceBlockEntity
import dev.aaronhowser.mods.irregular_implements.block.block_entity.base.RedstoneToolLinkable
import dev.aaronhowser.mods.irregular_implements.handler.WirelessRedstoneHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.block.state.BlockState

class RedstoneInterfaceBasicBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : RedstoneInterfaceBlockEntity(
	ModBlockEntities.REDSTONE_INTERFACE.get(),
	pPos,
	pBlockState
), RedstoneToolLinkable {

	private var linkedPos: BlockPos? = null

	override fun getLinkedPos(): BlockPos? = linkedPos

	override fun setLinkedPos(pos: BlockPos?) {
		val oldField = linkedPos
		if (oldField != null) {
			WirelessRedstoneHandler.unlinkBlock(
				level = this.level!!,
				interfacePos = this.blockPos,
				targetPos = oldField
			)
		}

		if (pos != null) {
			WirelessRedstoneHandler.linkBlock(
				level = this.level!!,
				interfacePos = this.blockPos,
				targetPos = pos
			)
		} else {
			WirelessRedstoneHandler.removeInterface(this.level!!, this.blockPos)
		}

		linkedPos = pos
		setChanged()
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)
		this.saveToTag(tag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)
		this.loadFromTag(tag)
	}

}