package dev.aaronhowser.mods.irregular_implements.item

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toGrayComponent
import dev.aaronhowser.mods.irregular_implements.datagen.tag.ModEntityTypeTagsProvider
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import net.minecraft.ChatFormatting
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.context.UseOnContext

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

    override fun useOn(context: UseOnContext): InteractionResult {
        val stack = context.itemInHand
        val component = stack.get(ModDataComponents.ENTITY_LIST) ?: return InteractionResult.PASS

        val entityData = component.lastOrNull() ?: return InteractionResult.PASS

        val level = context.level

        val clickedPos = context.clickedPos
        val clickedFace = context.clickedFace
        val clickedState = level.getBlockState(clickedPos)

        val posToSpawn = if (clickedState.isSuffocating(level, clickedPos)) {
            val relative = clickedPos.relative(clickedFace)
            if (level.getBlockState(relative).isSuffocating(level, relative)) {
                return InteractionResult.FAIL
            }

            relative
        } else {
            clickedPos
        }

        val entityTypeString = entityData.copyTag().getString("id")
        val entityType = level.registryAccess()
            .registryOrThrow(Registries.ENTITY_TYPE)
            .get(ResourceLocation.parse(entityTypeString)) ?: return InteractionResult.FAIL

        val entity = entityType.create(level) ?: return InteractionResult.FAIL

        val newEntityList = component.dropLast(1)
        if (stack.set(ModDataComponents.ENTITY_LIST, newEntityList) == null) {
            return InteractionResult.FAIL
        }

        entityData.loadInto(entity)
        entity.moveTo(posToSpawn.bottomCenter)
        level.addFreshEntity(entity)

        return InteractionResult.SUCCESS
    }

    override fun appendHoverText(
        stack: ItemStack,
        context: TooltipContext,
        tooltipComponents: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val customDataList: List<CustomData> = stack.get(ModDataComponents.ENTITY_LIST) ?: emptyList()

        val amount = customDataList.size
        val capacity = ServerConfig.SUMMONING_PENDULUM_CAPACITY.get()

        tooltipComponents.add(
            ModLanguageProvider.Tooltips.SUMMONING_PENDULUM_FRACTION
                .toGrayComponent(amount, capacity)
        )

        if (tooltipFlag.hasShiftDown()) {
            for (customData in customDataList) {
                val entityNbt = customData.copyTag()

                val entityName: Component = if (entityNbt.contains("CustomName")) {
                    val customName = entityNbt.getString("CustomName")
                    Component.literal(customName)
                } else {
                    val entityTypeString = entityNbt.getString("id")
                    val entityType = context.level()
                        ?.registryAccess()
                        ?.registryOrThrow(Registries.ENTITY_TYPE)
                        ?.get(ResourceLocation.parse(entityTypeString))
                        ?: continue

                    entityType.getDescription()
                }

                tooltipComponents.add(
                    ModLanguageProvider.Tooltips.LIST_POINT
                        .toGrayComponent(entityName)
                )
            }
        }

    }

}