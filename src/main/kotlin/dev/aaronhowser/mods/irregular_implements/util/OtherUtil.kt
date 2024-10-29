package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.world.entity.Entity

object OtherUtil {

    val Boolean?.isTrue: Boolean
        get() = this == true

    val Entity.isClientSide: Boolean
        get() = this.level().isClientSide

}