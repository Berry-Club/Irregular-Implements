package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.EnderAnchorCarrier
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import it.unimi.dsi.fastutil.longs.LongOpenHashSet
import net.minecraft.core.BlockPos
import net.minecraft.util.Mth
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.cos
import kotlin.math.sin

class EnderAnchorBlockEntity(
	pos: BlockPos,
	blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.ENDER_ANCHOR.get(), pos, blockState) {

	private fun clientTick() {
		val level = this.level ?: return

		if (!level.isClientSide) return

		val localPlayer = ClientUtil.localPlayer ?: return
		if (!localPlayer.isHolding(ModItems.PORTABLE_ENDER_BRIDGE.get())) return

		val distance = localPlayer.eyePosition.distanceTo(blockPos.center)
		val distanceToMaxSize = 100.0
		val percentage = (distance / distanceToMaxSize).coerceIn(0.0, 1.0)

		val easeInOutSine = -(cos(Mth.PI * percentage) - 1) / 2
		val scale = Mth.lerp(easeInOutSine, 0.8, 5.0)

		val colorOne = 0xFF14173E
		val colorTwo = 0xFF383993

		val tick = level.gameTime

		val period = 20.0 * 10.0

		val t = (sin((tick % period) / period * Math.PI * 2.0) + 1.0) / 2.0

		val r = Mth.lerp(
			t.toFloat(),
			(colorOne shr 16 and 0xFF).toFloat(),
			(colorTwo shr 16 and 0xFF).toFloat()
		).toInt()

		val g = Mth.lerp(
			t.toFloat(),
			(colorOne shr 8 and 0xFF).toFloat(),
			(colorTwo shr 8 and 0xFF).toFloat()
		).toInt()

		val b = Mth.lerp(
			t.toFloat(),
			(colorOne and 0xFF).toFloat(),
			(colorTwo and 0xFF).toFloat()
		).toInt()

		val color = (0x77 shl 24) or (r shl 16) or (g shl 8) or b

		CubeIndicatorRenderer.addIndicator(
			blockPos,
			2,
			color,
			scale.toFloat()
		)
	}

	companion object {
		fun EnderAnchorCarrier.getEnderAnchorPositionLongs(): LongOpenHashSet = this.`irregular_implements$getEnderAnchorBlockPositions`()
		fun EnderAnchorCarrier.getEnderAnchorPositions(): List<BlockPos> = this.getEnderAnchorPositionLongs().map(BlockPos::of)

		fun tick(
			level: Level,
			pos: BlockPos,
			state: BlockState,
			blockEntity: EnderAnchorBlockEntity
		) {
			if (level is EnderAnchorCarrier) {
				level.getEnderAnchorPositionLongs().add(pos.asLong())
			}

			if (level.isClientSide) {
				blockEntity.clientTick()
			}
		}
	}

}