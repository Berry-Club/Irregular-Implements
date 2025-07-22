package dev.aaronhowser.mods.irregular_implements.util

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.chunk.RenderChunkRegion
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.Level
import net.neoforged.fml.loading.FMLEnvironment

object ClientUtil {

	@JvmStatic
	val localLevel: Level?
		get() {
			if (!FMLEnvironment.dist.isClient) return null
			return Minecraft.getInstance().level
		}

	@JvmStatic
	val localPlayer: Player?
		get() {
			if (!FMLEnvironment.dist.isClient) return null
			return Minecraft.getInstance().player
		}

	fun levelFromBlockAndTintGetter(blockAndTintGetter: BlockAndTintGetter): Level? {


		return (blockAndTintGetter as? RenderChunkRegion)?.level
	}

}