package dev.aaronhowser.mods.irregular_implements.menu

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.menu.base.MultiStateSpriteButton
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

    fun setButtons() {
        this.toggleTypeButtons.clear()
        this.toggleNeedsComponentButtons.clear()

        val filter = this.menu.filter ?: return

        addToggleBlacklistButton()

        for (index in 0 until 9) {
            val entry = filter.getOrNull(index) ?: continue

            addToggleTypeButton(index, entry)
            addToggleNeedsComponentButton(index, entry)
        }
    }

    override fun containerTick() {
        super.containerTick()
        setButtons()
    }

    private fun addToggleBlacklistButton() {

        val x = this.leftPos + this.imageWidth - 24
        val y = this.topPos + 5

        val onPress = {
            ModPacketHandler.messageServer(ClientClickedMenuButton(ItemFilterMenu.TOGGLE_BLACKLIST_BUTTON_ID))
        }

        this.invertBlacklistButton = MultiStateSpriteButton.Builder(this.font)
            .location(x, y)
            .size(16)
            .addState(
                message = ModLanguageProvider.Tooltips.WHITELIST.toComponent(),
                menuSprite = ScreenTextures.Sprites.ItemFilter.Whitelist
            )
            .addState(
                message = ModLanguageProvider.Tooltips.BLACKLIST.toComponent(),
                menuSprite = ScreenTextures.Sprites.ItemFilter.Blacklist
            )
            .currentStateGetter(
                currentStateGetter = { if (this.menu.isBlacklist) 1 else 0 }
            )
            .onPress(onPress)
            .build()

        this.addRenderableWidget(this.invertBlacklistButton)
    }

    private fun addToggleTypeButton(index: Int, entry: FilterEntry) {
        if (entry is FilterEntry.Empty) return

        val x = this.leftPos + 8 + index * 18
        val y = this.topPos + 15

        val width = if (entry is FilterEntry.Tag) 16 else 8
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

        this.toggleTypeButtons.add(button)
        this.addRenderableWidget(button)
    }

    private fun addToggleNeedsComponentButton(index: Int, entry: FilterEntry) {
        if (entry !is FilterEntry.Item) return

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