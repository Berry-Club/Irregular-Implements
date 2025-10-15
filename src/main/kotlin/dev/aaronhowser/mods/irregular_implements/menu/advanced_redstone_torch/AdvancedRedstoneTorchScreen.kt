package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_torch

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AdvancedRedstoneTorchScreen(
	menu: AdvancedRedstoneTorchMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AdvancedRedstoneTorchMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.AdvancedRedstoneTorch
	override val showInventoryLabel: Boolean = false
	override val showTitleLabel: Boolean = false

	private lateinit var decreaseGreenStrengthButton: Button
	private lateinit var increaseGreenStrengthButton: Button
	private lateinit var decreaseRedStrengthButton: Button
	private lateinit var increaseRedStrengthButton: Button

	override fun baseInit() {

	}

	override fun renderLabels(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int) {

		val centerX = this.imageWidth / 2

		val greenStrengthText = ModMessageLang.GREEN_STRENGTH.toComponent()
		val greenStrength = this.menu.strengthGreen.toString()
		val redStrengthText = ModMessageLang.RED_STRENGTH.toComponent()
		val redStrength = this.menu.strengthRed.toString()

		guiGraphics.drawString(
			font,
			greenStrengthText,
			centerX - font.width(greenStrengthText) / 2,
			10,
			4210752,
			false
		)

		guiGraphics.drawString(
			font,
			greenStrength,
			centerX - font.width(greenStrength) / 2,
			20,
			4210752,
			false
		)

		guiGraphics.drawString(
			font,
			redStrengthText,
			centerX - font.width(redStrengthText) / 2,
			30,
			4210752,
			false
		)

		guiGraphics.drawString(
			font,
			redStrength,
			centerX - font.width(redStrength) / 2,
			40,
			4210752,
			false
		)

	}

}