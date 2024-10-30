package dev.aaronhowser.mods.irregular_implements.datagen.model

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.RainbowLamp
import dev.aaronhowser.mods.irregular_implements.registries.ModBlocks
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.Block
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import net.neoforged.neoforge.common.data.ExistingFileHelper

class ModBlockStateProvider(
    output: PackOutput,
    private val existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, IrregularImplements.ID, existingFileHelper) {

    override fun registerStatesAndModels() {

        for (block in singleTextureTransparentBlocks) {
            singleTextureTransparent(block.get())
        }

        rainbowLamp()

    }

    private val singleTextureTransparentBlocks = listOf(
        ModBlocks.BLOCK_OF_STICKS,
        ModBlocks.RETURNING_BLOCK_OF_STICKS
    )

    private fun rainbowLamp() {
        val block = ModBlocks.RAINBOW_LAMP.get()

        getVariantBuilder(block)
            .forAllStates {
                val colorInt = it.getValue(RainbowLamp.COLOR)
                val colorString = when (colorInt) {
                    0 -> "white"
                    1 -> "light_gray"
                    2 -> "gray"
                    3 -> "black"
                    4 -> "red"
                    5 -> "orange"
                    6 -> "yellow"
                    7 -> "lime"
                    8 -> "green"
                    9 -> "light_blue"
                    10 -> "cyan"
                    11 -> "blue"
                    12 -> "purple"
                    13 -> "magenta"
                    14 -> "pink"
                    15 -> "brown"
                    else -> error("Invalid color")
                }

                ConfiguredModel
                    .builder()
                    .modelFile(
                        models().cubeAll(
                            "${name(block)}_$colorString",
                            modLoc("block/rainbow_lamp/$colorString")
                        )
                    )
                    .build()
            }

    }

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