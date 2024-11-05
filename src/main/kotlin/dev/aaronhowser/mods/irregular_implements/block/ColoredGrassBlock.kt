package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.GrassBlock

class ColoredGrassBlock(dyeColor: DyeColor) : GrassBlock(
    Properties
        .ofFullCopy(Blocks.GRASS_BLOCK)
        .mapColor(dyeColor)
) {
}