package dev.aaronhowser.mods.irregular_implements.client.render

import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import dev.aaronhowser.mods.irregular_implements.item.DiviningRodItem
import dev.aaronhowser.mods.irregular_implements.registry.ModDataComponents
import dev.aaronhowser.mods.irregular_implements.util.ClientUtil
import net.minecraft.core.BlockPos
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.ClientTickEvent
import net.neoforged.neoforge.client.event.RenderLevelStageEvent

@EventBusSubscriber(
    modid = IrregularImplements.ID,
    value = [Dist.CLIENT]
)
object DiviningRodRenderer {

    private val positionsToCheck: LinkedHashSet<BlockPos> = linkedSetOf()
    private val indicators: MutableList<Indicator> = mutableListOf()

    private class Indicator(val target: BlockPos, var duration: Int, val oreTag: TagKey<Block>) {
        val color = DiviningRodItem.getColorForBlockTag(oreTag)
    }

    //TODO: Probably laggy, maybe make it only check once a second?
    @SubscribeEvent
    fun afterClientTick(event: ClientTickEvent.Post) {

        val iterator = indicators.iterator()
        while (iterator.hasNext()) {
            val indicator = iterator.next()
            indicator.duration--

            if (indicator.duration <= 0) {
                iterator.remove()
            }
        }

        val player = ClientUtil.localPlayer ?: return
        val playerPos = player.blockPosition()
        val level = player.level()

        val offHandTag = player.offhandItem.get(ModDataComponents.BLOCK_TAG)
        val mainHandTag = player.mainHandItem.get(ModDataComponents.BLOCK_TAG)

        if (offHandTag == null && mainHandTag == null) return

        for (dX in -5..5) for (dY in -5..5) for (dZ in -5..5) {
            val checkedPos = playerPos.offset(dX, dY, dZ)
            if (!level.isLoaded(checkedPos)) continue

            val checkedState = level.getBlockState(checkedPos)

            val matchesOffHand = offHandTag != null && checkedState.`is`(offHandTag)
            val matchesMainHand = mainHandTag != null && checkedState.`is`(mainHandTag)

            if (!matchesOffHand && !matchesMainHand) continue
            if (indicators.any { it.target == checkedPos }) continue

            val indicator = Indicator(
                checkedPos,
                20,
                if (matchesMainHand) mainHandTag!! else offHandTag!!
            )

            indicators.add(indicator)
        }
    }

    @SubscribeEvent
    fun onRenderLevel(event: RenderLevelStageEvent) {
        if (event.stage != RenderLevelStageEvent.Stage.AFTER_LEVEL) return
        val player = ClientUtil.localPlayer ?: return

        val partialTicks = event.partialTick

    }

}