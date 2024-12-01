package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.LongTag
import net.minecraft.nbt.Tag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.entity.item.FallingBlockEntity
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockDestabilizerBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_DESTABILIZER.get(), pPos, pBlockState) {

    companion object {
        const val STATE_NBT = "state"
        const val LAZY_NBT = "lazy"
        const val FUZZY_NBT = "fuzzy"

        const val INVALID_BLOCKS_NBT = "invalid_blocks"
        const val ALREADY_CHECKED_NBT = "already_checked"
        const val TO_CHECK_NBT = "to_check"
        const val TARGET_BLOCKS_NBT = "target_blocks"

        const val DROP_COUNTER_NBT = "drop_counter"
        const val TARGET_BLOCKS_SORTED_NBT = "target_blocks_sorted"
    }

    enum class State { IDLE, SEARCHING, DROPPING }

    var state: State = State.IDLE

    val alreadyChecked: HashSet<BlockPos> = hashSetOf()
    val toCheck: ArrayList<BlockPos> = arrayListOf()

    val targetBlocks: HashSet<BlockPos> = hashSetOf()

    // Initialized when the BE starts searching, it only accepts positions that have this BlockState
    // TL;DR: If it starts on Obsidian, targetState gets set to Obsidian and only Obsidian blocks are accepted
    var targetState: BlockState? = null

    var targetBlocksSorted: MutableList<BlockPos> = mutableListOf()
    var dropCounter: Int = 0

    // Fuzzy makes it so it compares Block rather than BlockState
    // TODO: Make it use tags or something instead
    var fuzzy: Boolean = false

    // Makes it save the shape of the structure and only search there
    var lazy: Boolean = false

    val invalidBlocks: HashSet<BlockPos> = hashSetOf()

    fun resetLazy() {
        if (this.state == State.IDLE) this.invalidBlocks.clear()
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putInt(STATE_NBT, this.state.ordinal)
        tag.putBoolean(LAZY_NBT, this.lazy)
        tag.putBoolean(FUZZY_NBT, this.fuzzy)

        if (this.lazy && this.invalidBlocks.isNotEmpty()) {
            val invalidBlocksTag = ListTag()
            for (blockPos in this.invalidBlocks) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                invalidBlocksTag.add(longTag)
            }
            tag.put(INVALID_BLOCKS_NBT, invalidBlocksTag)
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
            for (blockPos in this.targetBlocks) {
                val posLong = blockPos.asLong()
                val longTag = LongTag.valueOf(posLong)
                targetBlocksTag.add(longTag)
            }
            tag.put(TARGET_BLOCKS_NBT, targetBlocksTag)

            //TODO: Put a string and integer of the current block's name and metadata? obviously that doesn't exist any more but why even bother with that?
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

            //TODO: Put a string and integer of the current block's name and metadata? obviously that doesn't exist any more but why even bother with that?
        }
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val stateOrdinal = tag.getInt(STATE_NBT)
        this.state = State.entries[stateOrdinal]

        val lazy = tag.getBoolean(LAZY_NBT)
        this.lazy = lazy

        val fuzzy = tag.getBoolean(FUZZY_NBT)
        this.fuzzy = fuzzy

        if (this.lazy && tag.contains(INVALID_BLOCKS_NBT)) {
            val invalidBlocksTag = tag.getList(INVALID_BLOCKS_NBT, Tag.TAG_LONG.toInt())

            for (tagElement in invalidBlocksTag) {
                val posLong = (tagElement as LongTag).asLong
                val blockPos = BlockPos.of(posLong)
                this.invalidBlocks.add(blockPos)
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
                this.targetBlocks.add(blockPos)
            }

            //TODO: Target block name and metadata
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

            //TODO: Target block name and metadata
        }
    }


    private fun dropNextBlock() {
        val dropCounter = this.dropCounter

        if (dropCounter > this.targetBlocksSorted.size) {
            this.state = State.IDLE
            targetBlocksSorted.clear()
            targetState = null

            return
        }

        val level = this.level ?: return
        val checkedPos = this.targetBlocksSorted.getOrNull(dropCounter) ?: return
        val checkedState = level.getBlockState(checkedPos)

        val shouldDrop = (this.fuzzy && checkedState.block == targetState?.block) || checkedState == targetState

        if (shouldDrop) {
            FallingBlockEntity.fall(level, checkedPos, checkedState)
        }

        this.dropCounter++
    }

    private fun initDrop() {
        this.targetBlocksSorted = this.targetBlocks.sortedWith(
            compareBy<BlockPos> { it.y }
                .thenBy { it.distSqr(this.blockPos) }
        ).toMutableList()

        this.state = State.DROPPING
        this.dropCounter = 0

        this.targetBlocks.clear()
        this.toCheck.clear()
        this.alreadyChecked.clear()
    }


    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}