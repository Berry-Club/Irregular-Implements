package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload

class UpdateSpectreIlluminationPacket(
	val chunkPosLong: Long,
	val isIlluminated: Boolean
) : ModPacket() {

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<UpdateSpectreIlluminationPacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("update_client_screen_string"))

		val STREAM_CODEC: StreamCodec<ByteBuf, UpdateSpectreIlluminationPacket> =
			StreamCodec.composite(
				ByteBufCodecs.VAR_LONG, UpdateSpectreIlluminationPacket::chunkPosLong,
				ByteBufCodecs.BOOL, UpdateSpectreIlluminationPacket::isIlluminated,
				::UpdateSpectreIlluminationPacket
			)
	}

}