package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.toasts.Toast
import net.minecraft.client.gui.components.toasts.ToastComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class NotificationToast(
	private val title: String,
	private val description: String,
	private val icon: ItemStack?
) : Toast {

	//FIXME: Vanishes instead of sliding out
	override fun render(
		guiGraphics: GuiGraphics,
		toastComponent: ToastComponent,
		timeSinceLastVisible: Long
	): Toast.Visibility {

		if (timeSinceLastVisible > DISPLAY_TIME) {
			return Toast.Visibility.HIDE
		}

		guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, this.width(), this.height())

		if (this.icon != null) {
			guiGraphics.renderFakeItem(this.icon, 8, 8)
		}

		val font = toastComponent.minecraft.font

		guiGraphics.drawString(font, this.title, 30, 7, 0xFFFCFC00.toInt(), false)
		guiGraphics.drawString(font, this.description, 30, 18, 0xFFFCFCFC.toInt(), false)

		val shouldHide = timeSinceLastVisible.toFloat() >= DISPLAY_TIME * toastComponent.notificationDisplayTimeMultiplier

		return if (shouldHide) Toast.Visibility.HIDE else Toast.Visibility.SHOW
	}

	companion object {
		private val BACKGROUND_SPRITE: ResourceLocation = ResourceLocation.withDefaultNamespace("toast/advancement")
		private const val DISPLAY_TIME = 5000
	}

}