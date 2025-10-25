package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.Util.isNotTrue
import dev.aaronhowser.mods.patchoulidatagen.Util.isTrue
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item

class BookHeader private constructor(
	private val bookId: String,
	private val name: String,
	private val landingText: String,
	private val bookTexture: ResourceLocation?,
	private val fillerTexture: String?,
	private val craftingTexture: String?,
	private val textColor: Int?,
	private val headerColor: Int?,
	private val nameplateColor: Int?,
	private val linkColor: Int?,
	private val linkHoverColor: Int?,
	private val progressBarColor: Int?,
	private val progressBarBackground: Int?,
	private val openSound: ResourceLocation?,
	private val flipSound: ResourceLocation?,
	private val showProgress: Boolean?,
	private val version: String?,
	private val subtitle: String,
	private val creativeTab: String?,
	private val advancementTab: String?,
	private val doNotGenerateBook: Boolean?,
	private val customBookItem: Item?,
	private val showToast: Boolean?,
	private val useBlockyFont: Boolean?,
	private val i18n: Boolean?,
	private val pauseGame: Boolean?,
	private val icon: ResourceLocation?
) : BookElement {

	override fun getSaveName(): String = "book"

	fun getBookId(): String = bookId
	fun isTranslatable(): Boolean = i18n.isTrue()

	override fun toJson(): JsonObject {
		val json = JsonObject()

		json.apply {
			addProperty("name", name)
			addProperty("landing_text", landingText)

			addIfNotNull("book_texture", bookTexture)
			addIfNotNull("filler_texture", fillerTexture)
			addIfNotNull("crafting_texture", craftingTexture)
			addIfNotNull("text_color", textColor)
			addIfNotNull("header_color", headerColor)
			addIfNotNull("nameplate_color", nameplateColor)
			addIfNotNull("link_color", linkColor)
			addIfNotNull("link_hover_color", linkHoverColor)
			addIfNotNull("progress_bar_color", progressBarColor)
			addIfNotNull("progress_bar_background", progressBarBackground)
			addIfNotNull("open_sound", openSound)
			addIfNotNull("flip_sound", flipSound)
			addIfNotNull("show_progress", showProgress)
			addIfNotNull("version", version)
			addProperty("subtitle", subtitle)
			addIfNotNull("creative_tab", creativeTab)
			addIfNotNull("advancement_tab", advancementTab)
			addIfNotNull("do_not_generate_book", doNotGenerateBook)
			addIfNotNull("custom_book_item", customBookItem?.toString())
			addIfNotNull("show_toast", showToast)
			addIfNotNull("use_blocky_font", useBlockyFont)
			addIfNotNull("i18n", i18n)
			addIfNotNull("pause_game", pauseGame)
			addIfNotNull("icon", icon)

		}

		return json
	}

	class Builder private constructor() {

		private var componentSet = false
		private var name: Component? = null
		private var landingText: Component? = null

		private var nameText: String? = null
		private var landingTextText: String? = null
		private var bookTexture: ResourceLocation? = null
		private var fillerTexture: String? = null
		private var craftingTexture: String? = null
		private var textColor: Int? = null
		private var headerColor: Int? = null
		private var nameplateColor: Int? = null
		private var linkColor: Int? = null
		private var linkHoverColor: Int? = null
		private var progressBarColor: Int? = null
		private var progressBarBackground: Int? = null
		private var openSound: ResourceLocation? = null
		private var flipSound: ResourceLocation? = null
		private var showProgress: Boolean? = null
		private var version: String? = null
		private var subtitle: String? = null
		private var creativeTab: String? = null
		private var advancementTab: String? = null
		private var doNotGenerateBook: Boolean? = null
		private var customBookItem: Item? = null
		private var showToast: Boolean? = null
		private var useBlockyFont: Boolean? = null
		private var i18n: Boolean = false
		private var pauseGame = false
		private var icon: ResourceLocation? = null
		private var bookId: String? = null

		fun setBookComponent(
			bookId: String,
			name: Component,
			landingText: Component
		): Builder {

			if (i18n.isNotTrue()) {
				error("Don't use setBookComponent when i18n is false!")
			}

			return this
		}

		companion object {
			fun header() = Builder()
		}

	}

}