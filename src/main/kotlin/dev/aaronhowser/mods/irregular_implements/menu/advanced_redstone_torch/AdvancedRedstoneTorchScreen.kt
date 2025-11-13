package dev.aaronhowser.mods.irregular_implements.menu.advanced_redstone_torch

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AdvancedRedstoneTorchScreen(
	menu: AdvancedRedstoneTorchMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AdvancedRedstoneTorchMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.ADVANCED_REDSTONE_TORCH
	override val showInventoryLabel: Boolean = false
	override val showTitleLabel: Boolean = false

	private lateinit var decreaseGreenStrengthButton: Button
	private lateinit var increaseGreenStrengthButton: Button
	private lateinit var decreaseRedStrengthButton: Button
	private lateinit var increaseRedStrengthButton: Button

	override fun baseInit() {

		val decreaseComponent = Component.literal("-")
		val increaseComponent = Component.literal("+")

		val leftX = this.guiLeft + 5
		val rightX = this.rightPos - 5

		val greenY = this.guiTop + 16
		val redY = this.guiTop + 39

		this.decreaseGreenStrengthButton = Button.Builder(
			decreaseComponent
		) {
			val packet = ClientClickedMenuButton(AdvancedRedstoneTorchMenu.DECREASE_GREEN_POWER_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(leftX, greenY, 10, 10)
			.build()

		this.increaseGreenStrengthButton = Button.Builder(
			increaseComponent
		) {
			val packet = ClientClickedMenuButton(AdvancedRedstoneTorchMenu.INCREASE_GREEN_POWER_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(rightX - 10, greenY, 10, 10)
			.build()

		this.decreaseRedStrengthButton = Button.Builder(
			decreaseComponent
		) {
			val packet = ClientClickedMenuButton(AdvancedRedstoneTorchMenu.DECREASE_RED_POWER_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(leftX, redY, 10, 10)
			.build()

		this.increaseRedStrengthButton = Button.Builder(
			increaseComponent
		) {
			val packet = ClientClickedMenuButton(AdvancedRedstoneTorchMenu.INCREASE_RED_POWER_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(rightX - 10, redY, 10, 10)
			.build()

		addRenderableWidget(decreaseGreenStrengthButton)
		addRenderableWidget(increaseGreenStrengthButton)
		addRenderableWidget(decreaseRedStrengthButton)
		addRenderableWidget(increaseRedStrengthButton)

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
			7,
			4210752,
			false
		)

		guiGraphics.drawString(
			font,
			greenStrength,
			centerX - font.width(greenStrength) / 2,
			18,
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