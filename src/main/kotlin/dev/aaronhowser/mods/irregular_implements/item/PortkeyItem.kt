package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.item.component.LocationDataComponent
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.Unit
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.neoforged.neoforge.common.util.TriState
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent

//TODO: Ability to hide it as other items
class PortkeyItem : Item(
    Properties()
        .stacksTo(1)
) {

    // ModDataComponents.ENABLED means that picking up the portkey will teleport the player to the location

    companion object {
        fun pickUpPortkey(event: ItemEntityPickupEvent.Pre) {
            val itemEntity = event.itemEntity
            val itemStack = itemEntity.item

            if (!itemStack.`is`(ModItems.PORTKEY)) return

            val locationComponent = itemStack.get(ModDataComponents.LOCATION) ?: return
            if (!itemStack.has(ModDataComponents.IS_ENABLED)) return

            val player = event.player
            val level = player.level() as? ServerLevel ?: return

            if (level.dimension() != locationComponent.dimension) return

            val teleportLocation = locationComponent.blockPos.bottomCenter

            player.teleportTo(
                teleportLocation.x,
                teleportLocation.y,
                teleportLocation.z,
            )

            level.playSound(
                null,
                player.blockPosition(),
                SoundEvents.PLAYER_TELEPORT,
                player.soundSource,
            )

            event.setCanPickup(TriState.FALSE)
            itemEntity.discard()
        }
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos
        val clickedFace = context.clickedFace

        val posToTeleportTo = if (level.getBlockState(clickedPos).getCollisionShape(level, clickedPos).isEmpty) {
            clickedPos
        } else {
            clickedPos.relative(clickedFace)
        }

        val usedStack = context.itemInHand

        usedStack.set(
            ModDataComponents.LOCATION,
            LocationDataComponent(level, posToTeleportTo)
        )

        return InteractionResult.SUCCESS
    }


    override fun isFoil(stack: ItemStack): Boolean {
        return stack.has(ModDataComponents.LOCATION) && !stack.has(ModDataComponents.IS_ENABLED)
    }

    override fun onEntityItemUpdate(stack: ItemStack, entity: ItemEntity): Boolean {
        if (entity.age == 20 * 5) {
            stack.set(ModDataComponents.IS_ENABLED, Unit.INSTANCE)

            entity.setUnlimitedLifetime()

            entity.level().playSound(
                null,
                entity.blockPosition(),
                SoundEvents.BELL_BLOCK,
                entity.soundSource,
                1f,
                0.25f
            )

        }

        return super.onEntityItemUpdate(stack, entity)
    }

    //TODO: Tooltip

}