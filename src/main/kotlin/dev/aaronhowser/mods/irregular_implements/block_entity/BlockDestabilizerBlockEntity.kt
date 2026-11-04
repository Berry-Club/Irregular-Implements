package dev.aaronhowser.mods.irregular_implements.block_entity

import dev.aaronhowser.mods.aaron.block_entity.SyncingBlockEntity
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.isBlock
import dev.aaronhowser.mods.aaron.misc.AaronExtensions.toBlockPos
import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.menu.block_destabilizer.BlockDestabilizerMenu
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.AddIndicatorsPacket
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.RemoveIndicatorsPacket
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.Tag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3

class BlockDestabilizerBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : SyncingBlockEntity(ModBlockEntityTypes.BLOCK_DESTABILIZER.get(), pos, blockState), MenuProvider {

	enum class State { IDLE, SEARCHING, DROPPING }

	var state: State = State.IDLE
		private set

	private val alreadyChecked: HashSet<BlockPos> = hashSetOf()
	private val toCheck: ArrayList<BlockPos> = arrayListOf()

	private val targetBlockPositions: HashSet<BlockPos> = hashSetOf()

	// Initialized when the BE starts searching, it only accepts positions with this block
	// TL;DR: If it starts on Obsidian, targetBlock gets set to Obsidian and only Obsidian blocks are accepted
	private var targetBlock: Block? = null

	private var targetBlocksSorted: MutableList<BlockPos> = mutableListOf()
	private var dropCounter: Int = 0

	// Makes it save a list of positions and then load from that every time, rather than searching again
	// This also makes it not use targetBlock
	var isLazy: Boolean = false
		set(value) {
			field = value
			setChanged()
		}

	private val lazyBlocks: MutableSet<BlockPos> = mutableSetOf()

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putInt(STATE_NBT, state.ordinal)
		tag.putBoolean(LAZY_NBT, isLazy)

		if (lazyBlocks.isNotEmpty()) {
			val invalidBlocksTag = ListTag()
			for (blockPos in lazyBlocks) {
				val posLong = blockPos.asLong()
				val longTag = LongTag.valueOf(posLong)
				invalidBlocksTag.add(longTag)
			}
			tag.put(LAZY_BLOCKS, invalidBlocksTag)
		}

		if (state == State.SEARCHING) {
			val alreadyCheckedTag = ListTag()
			for (blockPos in alreadyChecked) {
				val posLong = blockPos.asLong()
				val longTag = LongTag.valueOf(posLong)
				alreadyCheckedTag.add(longTag)
			}
			tag.put(ALREADY_CHECKED_NBT, alreadyCheckedTag)

			val toCheckTag = ListTag()
			for (blockPos in toCheck) {
				val posLong = blockPos.asLong()
				val longTag = LongTag.valueOf(posLong)
				toCheckTag.add(longTag)
			}
			tag.put(TO_CHECK_NBT, toCheckTag)

			val targetBlocksTag = ListTag()
			for (blockPos in targetBlockPositions) {
				val posLong = blockPos.asLong()
				val longTag = LongTag.valueOf(posLong)
				targetBlocksTag.add(longTag)
			}
			tag.put(TARGET_BLOCKS_NBT, targetBlocksTag)
		}

		if (state == State.DROPPING) {
			tag.putInt(DROP_COUNTER_NBT, dropCounter)

			val targetBlocksSortedTag = ListTag()
			for (blockPos in targetBlocksSorted) {
				val posLong = blockPos.asLong()
				val longTag = LongTag.valueOf(posLong)
				targetBlocksSortedTag.add(longTag)
			}
			tag.put(TARGET_BLOCKS_SORTED_NBT, targetBlocksSortedTag)
		}

