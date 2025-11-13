package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.aaron.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.client.render.CubeIndicatorRenderer
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class RemoveIndicatorsPacket(
	val positions: List<BlockPos>
) : ModPacket() {

	override fun handleOnClient(context: IPayloadContext) {
		for (pos in positions) {
			CubeIndicatorRenderer.removeIndicatorsAt(pos)
		}
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<RemoveIndicatorsPacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("remove_indicators"))

		val STREAM_CODEC: StreamCodec<ByteBuf, RemoveIndicatorsPacket> =
			BlockPos.STREAM_CODEC
				.apply(ByteBufCodecs.list())
				.map(::RemoveIndicatorsPacket, RemoveIndicatorsPacket::positions)
	}

}