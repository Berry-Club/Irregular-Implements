package dev.aaronhowser.mods.irregular_implements.util

import com.google.common.collect.HashMultimap
import dev.aaronhowser.mods.irregular_implements.IrregularImplements
import net.neoforged.fml.common.EventBusSubscriber

object ServerScheduler {

    fun tick() {
        currentTick++
    }

    private var currentTick = 0
        set(value) {
            field = value
            handleScheduledTasks(value)
        }

    private val upcomingTasks: HashMultimap<Int, Runnable> = HashMultimap.create()

    fun scheduleTaskInTicks(ticksInFuture: Int, runnable: Runnable) {
        if (ticksInFuture > 0) {
            upcomingTasks.put(currentTick + ticksInFuture, runnable)
        } else {
            runnable.run()
        }
    }

    private fun handleScheduledTasks(tick: Int) {
        if (!upcomingTasks.containsKey(tick)) return

        val tasks = upcomingTasks[tick].iterator()

        while (tasks.hasNext()) {
            try {
                tasks.next().run()
            } catch (e: Exception) {
                IrregularImplements.LOGGER.error(e.toString())
            }

            tasks.remove()
        }
    }

}