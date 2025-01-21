package dev.aaronhowser.mods.irregular_implements.block.block_entity.base

import net.minecraft.world.SimpleContainer
import net.minecraft.world.level.block.entity.BlockEntity

open class ImprovedSimpleContainer(
    private val blockEntity: BlockEntity,
    size: Int
) : SimpleContainer(size) {

    override fun setChanged() {
        super.setChanged()
        this.blockEntity.setChanged()
    }

}