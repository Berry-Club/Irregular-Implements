package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.aaron.AaronExtraCodecs
import dev.aaronhowser.mods.aaron.packet.AaronPacket
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.handling.IPayloadContext

class AddIndicatorsPacket(
	val positions: List<BlockPos>,
	val durationTicks: Int,
	val color: Int,
	val dimensions: Vec3 = Vec3(1.0, 1.0, 1.0)
) : AaronPacket() {

	constructor(
		position: BlockPos,
		durationTicks: Int,
		color: Int,
		dimensions: Vec3 = Vec3(1.0, 1.0, 1.0)
	) : this(listOf(position), durationTicks, color, dimensions)

	override fun handleOnClient(context: IPayloadContext) {
		for (blockPos in positions) {
			CubeIndicatorRenderer.addIndicator(blockPos, durationTicks, color)
		}
	}

	override fun type(): CustomPacketPayload.Type<AddIndicatorsPacket> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<AddIndicatorsPacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("add_indicators"))

		val STREAM_CODEC: StreamCodec<ByteBuf, AddIndicatorsPacket> =
			StreamCodec.composite(
				BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), AddIndicatorsPacket::positions,
				ByteBufCodecs.VAR_INT, AddIndicatorsPacket::durationTicks,
				ByteBufCodecs.VAR_INT, AddIndicatorsPacket::color,
				AaronExtraCodecs.VEC3_STREAM_CODEC, AddIndicatorsPacket::dimensions,
				::AddIndicatorsPacket
			)
	}

}