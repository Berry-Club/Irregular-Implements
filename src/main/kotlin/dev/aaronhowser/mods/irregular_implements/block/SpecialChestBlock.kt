package dev.aaronhowser.mods.irregular_implements.block

import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class SpecialChestBlock private constructor(
    private val type: Type
) : ChestBlock(
    Properties.ofFullCopy(Blocks.CHEST),
    { if (type == Type.NATURE) ModBlockEntities.NATURE_CHEST.get() else ModBlockEntities.WATER_CHEST.get() }
) {

    private enum class Type { NATURE, WATER }

    companion object {
        val NATURE = SpecialChestBlock(Type.NATURE)
        val WATER = SpecialChestBlock(Type.WATER)
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return if (type == Type.NATURE) NatureBlockEntity(pos, state) else WaterBlockEntity(pos, state)
    }

    abstract class SpecialChestBlockEntity(type: BlockEntityType<*>, pos: BlockPos, blockState: BlockState) : ChestBlockEntity(type, pos, blockState) {
        override fun getDefaultName(): Component {
            return this.blockState.block.name
        }
    }

    class NatureBlockEntity(pos: BlockPos, blockState: BlockState) : SpecialChestBlockEntity(ModBlockEntities.NATURE_CHEST.get(), pos, blockState)
    class WaterBlockEntity(pos: BlockPos, blockState: BlockState) : SpecialChestBlockEntity(ModBlockEntities.WATER_CHEST.get(), pos, blockState)

}