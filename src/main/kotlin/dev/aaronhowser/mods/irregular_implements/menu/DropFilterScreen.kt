package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.menu.base.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class DropFilterScreen(
    menu: DropFilterMenu,
    playerInventory: Inventory,
    title: Component
) : BaseScreen<DropFilterMenu>(menu, playerInventory, title) {

    override val background = ScreenTextures.Background.DropFilter

    override fun baseInit() {
        this.inventoryLabelY = this.imageHeight - 94
    }

}