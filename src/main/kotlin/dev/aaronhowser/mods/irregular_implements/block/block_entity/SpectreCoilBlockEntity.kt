package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.block.SpectreCoilBlock
import dev.aaronhowser.mods.irregular_implements.handler.SpectreCoilSavedData
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreCoilBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.SPECTRE_COIL.get(), pPos, pBlockState) {

	constructor(pos: BlockPos, blockState: BlockState, coilType: SpectreCoilBlock.Type) : this(pos, blockState) {
		this.coilType = coilType
	}

	private var coilType: SpectreCoilBlock.Type = SpectreCoilBlock.Type.BASIC
		set(value) {
			field = value
			setChanged()
		}

	var ownerUuid: UUID = UUID.randomUUID()
		set(value) {
			field = value
			setChanged()
		}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		tag.putUUID(OWNER_UUID_NBT, this.ownerUuid)
		tag.putInt(COIL_TYPE_NBT, this.coilType.ordinal)
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
		if (uuid != null) {
			this.ownerUuid = uuid
		}

		val coilTypeOrdinal = tag.getInt(COIL_TYPE_NBT)
		val coilType = SpectreCoilBlock.Type.entries.getOrNull(coilTypeOrdinal)
		if (coilType != null) {
			this.coilType = coilType
		}

	}

	/**
	 * Returns a fake energy handler that can't be interacted with, but knows how much energy exists.
	 * We do this to stop other mods from pulling from this, rather than energy being pushed out of it.
	 */
	fun getEnergyHandler(direction: Direction?): IEnergyStorage? {
		if (direction != blockState.getValue(SpectreCoilBlock.FACING)) return null

		val level = this.level as? ServerLevel ?: return null
		val coil = SpectreCoilSavedData.get(level).getCoil(this.ownerUuid)

		return object : IEnergyStorage {
			override fun receiveEnergy(toReceive: Int, simulate: Boolean): Int {
				return 0
			}

			override fun extractEnergy(toExtract: Int, simulate: Boolean): Int {
				return 0
			}

			override fun getEnergyStored(): Int {
				return coil.energyStored
			}

			override fun getMaxEnergyStored(): Int {
				return coil.maxEnergyStored
			}

			override fun canExtract(): Boolean {
				return false
			}

			override fun canReceive(): Boolean {
				return false
			}
		}
	}

	private fun tick() {
		val level = level as? ServerLevel ?: return

		val facing = this.blockState.getValue(SpectreCoilBlock.FACING)
		val onBlockPos = this.blockPos.relative(facing)

		val energyHandler = level.getCapability(Capabilities.EnergyStorage.BLOCK, onBlockPos, facing.opposite)

		if (energyHandler == null || !energyHandler.canReceive()) return

		val rate = this.coilType.amountGetter.get()

		if (this.coilType.isGenerator) {
			energyHandler.receiveEnergy(rate, false)
			return
		}

		val coil = SpectreCoilSavedData.get(level).getCoil(this.ownerUuid)

		val available = coil.extractEnergy(rate, true)  // Simulate it, which makes it return the amount it can extract
		if (available <= 0) return

		val sent = energyHandler.receiveEnergy(available, false)
		if (sent <= 0) return

		coil.extractEnergy(sent, false)
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		const val OWNER_UUID_NBT = "OwnerUuid"
		const val COIL_TYPE_NBT = "CoilType"

		fun getCapability(blockEntity: SpectreCoilBlockEntity, direction: Direction?): IEnergyStorage? {
			return blockEntity.getEnergyHandler(direction)
		}

		fun tick(
			level: Level,
			blockPos: BlockPos,
			blockState: BlockState,
			blockEntity: SpectreCoilBlockEntity
		) {
			if (level.isClientSide) return

			blockEntity.tick()
		}
	}

}