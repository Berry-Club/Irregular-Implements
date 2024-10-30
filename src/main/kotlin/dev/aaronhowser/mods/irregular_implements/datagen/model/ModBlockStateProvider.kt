package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModBlockStateProvider(
    output: PackOutput,
    private val existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, IrregularImplements.ID, existingFileHelper) {

    override fun registerStatesAndModels() {

        for (block in basicBlock) {
            val model = cubeAll(block.get())
            simpleBlockWithItem(block.get(), model)
        }

    }

    val basicBlock = listOf(
        ModBlocks.BLOCK_OF_STICKS,
        ModBlocks.RETURNING_BLOCK_OF_STICKS
    )

}