package dev.aaronhowser.mods.irregular_implements.menu

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.ImageButton
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ClickType
import net.minecraft.world.inventory.Slot

// This class is pretty much a blind copy of CraftingScreen
class CustomCraftingTableScreen(
    menu: CustomCraftingTableMenu,
    playerInventory: Inventory,
    title: Component
) : AbstractContainerScreen<CustomCraftingTableMenu>(menu, playerInventory, title), RecipeUpdateListener {

    companion object {
        val CRAFTING_TABLE_LOCATION: ResourceLocation = ResourceLocation.withDefaultNamespace("textures/gui/container/crafting_table.png")
    }

    private val recipeBookComponent = RecipeBookComponent()
    private var widthTooNarrow = false

    override fun init() {
        super.init()
        this.widthTooNarrow = this.width < 379
        recipeBookComponent.init(this.width, this.height, this.minecraft!!, this.widthTooNarrow, this.menu)
        this.leftPos = recipeBookComponent.updateScreenPosition(this.width, this.imageWidth)

        val recipeButton = ImageButton(
            this.leftPos + 5,
            this.height / 2 - 49,
            20,
            18,
            RecipeBookComponent.RECIPE_BUTTON_SPRITES
        ) { button: Button ->
            recipeBookComponent.toggleVisibility()
            this.leftPos = recipeBookComponent.updateScreenPosition(this.width, this.imageWidth)
            button.setPosition(this.leftPos + 5, this.height / 2 - 49)
        }

        this.addRenderableWidget(recipeButton)

        this.addWidget(this.recipeBookComponent)
        this.titleLabelX = 29
    }

    public override fun containerTick() {
        super.containerTick()
        recipeBookComponent.tick()
    }

    override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        if (recipeBookComponent.isVisible && this.widthTooNarrow) {
            this.renderBackground(guiGraphics, mouseX, mouseY, partialTick)
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick)
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTick)
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick)
            recipeBookComponent.renderGhostRecipe(guiGraphics, this.leftPos, this.topPos, true, partialTick)
        }

        this.renderTooltip(guiGraphics, mouseX, mouseY)
        recipeBookComponent.renderTooltip(guiGraphics, this.leftPos, this.topPos, mouseX, mouseY)
    }

    override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
        val i = this.leftPos
        val j = (this.height - this.imageHeight) / 2
        guiGraphics.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        return if (recipeBookComponent.keyPressed(keyCode, scanCode, modifiers)) true else super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun charTyped(codePoint: Char, modifiers: Int): Boolean {
        return if (recipeBookComponent.charTyped(codePoint, modifiers)) true else super.charTyped(codePoint, modifiers)
    }

    override fun isHovering(x: Int, y: Int, width: Int, height: Int, mouseX: Double, mouseY: Double): Boolean {
        return (!this.widthTooNarrow || !recipeBookComponent.isVisible) && super.isHovering(x, y, width, height, mouseX, mouseY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        if (recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            this.focused = this.recipeBookComponent
            return true
        } else {
            return if (this.widthTooNarrow && recipeBookComponent.isVisible) true else super.mouseClicked(mouseX, mouseY, button)
        }
    }

    override fun hasClickedOutside(mouseX: Double, mouseY: Double, guiLeft: Int, guiTop: Int, mouseButton: Int): Boolean {
        val flag = (mouseX < guiLeft.toDouble() || mouseY < guiTop.toDouble() || mouseX >= (guiLeft + this.imageWidth).toDouble()) || mouseY >= (guiTop + this.imageHeight).toDouble()
        return recipeBookComponent.hasClickedOutside(mouseX, mouseY, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, mouseButton) && flag
    }

    override fun slotClicked(slot: Slot, slotId: Int, mouseButton: Int, type: ClickType) {
        super.slotClicked(slot, slotId, mouseButton, type)
        recipeBookComponent.slotClicked(slot)
    }

    override fun recipesUpdated() {
        recipeBookComponent.recipesUpdated()
    }

    override fun getRecipeBookComponent(): RecipeBookComponent {
        return this.recipeBookComponent
    }
}