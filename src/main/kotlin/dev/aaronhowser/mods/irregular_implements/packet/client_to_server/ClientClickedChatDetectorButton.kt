package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.ChatDetectorMenu
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedChatDetectorButton(
    private val buttonId: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val playerMenu = player.containerMenu as? ChatDetectorMenu ?: return@enqueueWork

            if (!playerMenu.stillValid(player)) return@enqueueWork

            playerMenu.handleButtonPressed(buttonId)
        }
    }

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {
        TODO("Not yet implemented")
    }
}