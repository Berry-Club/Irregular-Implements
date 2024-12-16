package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData

class SummoningPendulumItem : Item(
    Properties()
        .stacksTo(1)
) {

    override fun interactLivingEntity(
        stack: ItemStack,
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        if (interactionTarget.type.`is`(ModEntityTypeTagsProvider.SUMMONING_PENDULUM_BLACKLIST)) return InteractionResult.PASS

        val currentEntityList = stack.get(ModDataComponents.ENTITY_LIST) ?: emptyList()
        if (currentEntityList.size >= ServerConfig.SUMMONING_PENDULUM_CAPACITY.get()) return InteractionResult.PASS

        val entityNbt = CompoundTag()
        if (!interactionTarget.save(entityNbt)) return InteractionResult.FAIL

        val customData = CustomData.of(entityNbt)
        val newEntityList = currentEntityList + customData

        //FIXME: WHY????????
        stack.set(ModDataComponents.ENTITY_LIST, newEntityList)

        return InteractionResult.SUCCESS
    }

}