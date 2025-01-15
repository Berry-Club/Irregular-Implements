package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.client.gui.components.Button
import net.minecraft.network.chat.Component
import java.util.function.Supplier

class ChangingTextButton(
    x: Int,
    y: Int,
    width: Int,
    height: Int,
    private val messageGetter: Supplier<Component>,
    onPress: OnPress
) : Button(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION) {

    override fun getMessage(): Component {
        return this.messageGetter.get()
    }

}