		if (targetBlock != null) {
			val targetBlockStateName = BuiltInRegistries.BLOCK.getKey(targetBlock!!).toString()
			tag.putString(TARGET_STATE_BLOCK_NBT, targetBlockStateName)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		val stateOrdinal = tag.getInt(STATE_NBT)
		state = State.entries[stateOrdinal]

		val lazy = tag.getBoolean(LAZY_NBT)
		isLazy = lazy

		if (tag.contains(LAZY_BLOCKS)) {
			val invalidBlocksTag = tag.getList(LAZY_BLOCKS, Tag.TAG_LONG.toInt())

			for (tagElement in invalidBlocksTag) {
				val posLong = (tagElement as LongTag).asLong
				val blockPos = posLong.toBlockPos()
				lazyBlocks.add(blockPos)
			}
		}

		if (state == State.SEARCHING) {
			val alreadyCheckedTag = tag.getList(ALREADY_CHECKED_NBT, Tag.TAG_LONG.toInt())
			for (tagElement in alreadyCheckedTag) {
				val posLong = (tagElement as LongTag).asLong
				val blockPos = posLong.toBlockPos()
				alreadyChecked.add(blockPos)
			}

			val toCheckTag = tag.getList(TO_CHECK_NBT, Tag.TAG_LONG.toInt())
			for (tagElement in toCheckTag) {
				val posLong = (tagElement as LongTag).asLong
				val blockPos = posLong.toBlockPos()
				toCheck.add(blockPos)
			}

			val targetBlocksTag = tag.getList(TARGET_BLOCKS_NBT, Tag.TAG_LONG.toInt())
			for (tagElement in targetBlocksTag) {
				val posLong = (tagElement as LongTag).asLong
				val blockPos = posLong.toBlockPos()
				targetBlockPositions.add(blockPos)
			}
		}

		if (state == State.DROPPING) {
			val dropCounter = tag.getInt(DROP_COUNTER_NBT)
			this.dropCounter = dropCounter

			val targetBlocksSortedTag = tag.getList(TARGET_BLOCKS_SORTED_NBT, Tag.TAG_LONG.toInt())
			for (tagElement in targetBlocksSortedTag) {
				val posLong = (tagElement as LongTag).asLong
				val blockPos = posLong.toBlockPos()
				targetBlocksSorted.add(blockPos)
			}
		}

		if (tag.contains(TARGET_STATE_BLOCK_NBT)) {
			val targetBlockStateName = ResourceLocation.parse(tag.getString(TARGET_STATE_BLOCK_NBT))
			val targetBlock = BuiltInRegistries.BLOCK.get(targetBlockStateName)
			this.targetBlock = targetBlock
		}
	}

	// Runs when the block is powered
	fun initStart() {
		if (isLazy && lazyBlocks.isNotEmpty()) {
			targetBlockPositions.clear()
			targetBlockPositions.addAll(lazyBlocks)

			initDrop()
			return
		}

		val level = level ?: return

		val facing = blockState.getValue(DirectionalBlock.FACING)

		val targetBlockPos = blockPos.relative(facing)
		val targetBlockState = level.getBlockState(targetBlockPos)

		if (targetBlockState.isAir
			|| targetBlockState.isBlock(ModBlockTagsProvider.BLOCK_DESTABILIZER_BLACKLIST)
			|| targetBlockState.getDestroySpeed(level, targetBlockPos) <= 0
			|| level.getBlockEntity(targetBlockPos) != null
		) return

		targetBlock = targetBlockState.block
		state = State.SEARCHING

		toCheck.add(targetBlockPos)
	}

	// Runs every tick if `state` is `SEARCHING`
	private fun stepSearch() {
		val doneSearching = toCheck.isEmpty() || targetBlockPositions.count() >= ServerConfig.CONFIG.blockDestabilizerLimit.get()
		if (doneSearching) {
			initDrop()
			return
		}

		val nextPos = toCheck.removeFirst()
		if (alreadyChecked.contains(nextPos)) return

		alreadyChecked.add(nextPos)

		val level = level as? ServerLevel ?: return
		val checkedState = level.getBlockState(nextPos)

		val shouldAdd = checkedState.block == targetBlock
		if (shouldAdd) {
			targetBlockPositions.add(nextPos)

			for (direction in Direction.entries) {
				val offsetPos = nextPos.relative(direction)
				if (!alreadyChecked.contains(offsetPos)) {
					toCheck.add(offsetPos)
				}
			}
		}

		val color = if (shouldAdd) 0x00FF00 else 0xFF0000

		val packet = AddIndicatorsPacket(nextPos, 5, color)
		packet.messageNearbyPlayers(level, blockPos, 32.0)
	}

