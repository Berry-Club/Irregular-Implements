package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class InventoryTesterScreen(
    menu: InventoryTesterMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<InventoryTesterMenu>(menu, playerInventory, title) {

    override val background: ScreenTextures.Background = ScreenTextures.Background.InventoryTester

    override fun baseInit() {
        this.titleLabelX = 35
        this.inventoryLabelX = 35
        this.inventoryLabelY = this.imageHeight - 94
    }

}