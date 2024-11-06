package dev.aaronhowser.mods.irregular_implements.client.rendering

import com.google.common.primitives.Ints
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.core.Direction
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.client.extensions.IBakedModelExtension
import java.util.*

class CustomCraftingTableBlockModel(
    val originalQuads: EnumMap<Direction?, List<BakedQuad>>,
    val overlays: EnumMap<Direction, TextureAtlasSprite>,
    val particleTexture: TextureAtlasSprite,
    val isAmbientOcclusion: Boolean
) : IBakedModelExtension {

    val itemQuads: EnumMap<Direction?, List<BakedQuad>> = EnumMap(Direction::class.java)
    val overlayQuads: EnumMap<Direction?, List<BakedQuad>> = EnumMap(Direction::class.java)

    init {
        itemQuads[null] = originalQuads[null]

        for (direction in Direction.entries) {
            val directionQuads = originalQuads[direction] ?: emptyList()

            itemQuads[direction] = directionQuads
            originalQuads[direction] = directionQuads

            if (overlays.containsKey(direction)) {
                val overlayQuad =
            }
        }

    }

    private fun createSidedBakedQuad(
        x1: Float,
        x2: Float,
        z1: Float,
        z2: Float,
        y: Float,
        texture: TextureAtlasSprite,
        side: Direction
    ): BakedQuad {
        var c1 = rotate(Vec3(x1 - .5, y - .5, z1 - .5), side).add(.5, 0.5, .5)
        var c2 = rotate(Vec3(x1 - .5, y - .5, z2 - .5), side).add(.5, 0.5, .5)
        var c3 = rotate(Vec3(x2 - .5, y - .5, z2 - .5), side).add(.5, 0.5, .5)
        var c4 = rotate(Vec3(x2 - .5, y - .5, z1 - .5), side).add(.5, 0.5, .5)

        var rotation: Direction = Direction.SOUTH

        if (side === Direction.WEST || side === Direction.EAST || side === Direction.SOUTH) {
            rotation = Direction.SOUTH
        } else if (side === Direction.NORTH) {
            rotation = Direction.WEST
            c1 = rotate(c1.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c2 = rotate(c2.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c3 = rotate(c3.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c4 = rotate(c4.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
        }

        if (side !== Direction.UP && side !== Direction.SOUTH) {
            c1 = rotate(c1.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c2 = rotate(c2.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c3 = rotate(c3.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
            c4 = rotate(c4.add(-.5, -.5, -.5), rotation).add(.5, 0.5, .5)
        }

        return BakedQuad(
            Ints.concat(
                vertexToInts(
                    c1.x.toFloat(),
                    c1.y .toFloat(),
                    c1.z .toFloat(),
                    -1,
                    texture,
                    0f,
                    0f,
                    side
                ),
                vertexToInts(
                    c2.x.toFloat(),
                    c2.y.toFloat(),
                    c2.z.toFloat(),
                    -1,
                    texture,
                    0f,
                    16f,
                    side
                ),
                vertexToInts(
                    c3.x.toFloat(),
                    c3.y.toFloat(),
                    c3.z.toFloat(),
                    -1,
                    texture,
                    16f,
                    16f,
                    side
                ),
                vertexToInts(
                    c4.x.toFloat(),
                    c4.y.toFloat(),
                    c4.z.toFloat(),
                    -1,
                    texture,
                    16f,
                    0f,
                    side
                )
            ), -1,
            side,
            texture,
            false,
            DefaultVertexFormat.BLOCK
        )
    }

    private fun rotate(vec: Vec3, side: Direction): Vec3 {
        return when (side) {
            Direction.DOWN -> Vec3(vec.x, -vec.y, -vec.z)
            Direction.UP -> Vec3(vec.x, vec.y, vec.z)
            Direction.NORTH -> Vec3(vec.x, vec.z, -vec.y)
            Direction.SOUTH -> Vec3(vec.x, -vec.z, vec.y)
            Direction.WEST -> Vec3(-vec.y, vec.x, vec.z)
            Direction.EAST -> Vec3(vec.y, -vec.x, vec.z)
        }
    }

    private fun vertexToInts(
        x: Float,
        y: Float,
        z: Float,
        color: Int,
        texture: TextureAtlasSprite,
        u: Float,
        v: Float,
        side: Direction
    ): IntArray {
        val normal: Int

        val xN = ((side.normal.x * 127).toByte()).toInt() and 0xFF
        val yN = ((side.normal.y * 127).toByte()).toInt() and 0xFF
        val zN = ((side.normal.z * 127).toByte()).toInt() and 0xFF

        normal = xN or (yN shl 0x08) or (zN shl 0x10)

        return intArrayOf(
            java.lang.Float.floatToRawIntBits(x),
            java.lang.Float.floatToRawIntBits(y),
            java.lang.Float.floatToRawIntBits(z),
            color,
            java.lang.Float.floatToRawIntBits(texture.getU(u)),
            java.lang.Float.floatToRawIntBits(texture.getV(v)),
            normal
        )
    }

}