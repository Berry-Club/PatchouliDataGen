package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.page.PageType
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike

/**
 * This page is used to display a crafting recipe.
 *
 * See the [Default Page Types - Crafting Recipe Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#crafting-recipe-pages) documentation.
 */
class CraftingRecipePage private constructor(
	private val recipeOne: ResourceLocation,
	private val recipeTwo: ResourceLocation?,
	private val title: String?,
	private val text: String?
) : PageType {

	override fun getPageType(): String = "crafting"

	override fun addToJson(json: JsonObject) {
		json.apply {
			addProperty("recipe", recipeOne.toString())
			addIfNotNull("recipe2", recipeTwo)
			addIfNotNull("title", title)
			addIfNotNull("text", text)
		}
	}

	companion object {
		@JvmStatic
		fun builder(): Builder = Builder.setup()
	}

	class Builder private constructor() {
		private var recipeOne: ResourceLocation? = null
		private var recipeTwo: ResourceLocation? = null
		private var title: String? = null
		private var text: String? = null

		fun mainRecipe(recipeOutput: ItemLike): Builder {
			val itemName = recipeOutput.asItem().toString()
			this.recipeOne = ResourceLocation.tryParse(itemName)
			return this
		}

		fun mainRecipe(recipeId: ResourceLocation): Builder {
			this.recipeOne = recipeId
			return this
		}

		fun mainRecipe(recipeId: String): Builder {
			this.recipeOne = ResourceLocation.tryParse(recipeId)
			return this
		}

		fun secondaryRecipe(recipeOutput: ItemLike): Builder {
			val itemName = recipeOutput.asItem().toString()
			this.recipeTwo = ResourceLocation.tryParse(itemName)
			return this
		}

		fun secondaryRecipe(recipeId: ResourceLocation): Builder {
			this.recipeTwo = recipeId
			return this
		}

		fun secondaryRecipe(recipeId: String): Builder {
			this.recipeTwo = ResourceLocation.tryParse(recipeId)
			return this
		}

		fun title(title: String): Builder {
			this.title = title
			return this
		}

		fun text(text: String): Builder {
			this.text = text
			return this
		}

		fun text(translatable: Component): Builder {
			this.text = translatable.string
			return this
		}

		fun build(): CraftingRecipePage {
			require(recipeOne != null) { "Main recipe must be set!" }

			return CraftingRecipePage(
				recipeOne = recipeOne!!,
				recipeTwo = recipeTwo,
				title = title,
				text = text
			)
		}

		companion object {
			fun setup(): Builder = Builder()
		}
	}

}