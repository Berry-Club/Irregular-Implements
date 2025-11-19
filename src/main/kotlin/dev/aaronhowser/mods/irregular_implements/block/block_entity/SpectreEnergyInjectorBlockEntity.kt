package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.AaronExtensions.getUuidOrNull
import dev.aaronhowser.mods.irregular_implements.handler.SpectreEnergyHandler
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreEnergyInjectorBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.SPECTRE_ENERGY_INJECTOR.get(), pPos, pBlockState) {

	// Initialized as random, but immediately set on place or load
	var ownerUuid: UUID = UUID.randomUUID()
		set(value) {
			field = value
			setChanged()
		}

	fun getEnergyHandler(direction: Direction?): IEnergyStorage? {
		val level = this.level as? ServerLevel ?: return null

		return SpectreEnergyHandler.get(level).getEnergyInjector(this.ownerUuid)
	}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putUUID(OWNER_UUID_NBT, this.ownerUuid)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
		if (uuid != null) {
			this.ownerUuid = uuid
		}
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		const val OWNER_UUID_NBT = "OwnerUuid"

		fun getEnergyCapability(spectreCoilBlockEntity: SpectreEnergyInjectorBlockEntity, direction: Direction?): IEnergyStorage? {
			return spectreCoilBlockEntity.getEnergyHandler(direction)
		}

	}

}