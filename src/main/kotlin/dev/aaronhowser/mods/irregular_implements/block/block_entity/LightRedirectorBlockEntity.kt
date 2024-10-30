package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.registries.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class LightRedirectorBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.LIGHT_REDIRECTOR.get(), pPos, pBlockState) {

    companion object {
        val redirectorSet: HashSet<LightRedirectorBlockEntity> = HashSet()
    }

    val targets: EnumMap<Direction, Boolean> = EnumMap(Direction::class.java)

    init {
        redirectorSet.add(this)
    }

}