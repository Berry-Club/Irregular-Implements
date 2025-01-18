package dev.aaronhowser.mods.irregular_implements.item

import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.Item
import net.minecraft.world.item.component.ItemContainerContents

class ItemFilterItem : Item(
    Properties()
        .stacksTo(1)
        .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
)