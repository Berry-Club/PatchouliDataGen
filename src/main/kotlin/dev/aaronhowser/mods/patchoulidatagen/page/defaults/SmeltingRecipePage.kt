package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.page.PageType
import net.minecraft.world.level.ItemLike

class SmeltingRecipePage private constructor(
	private val recipeOne: String,
	private val recipeTwo: String?,
	private val title: String?,
	private val text: String?
) : PageType {

	override fun getPageType(): String = "smelting"

	override fun addToJson(json: JsonObject) {
		json.apply {
			addProperty("recipe", recipeOne)
			addIfNotNull("recipe2", recipeTwo)
			addIfNotNull("title", title)
			addIfNotNull("text", text)
		}
	}

	class Builder private constructor() {
		private var recipeOne : String? = null
		private var recipeTwo : String? = null
		private var title : String? = null
		private var text : String? = null

		fun mainRecipe(recipeId: String): Builder {
			this.recipeOne = recipeId
			return this
		}

		fun mainRecipe(recipeOutput: ItemLike): Builder {
			val itemName = recipeOutput.asItem().toString()
			this.recipeOne = itemName
		}

	}
}