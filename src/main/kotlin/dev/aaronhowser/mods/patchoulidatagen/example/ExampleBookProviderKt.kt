package dev.aaronhowser.mods.patchoulidatagen.example

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookEntry
import dev.aaronhowser.mods.patchoulidatagen.book_element.Book
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
	bookName: String,
	modId: String
) : PatchouliBookProvider(generator, bookName, modId) {

	override fun buildPages(consumer: Consumer<BookElement>) {

		val header = Book.builder()
			.setBookText(
				bookModId = PatchouliDataGen.MOD_ID,
				name = "Generated via Kotlin!",
				landingText = "This book was generated using the Patchouli DataGen library in Kotlin."
			)
			.creativeTab("minecraft:tools_and_utilities")
			.save(consumer)

		val categoryOne = BookCategory.builder()
			.header(header)
			.setDisplay(
				name = "Category One",
				description = "This is the first category in the Kotlin-generated book.",
				icon = Items.DIRT
			)
			.save(consumer, "category_one")

		val innerCategory = BookCategory.builder()
			.header(header)
			.setDisplay(
				name = "Inner Category",
				description = "This is a sub-category inside Category One.",
				icon = Items.COBBLESTONE
			)
			.parent(categoryOne)
			.save(consumer, "inner_category")

		val textPage = TextPage.builder()
			.title("Welcome to Kotlin DataGen")
			.text("test")
			.build()

		val craftingPage = CraftingRecipePage.builder()
			.mainRecipe(Items.STICK)
			.text(Component.translatable("item.minecraft.stick"))
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