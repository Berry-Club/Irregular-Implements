package dev.aaronhowser.mods.irregular_implements.datagen.datapack

import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.world.level.block.grower.TreeGrower
import java.util.*

object ModTreeGrowers {

    val SPECTRE: TreeGrower = TreeGrower(
        OtherUtil.modResource("spectre").toString(),
        Optional.empty(),
        Optional.of(ModConfiguredFeatures.SPECTRE_TREE_KEY),
        Optional.empty()
    )

}