package dev.aaronhowser.mods.irregular_implements.handler

import dev.aaronhowser.mods.irregular_implements.config.ServerConfig
import dev.aaronhowser.mods.irregular_implements.datagen.ModLanguageProvider.Companion.toComponent
import dev.aaronhowser.mods.irregular_implements.datagen.language.ModMessageLang
import dev.aaronhowser.mods.irregular_implements.packet.ModPacketHandler
import dev.aaronhowser.mods.irregular_implements.packet.server_to_client.RenderCubePacket
import dev.aaronhowser.mods.irregular_implements.registry.ModItems
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.status
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import java.lang.ref.WeakReference

object EscapeRopeHandler {

	private val runningTasks: ArrayList<Task> = ArrayList()

	fun addTask(player: ServerPlayer) {
		if (this.runningTasks.any { it.playerReference.get() == player }) return
		val task = Task(WeakReference(player))
		this.runningTasks.add(task)
	}

	fun tick() {
		val iterator = runningTasks.iterator()

		while (iterator.hasNext()) {
			val task = iterator.next()

			if (task.tick()) iterator.remove()
		}
	}

	//TODO: Apparently block GETTING is async-safe. Maybe I should make this async so it can do multiple at once?

	// Reverse because it searches by most recently added position first, so it has to add them in reverse order
	private val directionPriority = arrayOf(
		Direction.UP,
		Direction.NORTH,
		Direction.SOUTH,
		Direction.WEST,
		Direction.EAST,
		Direction.DOWN
	).reversed()

	private class Task(
		val playerReference: WeakReference<ServerPlayer>
	) {
		private val toCheck: ArrayList<BlockPos> = ArrayList()
		private val alreadyChecked: HashSet<BlockPos> = HashSet()

		private val levelAtStart = this.playerReference.get()?.level()

		init {
			val actualPlayer = this.playerReference.get()
			if (actualPlayer != null) toCheck.add(actualPlayer.blockPosition())
		}

		/**
		 * @return true if the task is done
		 */
		fun tick(): Boolean {
			val player = this.playerReference.get() ?: return true
			val level = player.serverLevel()
			val usedItem = player.useItem

			if (level != this.levelAtStart || !usedItem.`is`(ModItems.ESCAPE_ROPE)) return true

			val limit = ServerConfig.ESCAPE_ROPE_MAX_BLOCKS.get()
			val maxRuns = ServerConfig.ESCAPE_ROPE_BLOCKS_PER_TICK.get()
			val shouldSpawnIndicator = true // TODO: Config

			for (run in 0 until maxRuns) {
				player.status(
					ModMessageLang.ESCAPE_ROPE_HANDLER_PROGRESS.toComponent(alreadyChecked.size)
				)

				if (toCheck.isEmpty() || (limit > 1 && alreadyChecked.size >= limit)) {
					player.drop(usedItem, true)
					return true
				}

				val nextPos = getNextPositionToCheck() ?: return true
				if (shouldSpawnIndicator) {
					val packet = RenderCubePacket(nextPos, 20, 0x66FFFFFF, Vec3(1.0, 1.0, 1.0))
					ModPacketHandler.messagePlayer(player, packet)
				}

				if (!isEmptySpace(level, nextPos)) {
					alreadyChecked.add(nextPos)
					continue
				}

				if (!level.canSeeSky(nextPos)) {
					expandSearchFrom(nextPos)
					continue
				}

				teleportPlayerToSurface(level, player, usedItem, nextPos)
				return true
			}

			return false
		}

		private fun getNextPositionToCheck(): BlockPos? {
			while (toCheck.isNotEmpty()) {
				val pos = toCheck.removeLast()
				if (!alreadyChecked.contains(pos)) {
					return pos
				}
			}
			return null
		}

		private fun isEmptySpace(level: Level, pos: BlockPos): Boolean {
			return level.isLoaded(pos) &&
					level.getBlockState(pos).getCollisionShape(level, pos).isEmpty
		}

		private fun expandSearchFrom(pos: BlockPos) {
			alreadyChecked.add(pos)

			for (direction in directionPriority) {
				val offset = pos.relative(direction)
				if (!alreadyChecked.contains(offset)) {
					toCheck.add(offset)
				}
			}
		}

		private fun teleportPlayerToSurface(
			level: Level,
			player: ServerPlayer,
			item: ItemStack,
			targetPos: BlockPos
		) {
			level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5f, 1f)

			val teleportPos = findGroundBelow(level, targetPos) ?: targetPos.above()

			player.teleportTo(
				teleportPos.x + 0.5,
				teleportPos.y.toDouble(),
				teleportPos.z + 0.5
			)

			player.stopUsingItem()
			level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.5f, 1f)
			item.hurtAndBreak(1, player, player.getEquipmentSlotForItem(item))
		}

		private fun findGroundBelow(level: Level, pos: BlockPos): BlockPos? {
			val mutable = pos.mutable()

			for (y in pos.y downTo level.minBuildHeight) {
				mutable.setY(y)
				val state = level.getBlockState(mutable)

				val canStand = state.isFaceSturdy(level, mutable, Direction.UP)
						|| state.isCollisionShapeFullBlock(level, mutable)

				if (canStand) {
					return mutable.above().immutable()
				}
			}

			return null
		}
	}


}