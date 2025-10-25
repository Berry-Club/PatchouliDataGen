package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.page.PageType
import net.minecraft.network.chat.Component

/**
 * This is an empty page with no text
 *
 * See [Page Types - Quest Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#quest-pages)
 */
class RelationsPage private constructor(
	private val entries: Array<String>,
	private val title: String?,
	private val text: String?
) : PageType {

	override fun getPageType(): String = "relations"

	override fun addToJson(json: JsonObject) {
		json.apply {
			val entriesArray = JsonArray()
			for (entry in entries) {
				entriesArray.add(entry)
			}
			add("entries", entriesArray)

			addIfNotNull("title", title)
			addIfNotNull("text", text)
		}
	}

	companion object {
		@JvmStatic
		fun builder(): Builder = Builder.setup()
	}

	class Builder private constructor() {
		private val entries: MutableList<String> = mutableListOf()
		private var title: String? = null
		private var text: String? = null

		fun entries(modId: String, vararg entryIds: String): Builder {
			for (entryId in entryIds) {
				entries.add("$modId:$entryId")
			}

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

		fun build(): RelationsPage {
			require(entries.isNotEmpty()) { "At least one entry must be added to RelationsPage" }

			return RelationsPage(
				entries.toTypedArray(),
				title,
				text
			)
		}

		companion object {
			@JvmStatic
			fun setup(): Builder = Builder()
		}
	}
}