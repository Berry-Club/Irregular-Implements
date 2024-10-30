package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType

class LightRedirectorBlock : Block(
    Properties
        .of()
        .sound(SoundType.WOOD)
        .strength(2f)
) {
}