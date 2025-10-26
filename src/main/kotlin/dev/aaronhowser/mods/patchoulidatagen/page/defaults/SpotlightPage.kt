package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.page.AbstractPage
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.ItemLike

class SpotlightPage private constructor(
	private val spotlightItem: ItemLike,
	private val linkRecipe: Boolean?,
	private val title: String?,
	private val text: String?,
	advancement: ResourceLocation?,
	flag: String?,
	anchor: String?
) : AbstractPage(advancement, flag, anchor) {

	override fun getPageType(): String = "spotlight"

	override fun addToJson(json: JsonObject) {
		json.apply {
			addProperty("item", spotlightItem.asItem().toString())
			addIfNotNull("title", title)
			addIfNotNull("text", text)
			addIfNotNull("link_recipe", linkRecipe)
		}
	}

	companion object {
		@JvmStatic
		fun builder() = Builder.builder()
	}

	class Builder private constructor() : AbstractPage.Builder<Builder>() {
		private var spotlightItem: ItemLike? = null
		private var linkRecipe: Boolean? = null
		private var title: String? = null
		private var text: String? = null

		fun item(item: ItemLike): Builder {
			this.spotlightItem = item
			return this
		}

		fun linkRecipe(linkRecipe: Boolean): Builder {
			this.linkRecipe = linkRecipe
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

		fun build(): SpotlightPage {
			requireNotNull(spotlightItem) { "Spotlight item must be set" }

			return SpotlightPage(
				spotlightItem = spotlightItem!!,
				linkRecipe = linkRecipe,
				title = title,
				text = text,
				advancement = advancement,
				flag = flag,
				anchor = anchor
			)
		}

		companion object {
			@JvmStatic
			fun builder() = Builder()
		}
	}

}