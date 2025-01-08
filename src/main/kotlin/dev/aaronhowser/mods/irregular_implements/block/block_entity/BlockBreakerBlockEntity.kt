package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.mojang.authlib.GameProfile
import dev.aaronhowser.mods.irregular_implements.block.BlockBreakerBlock
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.Registries
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.component.Unbreakable
import net.minecraft.world.item.enchantment.EnchantmentHelper
import net.minecraft.world.item.enchantment.ItemEnchantments
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.common.util.FakePlayer
import net.neoforged.neoforge.common.util.FakePlayerFactory
import net.neoforged.neoforge.items.ItemHandlerHelper
import java.lang.ref.WeakReference
import java.util.*

class BlockBreakerBlockEntity(
    pPos: BlockPos,
    pBlockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_BREAKER.get(), pPos, pBlockState) {

    companion object {
        val breakerGameProfile = GameProfile(UUID.nameUUIDFromBytes("IIBlockBreaker".toByteArray()), "IIBlockBreaker")

        const val UUID_NBT = "UUID"
        const val IS_MINING_NBT = "IsMining"
        const val CAN_MINE_NBT = "CanMine"
        const val MINING_PROGRESS_NBT = "MiningProgress"

        fun tick(
            level: Level,
            blockPos: BlockPos,
            blockState: BlockState,
            blockEntity: BlockBreakerBlockEntity
        ) {
            if (level.isClientSide) return

            blockEntity.tick()
        }
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

            it.setItemInHand(InteractionHand.MAIN_HAND, unbreakableIronPick)
        }
    }

    fun tick() {
        val level = level as? ServerLevel ?: return

        if (!this.isFirstTick) {
            this.isFirstTick = true
            initFakePlayer()
        }

        val fakePlayer = this.fakePlayer?.get() ?: return

        if (this.isMining) {
            val facing = blockState.getValue(BlockBreakerBlock.FACING)
            val targetPos = blockPos.relative(facing)

            val targetState = level.getBlockState(targetPos)

            this.miningProgress += targetState.getDestroyProgress(fakePlayer, level, targetPos)

            // If not done mining, continue mining then stop tick
            if (this.miningProgress < 1f) {
                level.destroyBlockProgress(this.uuid.hashCode(), targetPos, (this.miningProgress * 10).toInt())
                return
            }

            this.isMining = false
            resetProgress()

            val possibleInventoryPos = this.blockPos.relative(facing.opposite)
            val inventoryHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, possibleInventoryPos, facing)

            fakePlayer.gameMode.destroyBlock(targetPos)

            for (i in fakePlayer.inventory.items.indices) {
                val stack = fakePlayer.inventory.items[i].copy()
                if (stack.isEmpty) continue

                fakePlayer.inventory.setItem(i, ItemStack.EMPTY)

                var remainder = stack.copy()

                if (inventoryHandler != null) {
                    remainder = ItemHandlerHelper.insertItemStacked(inventoryHandler, remainder, false)
                }

                if (!remainder.isEmpty) {
                    OtherUtil.dropStackAt(remainder.copy(), level, targetPos.center, instantPickup = false)
                }
            }
        }
    }

    private fun resetProgress() {
        val uuid = this.uuid ?: return
        val level = level as? ServerLevel ?: return

        val targetPos = blockPos.relative(blockState.getValue(BlockBreakerBlock.FACING))

        level.destroyBlockProgress(uuid.hashCode(), targetPos, -1)
        this.miningProgress = 0f
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        val uuid = this.uuid
        if (uuid != null) {
            tag.putUUID(UUID_NBT, uuid)
        }

        tag.putBoolean(IS_MINING_NBT, this.isMining)
        tag.putBoolean(CAN_MINE_NBT, this.canMine)
        tag.putFloat(MINING_PROGRESS_NBT, this.miningProgress)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.uuid = tag.getUuidOrNull(UUID_NBT)

        this.isMining = tag.getBoolean(IS_MINING_NBT)
        this.canMine = tag.getBoolean(CAN_MINE_NBT)
        this.miningProgress = tag.getFloat(MINING_PROGRESS_NBT)
    }

}