package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

object OtherUtil {

    fun modResource(path: String): ResourceLocation =
        ResourceLocation.fromNamespaceAndPath(IrregularImplements.ID, path)

    val Boolean?.isTrue: Boolean
        get() = this == true

    val Entity.isClientSide: Boolean
        get() = this.level().isClientSide

}