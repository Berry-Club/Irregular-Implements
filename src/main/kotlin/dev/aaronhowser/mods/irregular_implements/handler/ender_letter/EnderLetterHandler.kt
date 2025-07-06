package dev.aaronhowser.mods.irregular_implements.handler.ender_letter

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.saveddata.SavedData
import java.util.*

class EnderLetterHandler : SavedData() {

	val inventories: MutableMap<UUID, EnderMailboxInventory> = mutableMapOf()

	override fun save(tag: CompoundTag, registries: HolderLookup.Provider): CompoundTag {
		val listTag = tag.getList(LIST_NBT, Tag.TAG_COMPOUND.toInt())

		for ((uuid, inventory) in inventories) {
			if (!inventory.hasItems()) continue

			val invTag = CompoundTag()
			invTag.putUUID(OWNER_UUID_NBT, uuid)
			invTag.put(INVENTORY_NBT, inventory.saveAsTag(registries))

			listTag.add(invTag)
		}

		tag.put(LIST_NBT, listTag)

		return tag
	}

	fun getInventory(uuid: UUID): EnderMailboxInventory {
		return inventories.getOrPut(uuid) {
			EnderMailboxInventory(this)
		}
	}

	fun getInventory(owner: Entity): EnderMailboxInventory = getInventory(owner.uuid)

	companion object {
		const val LIST_NBT = "EnderLetterInventories"
		const val OWNER_UUID_NBT = "OwnerUUID"
		const val INVENTORY_NBT = "Inventory"

		private fun load(tag: CompoundTag, provider: HolderLookup.Provider): EnderLetterHandler {
			val handler = EnderLetterHandler()

			val listTag = tag.getList(LIST_NBT, Tag.TAG_COMPOUND.toInt())

			for (i in listTag.indices) {
				val tag = listTag.getCompound(i)

				val uuid = tag.getUUID(OWNER_UUID_NBT)
				val inventoryTag = tag.getCompound(INVENTORY_NBT)

				val inventory = EnderMailboxInventory.loadFromTag(handler, inventoryTag, provider)
				handler.inventories[uuid] = inventory
			}

			return handler
		}

		fun get(level: ServerLevel): EnderLetterHandler {
			if (level != level.server.overworld()) {
				return get(level.server.overworld())
			}

			return level.dataStorage.computeIfAbsent(
				Factory(::EnderLetterHandler, ::load),
				"ender_letter_inventories"
			)
		}


	}

}