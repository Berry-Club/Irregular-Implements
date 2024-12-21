package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.renderer.chunk.RenderChunkRegion
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.Level

object ClientUtil {

    @JvmStatic
    val localPlayer: LocalPlayer?
        get() = Minecraft.getInstance().player

    fun levelFromBlockAndTintGetter(blockAndTintGetter: BlockAndTintGetter): Level? {
        return (blockAndTintGetter as? RenderChunkRegion)?.level
    }

}