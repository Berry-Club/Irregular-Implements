package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.aaron.packet.AaronPacket
import dev.aaronhowser.mods.irregular_implements.client.ClientSpectreIllumination
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.level.ChunkPos
import net.neoforged.neoforge.network.handling.IPayloadContext

class UpdateSpectreIlluminationPacket(
	val chunkPosLong: Long,
	val isIlluminated: Boolean
) : AaronPacket() {

	override fun handleOnClient(context: IPayloadContext) {
		val chunkPos = ChunkPos(chunkPosLong)
		ClientSpectreIllumination.setChunkIlluminated(chunkPos, isIlluminated)
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<UpdateSpectreIlluminationPacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("update_spectre_illumination"))

		val STREAM_CODEC: StreamCodec<ByteBuf, UpdateSpectreIlluminationPacket> =
			StreamCodec.composite(
				ByteBufCodecs.VAR_LONG, UpdateSpectreIlluminationPacket::chunkPosLong,
				ByteBufCodecs.BOOL, UpdateSpectreIlluminationPacket::isIlluminated,
				::UpdateSpectreIlluminationPacket
			)
	}

}