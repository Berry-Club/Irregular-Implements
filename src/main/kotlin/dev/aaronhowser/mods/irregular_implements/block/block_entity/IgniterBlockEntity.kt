package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.igniter.IgniterMenu
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.tags.BlockTags
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.DirectionalBlock.FACING
import net.minecraft.world.level.block.FireBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class IgniterBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.IGNITER.get(), pPos, pBlockState), MenuProvider {

	enum class Mode(val nameComponent: Component) {
		TOGGLE(ModTooltipLang.IGNITER_TOGGLE.toComponent()),         // Make fire when powered, extinguish when unpowered
		IGNITE(ModTooltipLang.IGNITER_IGNITE.toComponent()),         // Make fire when powered, do nothing when unpowered
		KEEP_IGNITED(ModTooltipLang.IGNITER_KEEP_IGNITED.toComponent())    // Make fire when powered, make another fire if it goes out while powered
	}

	var mode: Mode = Mode.TOGGLE
		private set(value) {
			field = value
			setChanged()
		}

	fun blockUpdated(isPowered: Boolean, wasEnabled: Boolean) {
		val level = this.level ?: return

		val isTurningOn = isPowered && !wasEnabled
		val isTurningOff = !isPowered && wasEnabled

		when (this.mode) {
			Mode.KEEP_IGNITED -> if (isPowered) ignite(level, this.blockPos, this.blockState)

			Mode.IGNITE -> if (isTurningOn) ignite(level, this.blockPos, this.blockState)

			Mode.TOGGLE -> if (isTurningOn) ignite(level, this.blockPos, this.blockState) else if (isTurningOff) extinguish(level, this.blockPos, this.blockState)
		}
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(MODE_NBT, this.mode.ordinal)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		this.mode = Mode.entries[tag.getInt(MODE_NBT)]
	}

	// Menu stuff

	private val containerData = object : SimpleContainerData(CONTAINER_DATA_SIZE) {
		override fun set(index: Int, value: Int) {
			when (index) {
				MODE_INDEX -> this@IgniterBlockEntity.mode = Mode.entries.getOrNull(value) ?: Mode.TOGGLE
				else -> error("Unknown index: $index")
			}
		}

		override fun get(index: Int): Int {
			return when (index) {
				MODE_INDEX -> this@IgniterBlockEntity.mode.ordinal
				else -> error("Unknown index: $index")
			}
		}
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return IgniterMenu(containerId, this.containerData)
	}

	override fun getDisplayName(): Component {
		return this.blockState.block.name
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		const val MODE_NBT = "Mode"

		fun ignite(level: Level, igniterPos: BlockPos, igniterState: BlockState) {
			if (level.isClientSide) return

			val facing = igniterState.getValue(FACING)
			val targetPos = igniterPos.relative(facing)
			val targetState = level.getBlockState(targetPos)

			val canPlaceFire = targetState.canBeReplaced()
			if (canPlaceFire) {
				val fireState = (Blocks.FIRE as FireBlock).getStateForPlacement(level, targetPos)

				level.setBlockAndUpdate(targetPos, fireState)
			}
		}

		fun extinguish(level: Level, igniterPos: BlockPos, igniterState: BlockState) {
			if (level.isClientSide) return

			val facing = igniterState.getValue(FACING)
			val targetPos = igniterPos.relative(facing)
			val targetState = level.getBlockState(targetPos)

			if (targetState.`is`(BlockTags.FIRE)) {
				level.removeBlock(targetPos, false)
			}
		}

		const val CONTAINER_DATA_SIZE = 1
		const val MODE_INDEX = 0
	}

}