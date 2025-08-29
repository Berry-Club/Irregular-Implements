package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.handling.IPayloadContext

class RenderCubePacket(
	val blockPos: BlockPos,
	val durationTicks: Int,
	val color: Int,
	val dimensions: Vec3
) : ModPacket() {

	override fun handleOnClient(context: IPayloadContext) {
		CubeIndicatorRenderer.addIndicator(blockPos, durationTicks, color)
	}

	override fun type(): CustomPacketPayload.Type<RenderCubePacket> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<RenderCubePacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("render_cube"))

		val STREAM_CODEC: StreamCodec<ByteBuf, RenderCubePacket> =
			StreamCodec.composite(
				BlockPos.STREAM_CODEC, RenderCubePacket::blockPos,
				ByteBufCodecs.VAR_INT, RenderCubePacket::durationTicks,
				ByteBufCodecs.VAR_INT, RenderCubePacket::color,
				OtherUtil.VEC3_STREAM_CODEC, RenderCubePacket::dimensions,
				::RenderCubePacket
			)
	}

}