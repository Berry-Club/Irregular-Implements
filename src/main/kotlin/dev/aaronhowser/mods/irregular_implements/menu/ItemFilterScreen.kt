package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.item.component.ItemFilterDataComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

//TODO: Make buttons function
class ItemFilterScreen(
    menu: ItemFilterMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<ItemFilterMenu>(menu, playerInventory, title) {

    private val background = ScreenTextures.Backgrounds.ItemFilter

    private val toggleTypeButtons: MutableSet<Button> = mutableSetOf()
    private val toggleNeedsComponentButtons: MutableSet<Button> = mutableSetOf()

    private lateinit var invertBlacklistButton: Button

    override fun init() {
        this.imageWidth = background.width
        this.imageHeight = background.height

        this.inventoryLabelY = this.imageHeight - 94

        this.leftPos = (this.width - this.imageWidth) / 2
        this.topPos = (this.height - this.imageHeight) / 2

        setButtons()
    }

    private fun setButtons() {
        this.toggleTypeButtons.clear()
        this.toggleNeedsComponentButtons.clear()

        val filter = this.menu.filter ?: return

        for (index in 0 until 9) {
            val entry = filter.elementAtOrNull(index) ?: continue

            addToggleTypeButton(index, entry)
            addToggleNeedsComponentButton(index, entry)
        }

        this.invertBlacklistButton = Button.Builder(Component.empty(), { })
            .bounds(
                this.leftPos + 8,
                this.topPos + 5,
                8,
                8
            )
            .build()

        this.addRenderableWidget(this.invertBlacklistButton)

    }

    private fun addToggleTypeButton(index: Int, entry: ItemFilterDataComponent.FilterEntry) {
        val x = this.leftPos + 8 + index * 18
        val y = this.topPos + 15

        val width = if (entry is ItemFilterDataComponent.FilterEntry.ItemTag) 16 else 8
        val height = 8

        val button = Button.Builder(Component.empty(), { })
            .bounds(
                x, y,
                width, height
            )
            .build()

        this.toggleTypeButtons.add(button)
        this.addRenderableWidget(button)
    }

    private fun addToggleNeedsComponentButton(index: Int, entry: ItemFilterDataComponent.FilterEntry) {
        if (entry !is ItemFilterDataComponent.FilterEntry.SpecificItem) return

        val x = this.leftPos + 8 + index * 18 + 9
        val y = this.topPos + 15

        val width = 8
        val height = 8

        val button = Button.Builder(Component.empty(), { })
            .bounds(
                x, y,
                width, height
            )
            .build()

        this.toggleNeedsComponentButtons.add(button)
        this.addRenderableWidget(button)
    }

    // Render stuff

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        super.render(guiGraphics, mouseX, mouseY, partialTick)
        this.renderTooltip(guiGraphics, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        this.background.render(
            guiGraphics,
            this.leftPos,
            this.topPos,
        )
    }

}