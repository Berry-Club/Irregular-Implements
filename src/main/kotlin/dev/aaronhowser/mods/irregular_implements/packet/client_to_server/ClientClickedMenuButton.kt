package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.menu.base.MenuWithButtons
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientClickedMenuButton(
    private val buttonId: Int
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val playerMenu = player.containerMenu

            if (!playerMenu.stillValid(player)) return@enqueueWork

            if (playerMenu is MenuWithButtons) {
                playerMenu.handleButtonPressed(buttonId)
            }
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientClickedMenuButton> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientClickedMenuButton> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_clicked_menu_button"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientClickedMenuButton> =
            StreamCodec.composite(
                ByteBufCodecs.VAR_INT, ClientClickedMenuButton::buttonId,
                ::ClientClickedMenuButton
            )
    }
}