package dev.aaronhowser.mods.irregular_implements.menu.block_detector

import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.IIScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BlockDetectorScreen(
	menu: BlockDetectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<BlockDetectorMenu>(menu, playerInventory, title) {

	override val background: IIScreenTextures.Background = IIScreenTextures.Background.BlockDetector

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}