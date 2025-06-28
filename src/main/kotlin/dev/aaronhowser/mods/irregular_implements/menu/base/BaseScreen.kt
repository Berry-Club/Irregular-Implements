package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu

abstract class BaseScreen<M : AbstractContainerMenu>(
	menu: M,
	playerInventory: Inventory,
	title: Component
) : AbstractContainerScreen<M>(menu, playerInventory, title) {

	protected abstract val background: ScreenTextures.Background

	protected val rightPos: Int
		get() = this.leftPos + this.imageWidth
	protected val bottomPos: Int
		get() = this.topPos + this.imageHeight

	final override fun init() {
		this.imageWidth = background.width
		this.imageHeight = background.height

		this.leftPos = (this.width - this.imageWidth) / 2
		this.topPos = (this.height - this.imageHeight) / 2

		baseInit()
	}

	open fun baseInit() {

	}

	override fun isPauseScreen(): Boolean {
		return false
	}

	protected open val showTitleLabel = true
	protected open val showInventoryLabel = true

	override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {
		if (showTitleLabel) {
			guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false)
		}

		if (showInventoryLabel) {
			guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false)
		}
	}

	override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		super.render(guiGraphics, mouseX, mouseY, partialTick)
		this.renderTooltip(guiGraphics, mouseX, mouseY)
	}

	override fun renderBg(guiGraphics: GuiGraphics, partialTick: Float, mouseX: Int, mouseY: Int) {
		this.background.render(
			guiGraphics = guiGraphics,
			leftPos = this.leftPos,
			topPos = this.topPos
		)
	}
}