package dev.aaronhowser.mods.irregular_implements.menu.advanced_item_collector

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class AdvancedItemCollectorScreen(
	menu: AdvancedItemCollectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<AdvancedItemCollectorMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.AdvancedItemCollector

	private lateinit var buttonLowerX: Button
	private lateinit var buttonRaiseX: Button
	private lateinit var buttonLowerY: Button
	private lateinit var buttonRaiseY: Button
	private lateinit var buttonLowerZ: Button
	private lateinit var buttonRaiseZ: Button

	override fun baseInit() {

		val lowerButtonLeft = this.guiLeft + 29
		val raiseButtonLeft = this.guiLeft + 127

		val xButtonTop = this.guiTop + 20
		val yButtonTop = this.guiTop + 45
		val zButtonsTop = this.guiTop + 70

		this.titleLabelX = 26

		this.buttonLowerX = Button.Builder(
			Component.literal("-X")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.LOWER_X_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				lowerButtonLeft,
				xButtonTop,
				20,
				20
			)
			.build()

		this.buttonRaiseX = Button.Builder(
			Component.literal("+X")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.RAISE_X_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				raiseButtonLeft,
				xButtonTop,
				20,
				20
			)
			.build()


		this.buttonLowerY = Button.Builder(
			Component.literal("-Y")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.LOWER_Y_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				lowerButtonLeft,
				yButtonTop,
				20,
				20
			)
			.build()


		this.buttonRaiseY = Button.Builder(
			Component.literal("+Y")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.RAISE_Y_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				raiseButtonLeft,
				yButtonTop,
				20,
				20
			)
			.build()


		this.buttonLowerZ = Button.Builder(
			Component.literal("-Z")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.LOWER_Z_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				lowerButtonLeft,
				zButtonsTop,
				20,
				20
			)
			.build()


		this.buttonRaiseZ = Button.Builder(
			Component.literal("-X")
		) {
			val packet = ClientClickedMenuButton(AdvancedItemCollectorMenu.RAISE_Z_BUTTON_ID)
			packet.messageServer()
		}
			.bounds(
				raiseButtonLeft,
				zButtonsTop,
				20,
				20
			)
			.build()

		this.addRenderableWidget(this.buttonLowerX)
		this.addRenderableWidget(this.buttonRaiseX)
		this.addRenderableWidget(this.buttonLowerY)
		this.addRenderableWidget(this.buttonRaiseY)
		this.addRenderableWidget(this.buttonLowerZ)
		this.addRenderableWidget(this.buttonRaiseZ)
	}

	override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		super.render(guiGraphics, mouseX, mouseY, partialTick)
		drawRadiusTexts(guiGraphics)
	}

	private fun drawRadiusTexts(guiGraphics: GuiGraphics) {

		val middle = this.leftPos + this.imageWidth / 2

		val xChars = ModMessageLang.X_RADIUS
			.toComponent(this.menu.xRadius)
			.visualOrderText

		guiGraphics.drawString(
			this.font,
			xChars,
			middle - font.width(xChars) / 2,
			this.guiTop + 20 + 6,
			0x404040,
			false
		)

		val yChars = ModMessageLang.Y_RADIUS
			.toComponent(this.menu.yRadius)
			.visualOrderText

		guiGraphics.drawString(
			this.font,
			yChars,
			middle - font.width(yChars) / 2,
			this.guiTop + 45 + 6,
			0x404040,
			false
		)

		val zChars = ModMessageLang.Z_RADIUS
			.toComponent(this.menu.zRadius)
			.visualOrderText

		guiGraphics.drawString(
			this.font,
			zChars,
			middle - font.width(zChars) / 2,
			this.guiTop + 70 + 6,
			0x404040,
			false
		)

	}

	override val showInventoryLabel: Boolean = false

}