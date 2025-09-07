package dev.aaronhowser.mods.irregular_implements.block.block_entity

import com.mojang.authlib.GameProfile
import dev.aaronhowser.mods.irregular_implements.block.BlockBreakerBlock
import dev.aaronhowser.mods.irregular_implements.datagen.datapack.ModEnchantments
import dev.aaronhowser.mods.irregular_implements.registry.ModBlockEntities
import dev.aaronhowser.mods.irregular_implements.util.BetterFakePlayerFactory
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil
import dev.aaronhowser.mods.irregular_implements.util.OtherUtil.getUuidOrNull
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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
import net.minecraft.world.entity.Entity
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
import net.neoforged.neoforge.items.ItemHandlerHelper
import java.lang.ref.WeakReference
import java.util.*

class BlockBreakerBlockEntity(
	pPos: BlockPos,
	pBlockState: BlockState
) : BlockEntity(ModBlockEntities.BLOCK_BREAKER.get(), pPos, pBlockState) {

	private var uuid: UUID? = null

	private var isMining = true
	private var canMine = true

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

		val gameProfile = GameProfile(this.uuid, BlockBreakerFakePlayer.NAME)
		val fakePlayer = BetterFakePlayerFactory.get(level, gameProfile) {
			BlockBreakerFakePlayer(level, gameProfile)
		}

		fakePlayer.isSilent = true
		fakePlayer.setOnGround(true)

		val pickToUse = if (diamondBreaker.isEmpty) {
			getPick(level, Items.IRON_PICKAXE, withEnchantments = ItemEnchantments.EMPTY)
		} else {
			val breakerEnchants = diamondBreaker.getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT))
			getPick(level, Items.DIAMOND_PICKAXE, withEnchantments = breakerEnchants)
		}

		fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, pickToUse)

		this.fakePlayer = WeakReference(fakePlayer)
		setChanged()
	}

	fun upgrade(insertedBreaker: ItemStack) {
		val level = level as? ServerLevel ?: return
		val fakePlayer = this.fakePlayer?.get() ?: return

		val breakerEnchantments = insertedBreaker.getAllEnchantments(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT))
		val pick = getPick(level, Items.DIAMOND_PICKAXE, withEnchantments = breakerEnchantments)

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
			val destroyProgress = targetState.getDestroyProgress(fakePlayer, level, targetPos)

			this.miningProgress += destroyProgress

			if (this.miningProgress < 1f) {
				level.destroyBlockProgress(
					this.uuid.hashCode(),
					targetPos,
					Mth.ceil(this.miningProgress * 10)
				)
				return
			}

			mineBlock(facing, fakePlayer, targetPos, level)
		}
	}

	private fun mineBlock(facing: Direction, fakePlayer: FakePlayer, targetPos: BlockPos, level: ServerLevel) {

		this.isMining = false
		resetProgress()

		val possibleInventoryPos = this.blockPos.relative(facing.opposite)
		val inventoryBehind = level.getCapability(Capabilities.ItemHandler.BLOCK, possibleInventoryPos, facing)

		// The Magnetic enchantment on the pick immediately teleports the item to the FakePlayer's inventory
		fakePlayer.gameMode.destroyBlock(targetPos)

		val inventory = fakePlayer.inventory

		for (i in inventory.items.indices) {
			val stack = inventory.items[i].copy()

			if (stack.isEmpty
				|| ItemStack.isSameItemSameComponents(stack, inventory.getSelected())
			) continue

			inventory.removeItemNoUpdate(i)

			var remainder = stack.copy()

			if (inventoryBehind != null) {
				remainder = ItemHandlerHelper.insertItemStacked(inventoryBehind, remainder, false)
			}

			if (!remainder.isEmpty) {
				OtherUtil.dropStackAt(remainder.copy(), level, possibleInventoryPos.center, instantPickup = false)
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

	class BlockBreakerFakePlayer(level: ServerLevel, gameProfile: GameProfile) : FakePlayer(level, gameProfile) {

		override fun take(entity: Entity, quantity: Int) {
			// Super would try to send a packet to everyone nearby, which is bad
		}

		companion object {
			const val NAME = "IrregularImplementsBlockBreaker"
		}
	}

	companion object {
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
				ModEnchantments.getHolder(ModEnchantments.MAGNETIC, level.registryAccess()),
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

}