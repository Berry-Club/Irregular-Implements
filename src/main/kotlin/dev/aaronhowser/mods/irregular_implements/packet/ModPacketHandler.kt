package dev.aaronhowser.mods.irregular_implements.packet

import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientChangedChatDetector
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedBlockDestabilizerButton
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientBlockDestabilizer
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.UpdateClientChatDetector
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.network.PacketDistributor
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler

object ModPacketHandler {

    fun registerPayloads(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar("1")

        registrar.playToServer(
            ClientChangedChatDetector.TYPE,
            ClientChangedChatDetector.STREAM_CODEC,
            DirectionalPayloadHandler(
                { packet, context -> packet.receiveMessage(context) },
                { packet, context -> packet.receiveMessage(context) }
            )
        )

        registrar.playToClient(
            UpdateClientChatDetector.TYPE,
            UpdateClientChatDetector.STREAM_CODEC,
            DirectionalPayloadHandler(
                { packet, context -> packet.receiveMessage(context) },
                { packet, context -> packet.receiveMessage(context) }
            )
        )

        registrar.playToServer(
            ClientClickedBlockDestabilizerButton.TYPE,
            ClientClickedBlockDestabilizerButton.STREAM_CODEC,
            DirectionalPayloadHandler(
                { packet, context -> packet.receiveMessage(context) },
                { packet, context -> packet.receiveMessage(context) }
            )
        )

        registrar.playToClient(
            UpdateClientBlockDestabilizer.TYPE,
            UpdateClientBlockDestabilizer.STREAM_CODEC,
            DirectionalPayloadHandler(
                { packet, context -> packet.receiveMessage(context) },
                { packet, context -> packet.receiveMessage(context) }
            )
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

}