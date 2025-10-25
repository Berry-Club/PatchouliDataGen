package dev.aaronhowser.mods.patchoulidatagen.page.defaults

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.page.PageType

/**
 * This is an empty page with no text
 *
 * See [Page Types - Link Pages](https://vazkiimods.github.io/Patchouli/docs/patchouli-basics/page-types/#link-pages)
 */
class LinkPage private constructor(
	private val url: String,
	private val linkText: String
) : PageType {

	override fun getPageType(): String = "link"

	override fun addToJson(json: JsonObject) {
		json.apply {
			addProperty("url", url)
			addProperty("link_text", linkText)
		}
	}

	companion object {
		@JvmStatic
		fun setup(
			url: String,
			linkText: String
		): LinkPage {
			return LinkPage(
				url = url,
				linkText = linkText
			)
		}
	}
}