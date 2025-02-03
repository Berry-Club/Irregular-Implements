package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class PlayerInterfaceBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.PLAYER_INTERFACE.get(), pPos, pBlockState) {

    companion object {

        private var PLAYER_PREDICATE: (Player, BlockEntity) -> Boolean = { _, _ -> true }

        @JvmStatic
        fun setPlayerPredicate(predicate: (Player, BlockEntity) -> Boolean) {
            PLAYER_PREDICATE = predicate
        }

        const val OWNER_UUID_NBT = "OwnerUuid"

    }

    var ownerUuid: UUID = UUID.randomUUID()

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
        if (uuid != null) this.ownerUuid = uuid
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.putUUID(OWNER_UUID_NBT, ownerUuid)
    }

}