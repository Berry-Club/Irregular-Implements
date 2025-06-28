package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class DiaphanousBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.DIAPHANOUS_BLOCK.get(), pPos, pBlockState) {

	companion object {
		const val RENDERED_BLOCK_STATE = "RenderedBlockState"
		const val IS_INVERTED_NBT = "IsInverted"
	}

	var renderedBlockState: BlockState = Blocks.STONE.defaultBlockState()
		set(value) {
			field = value
			setChanged()
		}

	var isInverted: Boolean = false
		set(value) {
			field = value
			setChanged()
		}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		val blockStateTag = NbtUtils.writeBlockState(this.renderedBlockState)
		tag.put(RENDERED_BLOCK_STATE, blockStateTag)

		tag.putBoolean(IS_INVERTED_NBT, this.isInverted)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		this.isInverted = tag.getBoolean(IS_INVERTED_NBT)

		val blockStateTag = tag.getCompound(RENDERED_BLOCK_STATE)
		val readBlockState = NbtUtils.readBlockState(
			registries.lookupOrThrow(Registries.BLOCK),
			blockStateTag
		)

		this.renderedBlockState = readBlockState
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}