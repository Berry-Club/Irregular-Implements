package dev.aaronhowser.mods.irregular_implements.block.block_entity

import dev.aaronhowser.mods.aaron.AaronExtensions.getUuidOrNull
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import java.util.*

class SpectreLensBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntityTypes.SPECTRE_LENS.get(), pPos, pBlockState) {

	var owner: UUID? = null
		set(value) {
			field = value
			setChanged()
		}

	override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.saveAdditional(tag, registries)

		if (owner != null) {
			tag.putUUID(OWNER_UUID_NBT, owner!!)
		}
	}

	override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
		super.loadAdditional(tag, registries)

		owner = tag.getUuidOrNull(OWNER_UUID_NBT)
	}

	// Syncs with client
	override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
	override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

	companion object {
		const val OWNER_UUID_NBT = "OwnerUuid"

		@JvmStatic
		fun applyEffects(
			level: Level,
			pos: BlockPos,
			beaconLevel: Int,
			primaryEffect: Holder<MobEffect>?,
			secondaryEffect: Holder<MobEffect>?
		) {
			if (level.isClientSide || primaryEffect == null) return

			val blockEntity = level.getBlockEntity(pos.above()) as? SpectreLensBlockEntity ?: return
			val owner = blockEntity.owner ?: return
			val player = level.getPlayerByUUID(owner) ?: return

			val sameEffect = secondaryEffect != null && primaryEffect == secondaryEffect

			val amplifier = if (beaconLevel >= 4 && sameEffect) 1 else 0
			val duration = (9 + beaconLevel * 2) * 20

			val primaryEffectInstance = MobEffectInstance(primaryEffect, duration, amplifier, true, true)
			player.addEffect(primaryEffectInstance)

			if (beaconLevel >= 4 && secondaryEffect != null && !sameEffect) {
				val secondaryEffectInstance = MobEffectInstance(secondaryEffect, duration, 0, true, true)
				player.addEffect(secondaryEffectInstance)
			}
		}
	}

}