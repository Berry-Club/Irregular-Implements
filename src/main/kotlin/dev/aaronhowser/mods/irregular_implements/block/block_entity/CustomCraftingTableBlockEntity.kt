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

class CustomCraftingTableBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.CUSTOM_CRAFTING_TABLE.get(), pPos, pBlockState) {

	var renderedBlockState: BlockState = Blocks.OAK_PLANKS.defaultBlockState()
		set(value) {
			field = value
			setChanged()
		}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		val blockStateTag = NbtUtils.writeBlockState(this.renderedBlockState)
		tag.put(RENDERED_BLOCK_STATE, blockStateTag)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

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

	companion object {
		const val RENDERED_BLOCK_STATE = "RenderedBlockState"
	}

}