package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import net.minecraft.core.BlockPos

interface RedstoneToolLinkable {

    companion object {
        const val LINKED_POS_NBT = "linked_pos"
    }

    var linkedPos: BlockPos?

}