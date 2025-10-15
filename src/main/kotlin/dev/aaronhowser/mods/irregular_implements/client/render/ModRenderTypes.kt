package dev.aaronhowser.mods.irregular_implements.client.render

import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.VertexFormat
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.minecraft.client.renderer.RenderStateShard.COLOR_WRITE
import net.minecraft.client.renderer.RenderStateShard.ITEM_ENTITY_TARGET
import net.minecraft.client.renderer.RenderStateShard.LineStateShard
import net.minecraft.client.renderer.RenderStateShard.NO_CULL
import net.minecraft.client.renderer.RenderStateShard.NO_DEPTH_TEST
import net.minecraft.client.renderer.RenderStateShard.POSITION_COLOR_SHADER
import net.minecraft.client.renderer.RenderStateShard.RENDERTYPE_LINES_SHADER
import net.minecraft.client.renderer.RenderStateShard.TRANSLUCENT_TRANSPARENCY
import net.minecraft.client.renderer.RenderStateShard.VIEW_OFFSET_Z_LAYERING
import net.minecraft.client.renderer.RenderType
import java.util.OptionalDouble

object ModRenderTypes {

	@Suppress("INFERRED_INVISIBLE_RETURN_TYPE_WARNING")
	val LINES_THROUGH_WALL_RENDER_TYPE: RenderType.CompositeRenderType =
		RenderType.create(
			"${IrregularImplements.ID}:line_through_wall",
			DefaultVertexFormat.POSITION_COLOR_NORMAL,
			VertexFormat.Mode.LINES,
			256,
			true,
			false,
			RenderType.CompositeState.builder()
				.setShaderState(RENDERTYPE_LINES_SHADER)
				.setLineState(LineStateShard(OptionalDouble.empty()))
				.setLayeringState(VIEW_OFFSET_Z_LAYERING)
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setOutputState(ITEM_ENTITY_TARGET)
				.setCullState(NO_CULL)
				.setDepthTestState(NO_DEPTH_TEST)
				.setWriteMaskState(COLOR_WRITE)
				.createCompositeState(false)
		)

	@Suppress("INFERRED_INVISIBLE_RETURN_TYPE_WARNING")
	val QUADS_THROUGH_WALL_RENDER_TYPE: RenderType =
		RenderType.create(
			"${IrregularImplements.ID}:quads_through_wall",
			DefaultVertexFormat.POSITION_COLOR,
			VertexFormat.Mode.QUADS,
			1536,
			false,
			true,
			RenderType.CompositeState.builder()
				.setShaderState(POSITION_COLOR_SHADER)
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setCullState(NO_CULL)
				.setDepthTestState(NO_DEPTH_TEST)
				.setWriteMaskState(COLOR_WRITE)
				.createCompositeState(false)
		)

	fun linesThroughWalls() = LINES_THROUGH_WALL_RENDER_TYPE
	fun quadsThroughWalls() = QUADS_THROUGH_WALL_RENDER_TYPE

}