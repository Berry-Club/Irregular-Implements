package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData

class SummoningPendulumItem : Item(
    Properties()
        .stacksTo(1)
) {

    //FIXME: I should probably add a way to avoid NBT overflow or something
    override fun interactLivingEntity(
        stackIgnoreIgnoreIgnore: ItemStack,     // Why the hell is this not the actual stack? Why do I have to make a usedStack?
        player: Player,
        interactionTarget: LivingEntity,
        usedHand: InteractionHand
    ): InteractionResult {
        if (interactionTarget.type.`is`(ModEntityTypeTagsProvider.SUMMONING_PENDULUM_BLACKLIST)) return InteractionResult.PASS

        val usedStack = player.getItemInHand(usedHand)      // WHY IS THIS NECESSARY???

        val currentEntityList = usedStack.get(ModDataComponents.ENTITY_LIST) ?: emptyList()
        if (currentEntityList.size >= ServerConfig.SUMMONING_PENDULUM_CAPACITY.get()) return InteractionResult.PASS

        val entityNbt = CompoundTag()
        if (!interactionTarget.save(entityNbt)) return InteractionResult.FAIL

        val customData = CustomData.of(entityNbt)
        val newEntityList = currentEntityList + customData

        if (usedStack.set(ModDataComponents.ENTITY_LIST, newEntityList) == null) {
            return InteractionResult.FAIL
        }

        interactionTarget.remove(Entity.RemovalReason.DISCARDED)

        return InteractionResult.SUCCESS
    }

}