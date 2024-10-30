package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModBlockStateProvider(
    output: PackOutput,
    private val existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, IrregularImplements.ID, existingFileHelper) {

    override fun registerStatesAndModels() {

        for (block in singleTextureTransparentBlocks) {
            singleTextureTransparent(block.get())
        }

    }

    private val singleTextureTransparentBlocks = listOf(
        ModBlocks.BLOCK_OF_STICKS,
        ModBlocks.RETURNING_BLOCK_OF_STICKS
    )

    private fun singleTextureTransparent(block: Block) {
        val model = models()
            .cubeAll(name(block), blockTexture(block))
            .renderType(mcLoc("cutout"))

        simpleBlockWithItem(block, model)
    }

    private fun name(block: Block): String {
        return BuiltInRegistries.BLOCK.getKey(block).path
    }

}