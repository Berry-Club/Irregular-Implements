package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class PlayerInterfaceBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.PLAYER_INTERFACE.get(), pPos, pBlockState) {

    companion object {

        private var PLAYER_PREDICATE: (Player) -> Boolean = { true }

        @JvmStatic
        fun setPlayerPredicate(predicate: (Player) -> Boolean) {
            PLAYER_PREDICATE = predicate
        }

    }

}