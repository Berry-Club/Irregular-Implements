package dev.aaronhowser.mods.irregular_implements.menu.base

import net.minecraft.world.Container
import net.minecraft.world.inventory.Slot

//TODO: Make this actually act like a ghost slot
class GhostSlot(
    container: Container,
    slot: Int,
    x: Int,
    y: Int
) : Slot(container, slot, x, y)