package dev.aaronhowser.mods.irregular_implements.client.render.bewlr

import com.mojang.blaze3d.vertex.PoseStack
import dev.aaronhowser.mods.irregular_implements.block.block_entity.SpecialChestBlockEntity
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
import net.minecraft.core.BlockPos
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions

class SpecialChestBEWLR : BlockEntityWithoutLevelRenderer(
	Minecraft.getInstance().blockEntityRenderDispatcher,
	Minecraft.getInstance().entityModels
) {

	val dispatcher: BlockEntityRenderDispatcher = Minecraft.getInstance().blockEntityRenderDispatcher

	val waterChest = SpecialChestBlockEntity.WaterChestBlockEntity(BlockPos.ZERO, ModBlocks.WATER_CHEST.get().defaultBlockState())
	val natureChest = SpecialChestBlockEntity.NatureChestBlockEntity(BlockPos.ZERO, ModBlocks.NATURE_CHEST.get().defaultBlockState())

	override fun renderByItem(
		stack: ItemStack,
		displayContext: ItemDisplayContext,
		poseStack: PoseStack,
		buffer: MultiBufferSource,
		packedLight: Int,
		packedOverlay: Int
	) {
		val chestToRender = when (stack.item) {
			ModBlocks.WATER_CHEST.get().asItem() -> waterChest
			ModBlocks.NATURE_CHEST.get().asItem() -> natureChest
			else -> return
		}

		dispatcher.renderItem(chestToRender, poseStack, buffer, packedLight, packedOverlay)
	}

	object ClientItemExtensions : IClientItemExtensions {
		val BEWLR = SpecialChestBEWLR()

		override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
			return BEWLR
		}
	}

}