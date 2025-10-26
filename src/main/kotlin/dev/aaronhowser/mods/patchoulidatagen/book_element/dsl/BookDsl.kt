package dev.aaronhowser.mods.patchoulidatagen.book_element.dsl

import dev.aaronhowser.mods.patchoulidatagen.Util.isNotTrue
import dev.aaronhowser.mods.patchoulidatagen.Util.isTrue
import dev.aaronhowser.mods.patchoulidatagen.book_element.Book
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookElement
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import java.util.function.Consumer

class BookDsl {

	private var nameComponent: Component? = null
	private var landingTextComponent: Component? = null
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
	private var i18n: Boolean? = null
	private var pauseGame: Boolean? = null
	private var icon: ResourceLocation? = null
	private var bookModId: String? = null

	fun i18n(enabled: Boolean = true) {
		i18n = enabled
	}

	fun book(
		bookModId: String,
		name: Component,
		landingText: Component
	) {
		require(i18n.isTrue()) { "Don't use component book when i18n is false!" }

		this.bookModId = bookModId
		this.nameComponent = name
		this.landingTextComponent = landingText
	}

	fun book(
		bookModId: String,
		name: String,
		landingText: String
	) {
		require(i18n.isNotTrue()) { "Don't use text book when i18n is true!" }

		this.bookModId = bookModId
		this.nameText = name
		this.landingTextText = landingText
	}

	fun textures(block: Textures.() -> Unit) {
		Textures().apply(block)
	}

	fun colors(block: Colors.() -> Unit) {
		Colors().apply(block)
	}

	fun sounds(block: Sounds.() -> Unit) {
		Sounds().apply(block)
	}

	fun misc(block: Misc.() -> Unit) {
		Misc().apply(block)
	}


	inner class Textures {
		fun book(texture: ResourceLocation) {
			bookTexture = texture
		}

		fun filler(texture: String) {
			fillerTexture = texture
		}

		fun crafting(texture: String) {
			craftingTexture = texture
		}
	}

	inner class Colors {
		fun text(color: Int) {
			textColor = color
		}

		fun header(color: Int) {
			headerColor = color
		}

		fun nameplate(color: Int) {
			nameplateColor = color
		}

		fun link(color: Int, hover: Int) {
			linkColor = color
			linkHoverColor = hover
		}

		fun progressBar(color: Int, background: Int? = null) {
			progressBarColor = color
			background?.let { progressBarBackground = it }
		}
	}

	inner class Sounds {
		fun open(sound: ResourceLocation) {
			openSound = sound
		}

		fun flip(sound: ResourceLocation) {
			flipSound = sound
		}
	}

	inner class Misc {
		fun version(value: String) {
			version = value
		}

		fun subtitle(value: String) {
			subtitle = value
		}

		fun creativeTab(value: String) {
			creativeTab = value
		}

		fun advancementTab(value: String) {
			advancementTab = value
		}

		fun disableBook() {
			doNotGenerateBook = true
		}

		fun disableToast() {
			showToast = false
		}

		fun showProgress(value: Boolean) {
			showProgress = value
		}

		fun useMinecraftFont() {
			useBlockyFont = true
		}

		fun pauseGame() {
			pauseGame = true
		}

		fun icon(value: ResourceLocation) {
			icon = value
		}

		fun customItem(item: Item) {
			customBookItem = item
		}
	}


	fun build(): Book {
		val finalName: String
		val finalLandingText: String

		if (i18n.isTrue()) {
			finalName = nameComponent?.string ?: error("Name component must be set when i18n is enabled!")
			finalLandingText = landingTextComponent?.string ?: error("Landing text component must be set when i18n is enabled!")
		} else {
			finalName = nameText ?: error("Name must be set when i18n is disabled!")
			finalLandingText = landingTextText ?: error("Landing text must be set when i18n is disabled!")
		}

		requireNotNull(bookModId) { "Book ID must be set!" }

		return Book(
			bookModId = bookModId!!,
			name = finalName,
			landingText = finalLandingText,
			bookTexture = bookTexture,
			fillerTexture = fillerTexture,
			craftingTexture = craftingTexture,
			textColor = textColor,
			headerColor = headerColor,
			nameplateColor = nameplateColor,
			linkColor = linkColor,
			linkHoverColor = linkHoverColor,
			progressBarColor = progressBarColor,
			progressBarBackground = progressBarBackground,
			openSound = openSound,
			flipSound = flipSound,
			showProgress = showProgress,
			version = version,
			subtitle = subtitle,
			creativeTab = creativeTab,
			advancementTab = advancementTab,
			doNotGenerateBook = doNotGenerateBook,
			customBookItem = customBookItem,
			showToast = showToast,
			useBlockyFont = useBlockyFont,
			i18n = i18n,
			pauseGame = pauseGame,
			icon = icon
		)
	}

	companion object {
		inline fun createBook(consumer: Consumer<BookElement>, block: BookDsl.() -> Unit): Book {
			val book = BookDsl().apply(block).build()
			consumer.accept(book)
			return book
		}
	}

}