package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModBlockTagsProvider
import dev.aaronhowser.mods.irregular_implements.entity.IndicatorDisplayEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.Tag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

//TODO: Menu
class BlockDestabilizerBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_DESTABILIZER.get(), pPos, pBlockState) {

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

    private val lazyBlocks: HashSet<BlockPos> = hashSetOf()

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putInt(STATE_NBT, this.state.ordinal)
        tag.putBoolean(LAZY_NBT, this.isLazy)

        if (this.lazyBlocks.isNotEmpty()) {
            val invalidBlocksTag = ListTag()
            for (blockPos in this.lazyBlocks) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                invalidBlocksTag.add(longTag)
            }
            tag.put(LAZY_BLOCKS, invalidBlocksTag)
        }

        if (this.state == State.SEARCHING) {
            val alreadyCheckedTag = ListTag()
            for (blockPos in this.alreadyChecked) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                alreadyCheckedTag.add(longTag)
            }
            tag.put(ALREADY_CHECKED_NBT, alreadyCheckedTag)

            val toCheckTag = ListTag()
            for (blockPos in this.toCheck) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                toCheckTag.add(longTag)
            }
            tag.put(TO_CHECK_NBT, toCheckTag)

            val targetBlocksTag = ListTag()
            for (blockPos in this.targetBlockPositions) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                targetBlocksTag.add(longTag)
            }
            tag.put(TARGET_BLOCKS_NBT, targetBlocksTag)
        }

        if (this.state == State.DROPPING) {
            tag.putInt(DROP_COUNTER_NBT, this.dropCounter)

            val targetBlocksSortedTag = ListTag()
            for (blockPos in this.targetBlocksSorted) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                targetBlocksSortedTag.add(longTag)
            }
            tag.put(TARGET_BLOCKS_SORTED_NBT, targetBlocksSortedTag)
        }

        if (this.targetBlock != null) {
            val targetBlockStateName = BuiltInRegistries.BLOCK.getKey(targetBlock!!).toString()
            tag.putString(TARGET_STATE_BLOCK_NBT, targetBlockStateName)
        }
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val stateOrdinal = tag.getInt(STATE_NBT)
        this.state = State.entries[stateOrdinal]

        val lazy = tag.getBoolean(LAZY_NBT)
        this.isLazy = lazy

        if (tag.contains(LAZY_BLOCKS)) {
            val invalidBlocksTag = tag.getList(LAZY_BLOCKS, Tag.TAG_LONG.toInt())

            for (tagElement in invalidBlocksTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.lazyBlocks.add(blockPos)
            }
        }

        if (this.state == State.SEARCHING) {
            val alreadyCheckedTag = tag.getList(ALREADY_CHECKED_NBT, Tag.TAG_LONG.toInt())
            for (tagElement in alreadyCheckedTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.alreadyChecked.add(blockPos)
            }

            val toCheckTag = tag.getList(TO_CHECK_NBT, Tag.TAG_LONG.toInt())
            for (tagElement in toCheckTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.toCheck.add(blockPos)
            }

            val targetBlocksTag = tag.getList(TARGET_BLOCKS_NBT, Tag.TAG_LONG.toInt())
            for (tagElement in targetBlocksTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.targetBlockPositions.add(blockPos)
            }
        }

        if (this.state == State.DROPPING) {
            val dropCounter = tag.getInt(DROP_COUNTER_NBT)
            this.dropCounter = dropCounter

            val targetBlocksSortedTag = tag.getList(TARGET_BLOCKS_SORTED_NBT, Tag.TAG_LONG.toInt())
            for (tagElement in targetBlocksSortedTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.targetBlocksSorted.add(blockPos)
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
        if (this.isLazy && this.lazyBlocks.isNotEmpty()) {
            this.targetBlockPositions.clear()
            this.targetBlockPositions.addAll(this.lazyBlocks)

            initDrop()
            return
        }

        val level = this.level ?: return

        val facing = this.blockState.getValue(DirectionalBlock.FACING)

        val targetBlockPos = this.blockPos.relative(facing)
        val targetBlockState = level.getBlockState(targetBlockPos)

        if (targetBlockState.isAir
            || targetBlockState.`is`(ModBlockTagsProvider.BLOCK_DESTABILIZER_BLACKLIST)
            || targetBlockState.getDestroySpeed(level, targetBlockPos) <= 0
            || level.getBlockEntity(targetBlockPos) != null
        ) return

        this.targetBlock = targetBlockState.block
        this.state = State.SEARCHING

        this.toCheck.add(targetBlockPos)
    }

    // Runs every tick if `this.state` is `SEARCHING`
    private fun stepSearch() {
        val doneSearching = this.toCheck.isEmpty() || this.targetBlockPositions.count() >= ServerConfig.BLOCK_DESTABILIZER_LIMIT.get()
        if (doneSearching) {
            initDrop()
            return
        }

        val nextPos = this.toCheck.removeFirst()
        if (this.alreadyChecked.contains(nextPos)) return

        this.alreadyChecked.add(nextPos)

        val level = this.level ?: return
        val checkedState = level.getBlockState(nextPos)

        val shouldAdd = checkedState.block == this.targetBlock
        if (shouldAdd) {
            this.targetBlockPositions.add(nextPos)

            for (direction in Direction.entries) {
                val offsetPos = nextPos.relative(direction)
                if (!this.alreadyChecked.contains(offsetPos)) {
                    this.toCheck.add(offsetPos)
                }
            }
        }

        val color = if (shouldAdd) 0x00FF00 else 0xFF0000
        OtherUtil.spawnIndicatorBlockDisplay(level, nextPos, color, 5)
    }

    // Runs once if it's done searching, or if it's in lazy and it has a lazy shape
    private fun initDrop() {
        this.targetBlocksSorted = this.targetBlockPositions.sortedWith(
            compareBy<BlockPos> { it.y }
                .thenBy { it.distSqr(this.blockPos) }
        ).toMutableList()

        this.lazyBlocks.clear()
        this.lazyBlocks.addAll(this.targetBlocksSorted)

        this.state = State.DROPPING
        this.dropCounter = 0

        this.targetBlockPositions.clear()
        this.toCheck.clear()
        this.alreadyChecked.clear()
    }

    // Runs every tick if `this.state` is `DROPPING`
    private fun dropNextBlock() {
        if (this.dropCounter >= this.targetBlocksSorted.size) {
            this.state = State.IDLE
            this.targetBlocksSorted.clear()
            this.targetBlock = null

            return
        }

        val level = this.level ?: return
        val checkedPos = this.targetBlocksSorted.getOrNull(this.dropCounter) ?: return
        val checkedState = level.getBlockState(checkedPos)

        val shouldDrop = (checkedState.block == this.targetBlock || this.isLazy) && level.getBlockEntity(checkedPos) == null

        if (shouldDrop) {
            FallingBlockEntity.fall(level, checkedPos, checkedState)
        }

        this.dropCounter++
    }

    // Buttons

    fun toggleLazy() {
        if (state != State.IDLE) return

        this.isLazy = !this.isLazy
    }

    private val lazyIndicatorDisplays: MutableList<IndicatorDisplayEntity> = mutableListOf()
    fun showLazyShape(): Boolean {
        if (removeLazyIndicators() || this.state != State.IDLE) return false

        val level = this.level ?: return false

        for (blockPos in this.lazyBlocks) {
            val indicator = OtherUtil.spawnIndicatorBlockDisplay(level, blockPos, 0x0000FF, 20 * 15)
            if (indicator != null) lazyIndicatorDisplays.add(indicator)
        }

        return true
    }

    private fun removeLazyIndicators(): Boolean {
        var anyAlive = false

        for (indicator in this.lazyIndicatorDisplays) {
            if (indicator.isAlive) anyAlive = true
            indicator.discard()
        }

        lazyIndicatorDisplays.clear()

        return anyAlive
    }

    fun resetLazyShape() {
        if (this.state != State.IDLE) return

        removeLazyIndicators()
        this.lazyBlocks.clear()
    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}