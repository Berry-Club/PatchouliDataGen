package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.Util.addProperty
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike

class SmeltingRecipePage private constructor(
	private val recipeOne: ResourceLocation,
	private val recipeTwo: ResourceLocation?,
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

	companion object {
		@JvmStatic
		fun builder() = Builder.setup()
	}

	class Builder private constructor() {
		private var recipeOne: ResourceLocation? = null
		private var recipeTwo: ResourceLocation? = null
		private var title: String? = null
		private var text: String? = null

		fun mainRecipe(recipeId: ResourceLocation): Builder {
			this.recipeOne = recipeId
			return this
		}

		fun mainRecipe(recipeOutput: ItemLike): Builder {
			val itemLocation = BuiltInRegistries.ITEM.getKey(recipeOutput.asItem())
			this.recipeOne = itemLocation
			return this
		}

		fun secondaryRecipe(recipeId: ResourceLocation): Builder {
			this.recipeTwo = recipeId
			return this
		}

		fun secondaryRecipe(recipeOutput: ItemLike): Builder {
			val itemLocation = BuiltInRegistries.ITEM.getKey(recipeOutput.asItem())
			this.recipeTwo = itemLocation
			return this
		}

		fun title(title: String): Builder {
			this.title = title
			return this
		}

		fun title(title: Component): Builder {
			this.title = title.string
			return this
		}

		fun text(text: String): Builder {
			this.text = text
			return this
		}

		fun text(text: Component): Builder {
			this.text = text.string
			return this
		}

		fun build(): SmeltingRecipePage {
			val recipeOne = requireNotNull(this.recipeOne) { "Main recipe must be set!" }

			return SmeltingRecipePage(
				recipeOne = recipeOne,
				recipeTwo = recipeTwo,
				title = title,
				text = text
			)
		}

		companion object {
			@JvmStatic
			fun setup() = Builder()
		}
	}
}