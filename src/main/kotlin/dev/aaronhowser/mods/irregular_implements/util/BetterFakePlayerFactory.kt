package dev.aaronhowser.mods.irregular_implements.util

import com.mojang.authlib.GameProfile
import net.minecraft.server.level.ServerLevel
import net.neoforged.neoforge.common.util.FakePlayer

object BetterFakePlayerFactory {

	private val fakePlayers: MutableMap<FakePlayerKey, FakePlayer> = mutableMapOf()

	private data class FakePlayerKey(val level: ServerLevel, val username: GameProfile)

	fun get(level: ServerLevel, username: GameProfile, create: () -> FakePlayer): FakePlayer {
		val key = FakePlayerKey(level, username)
		return fakePlayers.getOrPut(key, create)
	}

}