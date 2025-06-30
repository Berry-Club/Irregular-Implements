package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext

class FlooParticlePacket(
	val positions: List<BlockPos>
) : IModPacket {

	override fun receiveOnClient(context: IPayloadContext) {
		context.enqueueWork {
			val level = context.player().level()

			for (pos in positions) {
				val spawnAt = pos.above().bottomCenter
				level.addParticle(
					ModParticleTypes.FLOO_FLAME.get(),
					spawnAt.x, spawnAt.y, spawnAt.z,
					0.0, 0.05, 0.0
				)
			}
		}
	}

	override fun type(): CustomPacketPayload.Type<FlooParticlePacket> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<FlooParticlePacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("floo_particle"))

		val STREAM_CODEC: StreamCodec<ByteBuf, FlooParticlePacket> =
			StreamCodec.composite(
				BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), FlooParticlePacket::positions,
				::FlooParticlePacket
			)

	}

}