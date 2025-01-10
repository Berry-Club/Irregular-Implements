package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.irregular_implements.block.block_entity.ChatDetectorBlockEntity
import dev.aaronhowser.mods.irregular_implements.packet.IModPacket
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.ai.attributes.Attributes
import net.neoforged.neoforge.network.handling.IPayloadContext

class ClientChangedChatDetectorString(
    val blockPos: BlockPos,
    val regexString: String
) : IModPacket {

    override fun receiveOnServer(context: IPayloadContext) {
        context.enqueueWork {
            val player = context.player() as? ServerPlayer ?: return@enqueueWork
            val chatDetectorBlockEntity = player.level().getBlockEntity(blockPos) as? ChatDetectorBlockEntity
                ?: return@enqueueWork

            val playerReach = player.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE)
            if (!player.canInteractWithBlock(blockPos, playerReach)) return@enqueueWork

            chatDetectorBlockEntity.regexString = regexString
        }
    }

    override fun type(): CustomPacketPayload.Type<ClientChangedChatDetectorString> {
        return TYPE
    }

    companion object {
        val TYPE: CustomPacketPayload.Type<ClientChangedChatDetectorString> =
            CustomPacketPayload.Type(OtherUtil.modResource("client_changed_chat_detector"))

        val STREAM_CODEC: StreamCodec<ByteBuf, ClientChangedChatDetectorString> =
            StreamCodec.composite(
                BlockPos.STREAM_CODEC, ClientChangedChatDetectorString::blockPos,
                ByteBufCodecs.STRING_UTF8, ClientChangedChatDetectorString::regexString,
                ::ClientChangedChatDetectorString
            )

    }

}