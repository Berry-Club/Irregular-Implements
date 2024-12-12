package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.registry.ModBlocks
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class PortableEnderBridgeItem : Item(
    Properties()
        .stacksTo(1)
) {

    //TODO: Sounds, particles
    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val usedStack = player.getItemInHand(usedHand)
        if (level.isClientSide) return InteractionResultHolder.pass(usedStack)

        val clipResult = OtherUtil.getPovResult(level, player, ServerConfig.PORTABLE_ENDER_BRIDGE_RANGE.get())

        val pos = clipResult.blockPos
        val state = level.getBlockState(pos)

        if (!state.`is`(ModBlocks.ENDER_ANCHOR)) return InteractionResultHolder.fail(usedStack)

        val stateAbove = level.getBlockState(pos.above())
        val stateTwoAbove = level.getBlockState(pos.above(2))

        if (!stateAbove.isAir || !stateTwoAbove.isAir) return InteractionResultHolder.fail(usedStack)

        player.teleportTo(pos.x + 0.5, pos.y + 1.0, pos.z + 0.5)

        return InteractionResultHolder.success(usedStack)
    }

}