	// Runs once if it's done searching, or if it's in lazy and it has a lazy shape
	private fun initDrop() {
		targetBlocksSorted = targetBlockPositions.sortedWith(
			compareBy<BlockPos> { it.y }
				.thenBy { it.distSqr(blockPos) }
		).toMutableList()

		lazyBlocks.clear()
		lazyBlocks.addAll(targetBlocksSorted)

		state = State.DROPPING
		dropCounter = 0

		targetBlockPositions.clear()
		toCheck.clear()
		alreadyChecked.clear()
	}

	// Runs every tick if `state` is `DROPPING`
	private fun dropNextBlock() {
		if (dropCounter >= targetBlocksSorted.size) {
			state = State.IDLE
			targetBlocksSorted.clear()
			targetBlock = null

			return
		}

		val level = level ?: return
		val checkedPos = targetBlocksSorted.getOrNull(dropCounter) ?: return
		val checkedState = level.getBlockState(checkedPos)

		val shouldDrop = (checkedState.block == targetBlock || isLazy) && level.getBlockEntity(checkedPos) == null

		if (shouldDrop) {
			FallingBlockEntity.fall(level, checkedPos, checkedState)
		}

		dropCounter++
	}

	// Menu stuff

	private val containerData = object : ContainerData {
		override fun get(index: Int): Int {
			return when (index) {
				LAZY_INDEX -> if (this@BlockDestabilizerBlockEntity.isLazy) 1 else 0
				else -> 0
			}
		}

		// The value is ignored
		override fun set(index: Int, value: Int) {
			when (index) {
				LAZY_INDEX -> toggleLazy()
				SHOW_LAZY_INDEX -> showLazyShape()
				RESET_LAZY_INDEX -> resetLazyShape()
			}
		}

		override fun getCount(): Int = CONTAINER_DATA_SIZE
	}

	override fun createMenu(containerId: Int, playerInventory: Inventory, player: Player): AbstractContainerMenu {
		return BlockDestabilizerMenu(containerId, containerData)
	}

	override fun getDisplayName(): Component {
		return blockState.block.name
	}

	// Buttons

	private fun toggleLazy() {
		if (state != State.IDLE) return

		isLazy = !isLazy
	}

	private fun showLazyShape(): Boolean {
		if (state != State.IDLE) return false

		val level = level as? ServerLevel ?: return false

		val packet = AddIndicatorsPacket(lazyBlocks.toList(), 20 * 10, 0x33FFFFFF, dimensions = Vec3(0.99, 0.99, 0.99))
		packet.messageNearbyPlayers(level, blockPos, 32.0)

		return true
	}

	private fun removeLazyIndicators(): Boolean {
		if (lazyBlocks.isEmpty()) return false

		val level = level as? ServerLevel ?: return false

		val packet = RemoveIndicatorsPacket(lazyBlocks.toList())
		packet.messageNearbyPlayers(level, blockPos, 32.0)

		return true
	}

	private fun resetLazyShape() {
		if (state != State.IDLE) return

		removeLazyIndicators()
		lazyBlocks.clear()
	}

	companion object {
		const val STATE_NBT = "state"
		const val LAZY_NBT = "lazy"

		const val TARGET_STATE_BLOCK_NBT = "target_state_block"

		const val LAZY_BLOCKS = "lazy_blocks"
		const val ALREADY_CHECKED_NBT = "already_checked"
		const val TO_CHECK_NBT = "to_check"
		const val TARGET_BLOCKS_NBT = "target_blocks"

		const val DROP_COUNTER_NBT = "drop_counter"
		const val TARGET_BLOCKS_SORTED_NBT = "target_blocks_sorted"

		const val CONTAINER_DATA_SIZE = 3
		const val LAZY_INDEX = 0
		const val SHOW_LAZY_INDEX = 1
		const val RESET_LAZY_INDEX = 2

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: BlockDestabilizerBlockEntity
		) {
			if (level.isClientSide) return

			if (blockEntity.state == State.SEARCHING) {
				blockEntity.stepSearch()
			} else if (blockEntity.state == State.DROPPING) {
				blockEntity.dropNextBlock()
			}
		}
	}

}