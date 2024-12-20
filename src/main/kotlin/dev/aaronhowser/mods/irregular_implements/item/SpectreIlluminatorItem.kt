package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.entity.IlluminatorEntity
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext

class SpectreIlluminatorItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        val clickedPos = context.clickedPos

        if (IlluminatorEntity.isChunkIlluminated(clickedPos, level)) {
            context.player?.sendSystemMessage(Component.literal("No!"))
            return InteractionResult.FAIL
        }

        val clickedFace = context.clickedFace

        val spawnPos = clickedPos.relative(clickedFace).center

        val entity = IlluminatorEntity(level)
        entity.setPos(spawnPos.x, spawnPos.y, spawnPos.z)

        level.addFreshEntity(entity)

        return InteractionResult.SUCCESS
    }

}