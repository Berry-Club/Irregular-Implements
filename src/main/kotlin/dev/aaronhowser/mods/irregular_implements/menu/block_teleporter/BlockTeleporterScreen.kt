package dev.aaronhowser.mods.irregular_implements.menu.block_teleporter

import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class BlockTeleporterScreen(
	menu: BlockTeleporterMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<BlockTeleporterMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.blockTeleporter

	override fun baseInit() {
		this.inventoryLabelY = this.imageHeight - 94
	}

}