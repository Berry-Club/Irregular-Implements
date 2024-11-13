package dev.aaronhowser.mods.irregular_implements.client.renderer.item

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class DiaphanousBlockClientExtensions : IClientItemExtensions {

    private val bewlr = DiaphanousBEWLR()

    override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
        return bewlr
    }

}