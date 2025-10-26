package dev.aaronhowser.mods.patchoulidatagen.example

import dev.aaronhowser.mods.patchoulidatagen.book_element.BookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookEntry
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookHeader
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.CraftingRecipePage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import net.minecraft.data.DataGenerator
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.item.Items
import net.neoforged.neoforge.common.data.ExistingFileHelper
import java.util.function.Consumer

class ExampleBookProviderKt(
	generator: DataGenerator,
	existingFileHelper: ExistingFileHelper?,
	bookName: String,
	modId: String
) : PatchouliBookProvider(generator, existingFileHelper, bookName, modId) {

	override fun buildPages(consumer: Consumer<BookElement>) {

		val header = BookHeader.builder()
			.setBookText(
				bookId = "$modId.book.kt",
				name = "Generated via Kotlin!",
				landingText = "This book was generated using the Patchouli DataGen library in Kotlin."
			)
			.build(consumer)

		val categoryOne = BookCategory.builder()
			.header(header)
			.setDisplay(
				title = "Category One",
				description = "This is the first category in the Kotlin-generated book.",
				icon = Items.DIRT
			)
			.save(consumer, "category_one")

		val textPage = TextPage.builder()
			.title("Welcome to Kotlin DataGen")
			.text(
				"""
				This is a test

				of multiple lines
			""".trimIndent()
			)
			.build()

		val craftingPage = CraftingRecipePage.builder()
			.mainRecipe(Items.STICK)
			.build()

		BookEntry.builder()
			.category(categoryOne)
			.addPage(textPage)
			.addPage(craftingPage)
			.display("One!!!", Items.APPLE)
			.save(consumer, "entry_one")

	}

	fun translate(key: String): MutableComponent = Component.translatable(key)
}