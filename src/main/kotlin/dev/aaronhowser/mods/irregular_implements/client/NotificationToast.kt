package dev.aaronhowser.mods.irregular_implements.client

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.toasts.Toast
import net.minecraft.client.gui.components.toasts.ToastComponent
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack

class NotificationToast(
    private val title: String,
    private val description: String,
    private val icon: ItemStack?
) : Toast {

    companion object {
        private val BACKGROUND_SPRITE: ResourceLocation = ResourceLocation.withDefaultNamespace("toast/advancement")
        private const val DISPLAY_TIME = 5000
    }

    override fun render(
        guiGraphics: GuiGraphics,
        toastComponent: ToastComponent,
        timeSinceLastVisible: Long
    ): Toast.Visibility {

        if (this.icon != null) {
            guiGraphics.renderFakeItem(this.icon, 8, 8)
        }

        val font = toastComponent.minecraft.font
        val characters = font.split(Component.literal(title), 125)

        return Toast.Visibility.SHOW
    }
}