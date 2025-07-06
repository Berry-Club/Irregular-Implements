package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.EnderMailboxBlock
import dev.aaronhowser.mods.irregular_implements.handler.ender_letter.EnderLetterHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class EnderMailboxBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntities.ENDER_MAILBOX.get(), pos, blockState) {

	private var ownerUuid: UUID = UUID.randomUUID()

	fun tick() {
		val level = level as? ServerLevel ?: return
		if (level.gameTime % 20 != 0L) return

		val handler = EnderLetterHandler.get(level)
		val inventory = handler.getInventory(ownerUuid)

		val wasFlagUp = blockState.getValue(EnderMailboxBlock.IS_FLAG_RAISED)
		val shouldFlagBeUp = inventory?.hasItems() ?: false

		if (wasFlagUp != shouldFlagBeUp) {
			val newState = blockState.setValue(EnderMailboxBlock.IS_FLAG_RAISED, shouldFlagBeUp)
			level.setBlockAndUpdate(worldPosition, newState)
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putUUID(OWNER_NBT, ownerUuid)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		if (tag.contains(OWNER_NBT)) {
			ownerUuid = tag.getUUID(OWNER_NBT)
		}
	}

	companion object {
		const val OWNER_NBT = "Owner"

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: EnderMailboxBlockEntity
		) {
			blockEntity.tick()
		}
	}

}