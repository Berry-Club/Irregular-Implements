package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.OnlineDetectorBlock
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class OnlineDetectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.ONLINE_DETECTOR.get(), pPos, pBlockState) {

    companion object {

        fun tick(level: Level, pos: BlockPos, state: BlockState, blockEntity: OnlineDetectorBlockEntity) {
            if (level !is ServerLevel || level.gameTime % 20 != 0L) return

            val username = blockEntity.username
            val playerOnline = level.server.playerList.getPlayerByName(username) != null

            if (playerOnline != state.getValue(OnlineDetectorBlock.ENABLED)) {
                level.setBlockAndUpdate(
                    pos,
                    state.setValue(OnlineDetectorBlock.ENABLED, playerOnline)
                )
            }
        }

        const val USERNAME_NBT = "Username"
    }

    var username: String = ""
        set(value) {
            field = value
            setChanged()
        }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putString(USERNAME_NBT, this.username)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.username = tag.getString(USERNAME_NBT)
    }

}