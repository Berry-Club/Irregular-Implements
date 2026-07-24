package dev.aaronhowser.mods.irregular_implements.client.render.block_entity

import dev.aaronhowser.mods.irregular_implements.block_entity.SpecialChestBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntityTypes
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.client.renderer.Sheets
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.ChestRenderer
import net.minecraft.client.resources.model.Material
import net.minecraft.world.level.block.state.properties.ChestType

class SpecialChestRenderer(
	context: BlockEntityRendererProvider.Context
) : ChestRenderer<SpecialChestBlockEntity>(context) {

	override fun getMaterial(blockEntity: SpecialChestBlockEntity, chestType: ChestType): Material {
		return when (blockEntity.type) {
			ModBlockEntityTypes.NATURE_CHEST.get() -> NATURE_CHEST_MATERIAL
			ModBlockEntityTypes.WATER_CHEST.get() -> WATER_CHEST_MATERIAL
			else -> super.getMaterial(blockEntity, chestType)
		}
	}

	companion object {
		private val NATURE_CHEST_MATERIAL =
			Material(Sheets.CHEST_SHEET, OtherUtil.modResource("entity/chest/nature_chest"))
		private val WATER_CHEST_MATERIAL =
			Material(Sheets.CHEST_SHEET, OtherUtil.modResource("entity/chest/water_chest"))
	}
}
