package dev.aaronhowser.mods.irregular_implements.util

import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.ItemStack
import java.lang.ref.WeakReference

object EscapeRopeHandler {

    private val runningTasks: ArrayList<Task> = ArrayList()

    fun addTask(player: ServerPlayer) {
        if (this.runningTasks.any { it.player.get() == player }) return

        val task = Task(WeakReference(player))
        task.toCheck.add(player.blockPosition())

        this.runningTasks.add(task)
    }

    fun tick() {
        val iterator = runningTasks.iterator()

        while (iterator.hasNext()) {
            val task = iterator.next()

            if (task.tick()) iterator.remove()
        }
    }

    private class Task(
        val player: WeakReference<ServerPlayer>
    ) {
        val toCheck: ArrayList<BlockPos> = ArrayList()
        val alreadyChecked: HashSet<BlockPos> = HashSet()

        val levelAtStart = player.get()?.level()

        /**
         * @return true is the task is done
         */
        fun tick(): Boolean {
            val actualPlayer = player.get() ?: return true
            val level = actualPlayer.level()
            val usedItem = actualPlayer.useItem

            if (level != levelAtStart
                || !usedItem.`is`(ModItems.ESCAPE_ROPE)
            ) return true

            // Runs 4 times per tick so that it works 4 times faster
            for (run in 0 until 4) {

                if (this.toCheck.isEmpty() || this.alreadyChecked.size >= 1000) {
                    actualPlayer.drop(usedItem, false)
                    actualPlayer.setItemInHand(actualPlayer.usedItemHand, ItemStack.EMPTY)  // is this required?

                    return true
                }

                var nextPos = this.toCheck.removeLast()
                while (this.alreadyChecked.contains(nextPos) && this.toCheck.isNotEmpty()) {
                    nextPos = this.toCheck.removeLast()
                }

                if (this.alreadyChecked.contains(nextPos)) {
                    return true
                }

                val posIsEmpty = level.isLoaded(nextPos) && level.getBlockState(nextPos).getCollisionShape(level, nextPos).isEmpty
                if (!posIsEmpty) {
                    this.alreadyChecked.add(nextPos)
                    continue
                }

                if (!level.canSeeSky(nextPos)) {
                    this.alreadyChecked.add(nextPos)

                    for (direction in Direction.entries) {
                        val offset = nextPos.relative(direction)

                        if (!this.alreadyChecked.contains(offset)) {
                            this.toCheck.add(offset)
                        }
                    }

                    continue
                }

                level.playSound(null, actualPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5f, 1f)

                var foundGround = false

                groundSearch@
                for (y in nextPos.y downTo level.minBuildHeight) {
                    val possibleTeleportPos = BlockPos(nextPos.x, y, nextPos.z)
                    val stateThere = level.getBlockState(possibleTeleportPos)

                    val canStandOn = stateThere.isFaceSturdy(level, possibleTeleportPos, Direction.UP)
                            || stateThere.isCollisionShapeFullBlock(level, possibleTeleportPos)

                    if (canStandOn) {
                        actualPlayer.teleportTo(
                            possibleTeleportPos.x + 0.5,
                            possibleTeleportPos.y + 1.0,
                            possibleTeleportPos.z + 0.5
                        )

                        foundGround = true
                        break@groundSearch
                    }
                }

                if (!foundGround) {
                    actualPlayer.teleportTo(
                        nextPos.x + 0.5,
                        nextPos.y + 1.0,
                        nextPos.z + 0.5
                    )
                }

                actualPlayer.stopUsingItem()

                level.playSound(null, actualPlayer.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5f, 1f)

                usedItem.hurtAndBreak(1, actualPlayer, actualPlayer.getEquipmentSlotForItem(usedItem))
                return true
            }

            return false
        }

    }

}