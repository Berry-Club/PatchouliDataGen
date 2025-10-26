package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import net.minecraft.network.chat.Component

class TextPage private constructor(
	private val text: String,
	private val title: String?
) : PageType {

	override fun getPageType(): String = "text"

	override fun addToJson(json: JsonObject) {
		json.apply {
			addProperty("text", text)
			addIfNotNull("title", title)
		}
	}

	companion object {
		@JvmStatic
		fun builder(): Builder = Builder.create()
	}

	class Builder {
		private var text: String? = null
		private var title: String? = null

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

		fun build(): TextPage {
			requireNotNull(text) { "TextPage text must be set!" }
			return TextPage(
				text = text!!,
				title = title
			)
		}

		companion object {
			fun create(): Builder = Builder()
		}

	}


}