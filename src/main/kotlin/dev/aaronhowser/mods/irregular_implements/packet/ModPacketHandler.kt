package dev.aaronhowser.mods.irregular_implements.packet

import dev.aaronhowser.mods.aaron.packet.AaronPacketRegistrar
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.PaintBiomePacket
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.*
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

object ModPacketHandler : AaronPacketRegistrar {

	fun registerPayloads(event: RegisterPayloadHandlersEvent) {
		val registrar = event.registrar("1")

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

		toClient(
			registrar,
			FlooTokenActivatedPacket.TYPE,
			FlooTokenActivatedPacket.STREAM_CODEC
		)

		toClient(
			registrar,
			AddIndicatorsPacket.TYPE,
			AddIndicatorsPacket.STREAM_CODEC
		)

		toClient(
			registrar,
			RemoveIndicatorsPacket.TYPE,
			RemoveIndicatorsPacket.STREAM_CODEC
		)

		toServer(
			registrar,
			PaintBiomePacket.TYPE,
			PaintBiomePacket.STREAM_CODEC
		)

		toClient(
			registrar,
			UpdateSpectreIlluminationPacket.TYPE,
			UpdateSpectreIlluminationPacket.STREAM_CODEC
		)
	}

}