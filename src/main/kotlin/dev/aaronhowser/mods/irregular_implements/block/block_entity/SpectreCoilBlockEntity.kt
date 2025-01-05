package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.block.SpectreCoilBlock
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
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.energy.IEnergyStorage
import java.util.*

class SpectreCoilBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.SPECTRE_COIL.get(), pPos, pBlockState) {

    companion object {
        const val OWNER_UUID_NBT = "OwnerUuid"
        const val COIL_TYPE_NBT = "CoilType"
    }

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
        tag.putString(COIL_TYPE_NBT, this.coilType.name)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        val uuid = tag.getUuidOrNull(OWNER_UUID_NBT)
        if (uuid != null) {
            this.ownerUuid = uuid
        }

        try {
            val coilTypeString = tag.getString(COIL_TYPE_NBT)
            val coilType = SpectreCoilBlock.Type.valueOf(coilTypeString)

            this.coilType = coilType
        } catch (e: IllegalArgumentException) {
            // Invalid coil type, default to basic
            this.coilType = SpectreCoilBlock.Type.BASIC

            IrregularImplements.LOGGER.error(e)
        }
    }


    fun getEnergyHandler(direction: Direction?): IEnergyStorage? {
        if (direction != blockState.getValue(SpectreCoilBlock.FACING)) return null

        val level = this.level as? ServerLevel ?: return null


    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}