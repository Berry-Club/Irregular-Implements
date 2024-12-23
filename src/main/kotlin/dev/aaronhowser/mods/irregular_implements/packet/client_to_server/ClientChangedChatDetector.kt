package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.TellClientChatDetectorChanged
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientChangedChatDetector(
    val blockPos: BlockPos,
    val stopsMessage: Boolean,
    val message: String
) : IModPacket {

    override fun receiveMessage(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val chatDetectorBlockEntity = player.level().getBlockEntity(blockPos) as? ChatDetectorBlockEntity
                ?: return@enqueueWork

            val playerReach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE)
            if (!player.canInteractWithBlock(blockPos, playerReach)) return@enqueueWork

            chatDetectorBlockEntity.regexString = message
            chatDetectorBlockEntity.stopsMessage = stopsMessage

            ModPacketHandler.messagePlayer(player, TellClientChatDetectorChanged(stopsMessage, message))
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientChangedChatDetector> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientChangedChatDetector> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_changed_chat_detector"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientChangedChatDetector> =
            StreamCodec.composite(
                BlockPos.STREAM_CODEC, ClientChangedChatDetector::blockPos,
                ByteBufCodecs.BOOL, ClientChangedChatDetector::stopsMessage,
                ByteBufCodecs.STRING_UTF8, ClientChangedChatDetector::message,
                ::ClientChangedChatDetector
            )

    }

}