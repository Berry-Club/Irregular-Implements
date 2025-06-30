package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.handling.IPayloadContext

class FlooParticlePacket(
	val positions: List<BlockPos>
) : IModPacket {

	override fun receiveOnClient(context: IPayloadContext) {
		context.enqueueWork {
			val level = context.player().level()

			for (pos in positions) {
				for (i in 0 until 25) {
					val iProgress = i.toDouble() / 25
					for (j in 0 until 25) {
						val jProgress = j.toDouble() / 25

						val spawnAt = Vec3(
							pos.x + jProgress,
							pos.y + 1.1,
							pos.z + iProgress
						)

						level.addParticle(
							ParticleTypes.FLAME,
							spawnAt.x, spawnAt.y, spawnAt.z,
							0.0, 0.01, 0.0
						)
					}
				}
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