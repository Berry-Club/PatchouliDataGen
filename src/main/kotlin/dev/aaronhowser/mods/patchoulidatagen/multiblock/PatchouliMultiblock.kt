package dev.aaronhowser.mods.patchoulidatagen.multiblock

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.tags.TagKey
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.Property

class PatchouliMultiblock(
	private val multiblock: List<List<String>>,
	private val mappings: Map<Char, String>
) : Multiblock {

	override fun toJson(): JsonObject {
		val json = JsonObject()

		val mappingJson = JsonObject()
		for ((char, block) in mappings) {
			mappingJson.addProperty(
				char.toString(),
				block
			)
		}
		json.add("mapping", mappingJson)

		val allLayersArray = JsonArray()

		for (layer in multiblock) {
			val layerArray = JsonArray()

			for (row in layer) {
				layerArray.add(row)
			}

			allLayersArray.add(layerArray)
		}

		json.add("layers", allLayersArray)

		return json
	}

	companion object {
		@JvmStatic
		fun builder(): Builder = Builder.create()
	}

	class Builder private constructor() : Multiblock.Builder {

		private val mappingCharacters: MutableSet<Char> = mutableSetOf()
		private val mappings: MutableMap<Char, String> = mutableMapOf()
		private val multiblock: MutableList<List<String>> = mutableListOf()

		fun pattern(vararg layerPattern: String): Builder {
			val layerMultiblock = layerPattern.toList()
			multiblock.add(layerMultiblock)
			return this
		}

		fun map(char: Char, block: Block): Builder {
			require(char !in mappingCharacters) { "Character '$char' is already mapped to a block." }

			mappingCharacters.add(char)
			mappings[char] = block.name.string

			return this
		}

		fun <T : Comparable<T>> map(
			char: Char,
			block: Block,
			property: Property<T>,
			value: T
		): Builder {

			require(char !in mappingCharacters) { "Character '$char' is already mapped to a block." }

			mappingCharacters.add(char)
			val blockStateString = "${block.name.string}[${property.name}=${property.getName(value)}]"
			mappings[char] = blockStateString

			return this
		}

		fun map(char: Char, blockTag: TagKey<Block>): Builder {
			require(char !in mappingCharacters) { "Character '$char' is already mapped to a block." }

			mappingCharacters.add(char)
			mappings[char] = "#${blockTag.location}"

			return this
		}

		override fun build(): Multiblock {
			require(mappingCharacters.isNotEmpty()) { "At least one mapping must be defined." }
			require(multiblock.isNotEmpty()) { "At least one layer must be defined." }

			val amountZeroes = multiblock.flatten().sumOf { layer -> layer.count { it == '0' } }

			require(amountZeroes == 1) {
				"Exactly one '0' character must be present to represent the center. See https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/multiblocks#the-pattern"
			}

			return PatchouliMultiblock(
				multiblock = multiblock,
				mappings = mappings
			)
		}

		companion object {
			fun create(): Builder = Builder()
		}
	}

}