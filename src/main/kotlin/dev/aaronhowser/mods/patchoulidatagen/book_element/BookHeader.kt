package dev.aaronhowser.mods.patchoulidatagen.book_element

import com.google.gson.JsonObject
import dev.aaronhowser.mods.patchoulidatagen.Util.addIfNotNull
import dev.aaronhowser.mods.patchoulidatagen.Util.isTrue
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
}