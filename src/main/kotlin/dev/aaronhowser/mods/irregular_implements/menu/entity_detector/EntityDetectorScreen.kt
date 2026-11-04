package dev.aaronhowser.mods.irregular_implements.menu.entity_detector

import dev.aaronhowser.mods.aaron.menu.BaseScreen
import dev.aaronhowser.mods.aaron.menu.components.ChangingTextButton
import dev.aaronhowser.mods.aaron.menu.components.MultiStageSpriteButton
import dev.aaronhowser.mods.aaron.menu.textures.ScreenBackground
import dev.aaronhowser.mods.aaron.packet.c2s.ClientClickedMenuButton
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModTooltipLang
import dev.aaronhowser.mods.irregular_implements.menu.ScreenTextures
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class EntityDetectorScreen(
	menu: EntityDetectorMenu,
	playerInventory: Inventory,
	title: Component
) : BaseScreen<EntityDetectorMenu>(menu, playerInventory, title) {

	override val background: ScreenBackground = ScreenTextures.Backgrounds.ENTITY_DETECTOR

	private lateinit var buttonLowerX: Button
	private lateinit var buttonRaiseX: Button
	private lateinit var buttonLowerY: Button
	private lateinit var buttonRaiseY: Button
	private lateinit var buttonLowerZ: Button
	private lateinit var buttonRaiseZ: Button
	private lateinit var buttonFilterType: Button
	private lateinit var buttonToggleInversion: Button

	override fun baseInit() {

		val lowerButtonLeft = guiLeft + 29
		val raiseButtonLeft = guiLeft + 126

		val xButtonTop = guiTop + 20
		val yButtonTop = guiTop + 45
		val zButtonsTop = guiTop + 70

		titleLabelX = 24

		buttonLowerX = Button.Builder(
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

		buttonRaiseX = Button.Builder(
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


		buttonLowerY = Button.Builder(
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


		buttonRaiseY = Button.Builder(
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


		buttonLowerZ = Button.Builder(
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


		buttonRaiseZ = Button.Builder(
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

		addRenderableWidget(buttonLowerX)
		addRenderableWidget(buttonRaiseX)
		addRenderableWidget(buttonLowerY)
		addRenderableWidget(buttonRaiseY)
		addRenderableWidget(buttonLowerZ)
		addRenderableWidget(buttonRaiseZ)

		val bottomButtonY = topPos + 95

		buttonFilterType = ChangingTextButton(
			x = lowerButtonLeft,
			y = bottomButtonY,
			height = 20,
			width = 90,
			messageGetter = {
				menu.getFilter().component
			},
			onPress = {
				val packet = ClientClickedMenuButton(EntityDetectorMenu.CYCLE_FILTER_BUTTON_ID)
				packet.messageServer()
			}
		)

		buttonToggleInversion = MultiStageSpriteButton.Builder(font)
			.size(20)
			.location(
				x = rightPos - 29 - 20,
				y = bottomButtonY
			)
			.addStage(
				message = ModTooltipLang.UNINVERTED.toComponent(),
				sprite = ScreenTextures.Sprites.UNINVERTED
			)
			.addStage(
				message = ModTooltipLang.INVERTED.toComponent(),
				sprite = ScreenTextures.Sprites.INVERTED
			)
			.currentStageGetter(
				currentStageGetter = { if (menu.isInverted) 1 else 0 }
			)
			.onPress(
				onPress = {
					val packet = ClientClickedMenuButton(EntityDetectorMenu.TOGGLE_INVERTED_BUTTON_ID)
					packet.messageServer()
				}
			)
			.build()

		addRenderableWidget(buttonFilterType)
		addRenderableWidget(buttonToggleInversion)
	}

	override fun render(guiGraphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
		super.render(guiGraphics, mouseX, mouseY, partialTick)
		drawRadiusTexts(guiGraphics)
	}

	private fun drawRadiusTexts(guiGraphics: GuiGraphics) {

		val middle = leftPos + imageWidth / 2

		val xChars = ModMessageLang.X_RADIUS
			.toComponent(menu.xRadius)
			.visualOrderText

		guiGraphics.drawString(
			font,
			xChars,
			middle - font.width(xChars) / 2,
			guiTop + 20 + 6,
			0x404040,
			false
		)

		val yChars = ModMessageLang.Y_RADIUS
			.toComponent(menu.yRadius)
			.visualOrderText

		guiGraphics.drawString(
			font,
			yChars,
			middle - font.width(yChars) / 2,
			guiTop + 45 + 6,
			0x404040,
			false
		)

		val zChars = ModMessageLang.Z_RADIUS
			.toComponent(menu.zRadius)
			.visualOrderText

		guiGraphics.drawString(
			font,
			zChars,
			middle - font.width(zChars) / 2,
			guiTop + 70 + 6,
			0x404040,
			false
		)

	}

	override val showInventoryLabel: Boolean = false

}