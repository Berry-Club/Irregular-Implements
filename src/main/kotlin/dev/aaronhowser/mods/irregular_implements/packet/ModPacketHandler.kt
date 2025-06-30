package dev.aaronhowser.mods.irregular_implements.packet

import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedMenuString
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.BurningFlooFireplacePacket
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.SendClientToast
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientScreenString
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.PacketDistributor
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.registration.PayloadRegistrar

object ModPacketHandler {

	fun registerPayloads(event: RegisterPayloadHandlersEvent) {
		val registrar = event.registrar("1")

		toServer(
			registrar,
			ClientChangedMenuString.TYPE,
			ClientChangedMenuString.STREAM_CODEC
		)

		toClient(
			registrar,
			UpdateClientScreenString.TYPE,
			UpdateClientScreenString.STREAM_CODEC
		)

		toServer(
			registrar,
			ClientClickedMenuButton.TYPE,
			ClientClickedMenuButton.STREAM_CODEC
		)

		toClient(
			registrar,
			SendClientToast.TYPE,
			SendClientToast.STREAM_CODEC
		)

		toClient(
			registrar,
			BurningFlooFireplacePacket.TYPE,
			BurningFlooFireplacePacket.STREAM_CODEC
		)
	}

	fun messageNearbyPlayers(packet: IModPacket, serverLevel: ServerLevel, origin: Vec3, radius: Double) {
		for (player in serverLevel.players()) {
			val distance = player.distanceToSqr(origin.x(), origin.y(), origin.z())
			if (distance < radius * radius) {
				messagePlayer(player, packet)
			}
		}
	}

	fun messagePlayer(player: ServerPlayer, packet: IModPacket) {
		PacketDistributor.sendToPlayer(player, packet)
	}

	fun messageAllPlayers(packet: IModPacket) {
		PacketDistributor.sendToAllPlayers(packet)
	}

	fun messageServer(packet: IModPacket) {
		PacketDistributor.sendToServer(packet)
	}

	private fun <T : IModPacket> toClient(
		registrar: PayloadRegistrar,
		packetType: CustomPacketPayload.Type<T>,
		streamCodec: StreamCodec<in RegistryFriendlyByteBuf, T>,
	) {
		registrar.playToClient(
			packetType,
			streamCodec
		) { packet, context -> packet.receiveOnClient(context) }
	}

	private fun <T : IModPacket> toServer(
		registrar: PayloadRegistrar,
		packetType: CustomPacketPayload.Type<T>,
		streamCodec: StreamCodec<in RegistryFriendlyByteBuf, T>
	) {
		registrar.playToServer(
			packetType,
			streamCodec
		) { packet, context -> packet.receiveOnServer(context) }
	}

}