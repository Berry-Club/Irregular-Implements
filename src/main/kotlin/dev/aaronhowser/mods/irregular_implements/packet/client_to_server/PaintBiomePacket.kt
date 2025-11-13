package dev.aaronhowser.mods.irregular_implements.packet.client_to_server

import dev.aaronhowser.mods.aaron.packet.ModPacket
import dev.aaronhowser.mods.irregular_implements.item.BiomePainterItem
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import io.netty.buffer.ByteBuf
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.biome.Biome
import net.neoforged.neoforge.network.handling.IPayloadContext

class PaintBiomePacket(
	val blockPos: BlockPos,
	val biomeRk: ResourceKey<Biome>
) : ModPacket() {

	override fun handleOnServer(context: IPayloadContext) {
		BiomePainterItem.handlePacket(this, context)
	}

	override fun type(): CustomPacketPayload.Type<out CustomPacketPayload?> {
		return TYPE
	}

	companion object {
		val TYPE: CustomPacketPayload.Type<PaintBiomePacket> =
			CustomPacketPayload.Type(OtherUtil.modResource("paint_biome"))

		val STREAM_CODEC: StreamCodec<ByteBuf, PaintBiomePacket> =
			StreamCodec.composite(
				BlockPos.STREAM_CODEC, PaintBiomePacket::blockPos,
				ResourceKey.streamCodec(Registries.BIOME), PaintBiomePacket::biomeRk,
				::PaintBiomePacket
			)
	}

}