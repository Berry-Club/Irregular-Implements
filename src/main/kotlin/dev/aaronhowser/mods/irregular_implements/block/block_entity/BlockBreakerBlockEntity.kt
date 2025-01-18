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
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
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
        const val DIAMOND_BREAKER_NBT = "DiamondBreaker"

        private fun getPick(
            level: Level,
            item: Item,
            withEnchantments: ItemEnchantments
        ): ItemStack {
            val stack = item.defaultInstance
            stack.set(DataComponents.UNBREAKABLE, Unbreakable(true))

            val enchantments = ItemEnchantments.Mutable(withEnchantments)
            enchantments.set(
                level.registryAccess().registry(Registries.ENCHANTMENT).get().getHolderOrThrow(ModEnchantments.MAGNETIC),
                1
            )

            EnchantmentHelper.setEnchantments(
                stack,
                enchantments.toImmutable()
            )

            return stack
        }

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

    var diamondBreaker: ItemStack = ItemStack.EMPTY
        private set

    private fun initFakePlayer() {
        val level = level as? ServerLevel ?: return

        if (this.uuid == null) {
            this.uuid = UUID.randomUUID()
            setChanged()
        }

        this.fakePlayer = WeakReference(FakePlayerFactory.get(level, breakerGameProfile))
        setChanged()

        this.fakePlayer?.get()?.let {
            it.isSilent = true
            it.setOnGround(true)

            it.setItemInHand(InteractionHand.MAIN_HAND, getPick(level, Items.IRON_PICKAXE, withEnchantments = ItemEnchantments.EMPTY))
        }
    }

    fun upgrade(insertedBreaker: ItemStack) {
        val level = level as? ServerLevel ?: return
        val fakePlayer = this.fakePlayer?.get() ?: return

        val enchantments = insertedBreaker.getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT))
        val pick = getPick(level, Items.DIAMOND_PICKAXE, withEnchantments = enchantments)

        this.diamondBreaker = insertedBreaker
        setChanged()

        fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, pick)
    }

    fun downgrade(player: Player) {
        val level = level as? ServerLevel ?: return

        OtherUtil.giveOrDropStack(this.diamondBreaker, player)
        this.diamondBreaker = ItemStack.EMPTY

        val basicPick = getPick(level, Items.IRON_PICKAXE, withEnchantments = ItemEnchantments.EMPTY)

        this.fakePlayer?.get()?.setItemInHand(InteractionHand.MAIN_HAND, basicPick)
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

            //FIXME: Not applying efficiency enchantment, broken at Player.getDigSpeed getAttributeValue
            this.miningProgress += targetState.getDestroyProgress(fakePlayer, level, targetPos)

            // If not done mining, continue mining then stop tick
            if (this.miningProgress < 1f) {
                level.destroyBlockProgress(
                    this.uuid.hashCode(),
                    targetPos,
                    Mth.ceil(this.miningProgress * 10)
                )
                return
            }

            this.isMining = false
            resetProgress()

            val possibleInventoryPos = this.blockPos.relative(facing.opposite)
            val inventoryHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, possibleInventoryPos, facing)

            fakePlayer.gameMode.destroyBlock(targetPos)

            val inventory = fakePlayer.inventory

            for (i in inventory.items.indices) {
                val stack = inventory.items[i].copy()

                if (stack.isEmpty
                    || ItemStack.isSameItemSameComponents(stack, inventory.getSelected())
                ) continue

                inventory.removeItemNoUpdate(i)

                var remainder = stack.copy()

                if (inventoryHandler != null) {
                    remainder = ItemHandlerHelper.insertItemStacked(inventoryHandler, remainder, false)
                }

                if (!remainder.isEmpty) {
                    OtherUtil.dropStackAt(remainder.copy(), level, possibleInventoryPos.center, instantPickup = false)
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

    fun neighborChanged(state: BlockState, level: Level) {
        val targetPos = blockPos.relative(state.getValue(BlockBreakerBlock.FACING))
        val targetState = level.getBlockState(targetPos)

        this.canMine = !level.hasNeighborSignal(blockPos)

        if (this.canMine) {
            if (!targetState.isAir) {
                if (!this.isMining) {
                    this.isMining = true
                    this.miningProgress = 0f
                }
            } else {
                this.isMining = false
                resetProgress()
            }
        } else {
            if (this.isMining) {
                this.isMining = false
                resetProgress()
            }
        }
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

        if (!this.diamondBreaker.isEmpty) {
            tag.put(DIAMOND_BREAKER_NBT, this.diamondBreaker.save(registries))
        }
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        this.uuid = tag.getUuidOrNull(UUID_NBT)

        this.isMining = tag.getBoolean(IS_MINING_NBT)
        this.canMine = tag.getBoolean(CAN_MINE_NBT)
        this.miningProgress = tag.getFloat(MINING_PROGRESS_NBT)

        if (tag.contains(DIAMOND_BREAKER_NBT)) {
            this.diamondBreaker = ItemStack.parseOptional(registries, tag.getCompound(DIAMOND_BREAKER_NBT))
        }
    }

    // Syncs with client
    override fun getUpdateTag(pRegistries: HolderLookup.Provider): CompoundTag = saveWithoutMetadata(pRegistries)
    override fun getUpdatePacket(): Packet<ClientGamePacketListener> = ClientboundBlockEntityDataPacket.create(this)

}