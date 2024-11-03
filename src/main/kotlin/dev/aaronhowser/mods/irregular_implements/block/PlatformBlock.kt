package dev.aaronhowser.mods.irregular_implements.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockSetType

class PlatformBlock(
    val blockSetType: BlockSetType?,
) : Block(
    Properties
        .ofFullCopy(
            when (blockSetType) {
                BlockSetType.OAK -> Blocks.OAK_TRAPDOOR
                BlockSetType.SPRUCE -> Blocks.SPRUCE_TRAPDOOR
                BlockSetType.BIRCH -> Blocks.BIRCH_TRAPDOOR
                BlockSetType.JUNGLE -> Blocks.JUNGLE_TRAPDOOR
                BlockSetType.ACACIA -> Blocks.ACACIA_TRAPDOOR
                BlockSetType.DARK_OAK -> Blocks.DARK_OAK_TRAPDOOR
                BlockSetType.CRIMSON -> Blocks.CRIMSON_TRAPDOOR
                BlockSetType.WARPED -> Blocks.WARPED_TRAPDOOR
                BlockSetType.MANGROVE -> Blocks.MANGROVE_TRAPDOOR
                BlockSetType.BAMBOO -> Blocks.BAMBOO_TRAPDOOR
                BlockSetType.CHERRY -> Blocks.CHERRY_TRAPDOOR
                null -> Blocks.BLUE_ICE
                else -> error("Unknown block set type: $blockSetType")
            }
        )
) {
}