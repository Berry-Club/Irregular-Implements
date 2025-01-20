package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStageSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.base.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.util.FilterEntry
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.Button.OnPress
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

        addToggleBlacklistButton()

        for (index in 0 until 9) {
            addToggleTypeButton(index)
            addToggleNeedsComponentButton(index)
        }
    }

    private fun addToggleBlacklistButton() {

        val x = this.leftPos + this.imageWidth - 24
        val y = this.topPos + 5

        val onPress = {
            ModPacketHandler.messageServer(ClientClickedMenuButton(ItemFilterMenu.TOGGLE_BLACKLIST_BUTTON_ID))
        }

        this.invertBlacklistButton = MultiStageSpriteButton.Builder(this.font)
            .location(x, y)
            .size(16)
            .addStage(
                message = ModLanguageProvider.Tooltips.WHITELIST.toComponent(),
                menuSprite = ScreenTextures.Sprites.ItemFilter.Whitelist
            )
            .addStage(
                message = ModLanguageProvider.Tooltips.BLACKLIST.toComponent(),
                menuSprite = ScreenTextures.Sprites.ItemFilter.Blacklist
            )
            .currentStageGetter(
                currentStageGetter = { if (this.menu.isBlacklist) 1 else 0 }
            )
            .onPress(onPress)
            .build()

        this.addRenderableWidget(this.invertBlacklistButton)
    }

    private fun addToggleTypeButton(index: Int) {

        val x = this.leftPos + 8 + index * 18
        val y = this.topPos + 15

        val filterAtIndex = this.menu.filter?.getOrNull(index)

        val width = if (filterAtIndex is FilterEntry.Tag) 16 else 8
        val height = 8

        val buttonId = ItemFilterMenu.getToggleTypeButtonId(index)

        val onPress = OnPress {
            ModPacketHandler.messageServer(ClientClickedMenuButton(buttonId))
        }

        val button = Button.Builder(Component.empty(), onPress)
            .bounds(
                x, y,
                width, height
            )
            .build()

        button.visible = filterAtIndex != null && filterAtIndex !is FilterEntry.Empty

        this.toggleTypeButtons.add(button)
        this.addRenderableWidget(button)
    }

    private fun addToggleNeedsComponentButton(index: Int) {
        val x = this.leftPos + 8 + index * 18 + 9
        val y = this.topPos + 15

        val width = 8
        val height = 8

        val buttonId = ItemFilterMenu.getToggleNeedsComponentButtonId(index)
        val onPress = OnPress {
            ModPacketHandler.messageServer(ClientClickedMenuButton(buttonId))
        }

        val button = Button.Builder(Component.empty(), onPress)
            .bounds(
                x, y,
                width, height
            )
            .build()

        button.visible = this.menu.filter?.getOrNull(index) is FilterEntry.Item

        this.toggleNeedsComponentButtons.add(button)
        this.addRenderableWidget(button)
    }

    override fun containerTick() {
        super.containerTick()

        for (buttonIndex in this.toggleTypeButtons.indices) {
            val button = this.toggleTypeButtons.elementAtOrNull(buttonIndex) ?: continue

            val entry = this.menu.filter?.getOrNull(buttonIndex)

            button.visible = entry != null && entry !is FilterEntry.Empty

            button.width = if (entry is FilterEntry.Tag) 16 else 8
        }

        for (buttonIndex in this.toggleNeedsComponentButtons.indices) {
            val button = this.toggleNeedsComponentButtons.elementAtOrNull(buttonIndex) ?: continue
            val entry = this.menu.filter?.getOrNull(buttonIndex)

            button.visible = entry is FilterEntry.Item
        }
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