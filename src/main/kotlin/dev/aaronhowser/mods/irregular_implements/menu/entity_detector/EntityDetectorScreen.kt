package dev.aaronhowser.mods.irregular_implements.menu.entity_detector

import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.menu.BaseScreen
import dev.aaronhowser.mods.irregular_implements.menu.ChangingTextButton
import dev.aaronhowser.mods.irregular_implements.menu.ImprovedSpriteButton
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import dev.aaronhowser.mods.irregular_implements.packet.client_to_server.ClientClickedMenuButton
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class EntityDetectorScreen(
	menu: EntityDetectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EntityDetectorMenu>(menu, playerInventory, title) {

	override val background: ScreenTextures.Background = ScreenTextures.Background.EntityDetector

	private lateinit var buttonLowerX: Button
	private lateinit var buttonRaiseX: Button
	private lateinit var buttonLowerY: Button
	private lateinit var buttonRaiseY: Button
	private lateinit var buttonLowerZ: Button
	private lateinit var buttonRaiseZ: Button
	private lateinit var buttonFilterType: Button
	private lateinit var buttonToggleInversion: Button

	override fun baseInit() {

		val lowerButtonLeft = this.guiLeft + 29
		val raiseButtonLeft = this.guiLeft + 126

		val xButtonTop = this.guiTop + 20
		val yButtonTop = this.guiTop + 45
		val zButtonsTop = this.guiTop + 70

		this.titleLabelX = 24

		this.buttonLowerX = Button.Builder(
			ModMessageLang.MINUS_X.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.LOWER_X_BUTTON_ID)
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
			ModMessageLang.PLUS_X.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.RAISE_X_BUTTON_ID)
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
			ModMessageLang.MINUS_Y.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.LOWER_Y_BUTTON_ID)
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
			ModMessageLang.PLUS_Y.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.RAISE_Y_BUTTON_ID)
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
			ModMessageLang.MINUS_Z.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.LOWER_Z_BUTTON_ID)
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
			ModMessageLang.PLUS_Z.toComponent()
		) {
			val packet = ClientClickedMenuButton(EntityDetectorMenu.RAISE_Z_BUTTON_ID)
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

		val bottomButtonY = this.topPos + 95

		this.buttonFilterType = ChangingTextButton(
			x = lowerButtonLeft,
			y = bottomButtonY,
			height = 20,
			width = 90,
			messageGetter = {
				this.menu.getFilter().component
			},
			onPress = {
				val packet = ClientClickedMenuButton(EntityDetectorMenu.CYCLE_FILTER_BUTTON_ID)
				packet.messageServer()
			}
		)

		this.addRenderableWidget(this.buttonFilterType)
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