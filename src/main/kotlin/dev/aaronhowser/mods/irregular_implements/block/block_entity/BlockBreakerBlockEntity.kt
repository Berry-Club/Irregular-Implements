package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.mojang.authlib.GameProfile
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.Unbreakable
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.ItemEnchantments
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.common.util.FakePlayer
import net.neoforged.neoforge.common.util.FakePlayerFactory
import java.lang.ref.WeakReference
import java.util.*

class BlockBreakerBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_BREAKER.get(), pPos, pBlockState) {

    companion object {
        val breakerGameProfile = GameProfile(UUID.nameUUIDFromBytes("IIBlockBreaker".toByteArray()), "IIBlockBreaker")
    }

    private var uuid: UUID? = null

    private var isMining = false
    private var canMine = false

    private var miningProgress = 0f

    private var isFirstTick = false

    private var fakePlayer: WeakReference<FakePlayer>? = null

    private fun initFakePlayer() {
        val level = level as? ServerLevel ?: return

        if (this.uuid == null) {
            this.uuid = UUID.randomUUID()
            setChanged()
        }

        this.fakePlayer = WeakReference(FakePlayerFactory.get(level, breakerGameProfile))
        setChanged()

        val unbreakableIronPick = Items.IRON_PICKAXE.defaultInstance
        unbreakableIronPick.set(DataComponents.UNBREAKABLE, Unbreakable(true))

        val enchantments = ItemEnchantments.Mutable(ItemEnchantments.EMPTY)
        enchantments.set(
            level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolderOrThrow(ModEnchantments.MAGNETIC),
            1
        )

        EnchantmentHelper.setEnchantments(
            unbreakableIronPick,
            enchantments.toImmutable()
        )

        this.fakePlayer?.get()?.let {
            it.isSilent = true
            it.setOnGround(true)
        }

    }

}