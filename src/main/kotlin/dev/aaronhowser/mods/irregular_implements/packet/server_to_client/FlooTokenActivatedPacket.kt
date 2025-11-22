package dev.aaronhowser.mods.irregular_implements.packet.server_to_client

import dev.aaronhowser.mods.aaron.AaronExtraCodecs
import dev.aaronhowser.mods.aaron.packet.AaronPacket
import dev.aaronhowser.mods.irregular_implements.registry.ModParticleTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.handling.IPayloadContext

class FlooTokenActivatedPacket(
	val location: Vec3
) : AaronPacket() {

	override fun handleOnClient(context: IPayloadContext) {
		val level = context.player().level()

		val y = location.y

		for (i in 0 until 40) {
			val iProgress = i.toDouble() / 40
			val x = location.x - 1 + iProgress * 2

			for (j in 0 until 40) {
				val jProgress = j.toDouble() / 40
				val z = location.z - 1 + jProgress * 2

				level.addParticle(
					ModParticleTypes.FLOO_FLAME.get(),
					x, y, z,
					0.0, level.random.nextDouble() * 0.3 + 0.1, 0.0
				)
			}
		}
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<FlooTokenActivatedPacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("floo_token_activated"))

		val STREAM_CODEC: StreamCodec<ByteBuf, FlooTokenActivatedPacket> =
			AaronExtraCodecs.VEC3_STREAM_CODEC.map(::FlooTokenActivatedPacket, FlooTokenActivatedPacket::location)
	}

}