package dev.aaronhowser.mods.patchoulidatagen.example_kt

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.CraftingRecipePage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import net.minecraft.data.DataGenerator
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Items
import java.util.function.Consumer

class ExampleBookProviderKt(
	generator: DataGenerator,
	bookName: String,
	modId: String
) : PatchouliBookProvider(generator, bookName, modId) {

	override fun buildPages(consumer: Consumer<PatchouliBookElement>) {

		val book = PatchouliBook.builder()
			.setBookText(
				bookModId = PatchouliDataGen.MOD_ID,
				name = "Generated via Kotlin!",
				landingText = "This book was generated using the Patchouli DataGen library in Kotlin."
			)
			.creativeTab("minecraft:tools_and_utilities")
			.save(consumer)

		val categoryOne = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Category One Kotlin",
				description = "This is the first category in the Kotlin-generated book.",
				icon = Items.DIRT
			)
			.save(consumer, "category_one")

		val innerCategory = PatchouliBookCategory.builder()
			.book(book)
			.setDisplay(
				name = "Inner Category",
				description = "This is a sub-category inside Category One.",
				icon = Items.COBBLESTONE
			)
			.parent(categoryOne)
			.save(consumer, "inner_category")

		PatchouliBookEntry.builder()
			.category(categoryOne)
			.addPage(
				TextPage.builder()
					.title("Welcome to Kotlin DataGen")
					.text(
						lines(
							"This is a line of text.",
							"Here's another line of text.",
							"${DARK_GREEN}DARK GREEN${RESET} text!"
						)
					)
					.build()
			)
			.addPage(
				TextPage.builder()
					.title("More lines!!")
					.text(
						doubleSpacedLines(
							"This is a line of text.",
							"Here's another line of text.",
							"And yet another line of text.${BR}You can also do it like this!"
						)
					)
					.build()
			)
			.addPage(
				TextPage.builder()
					.text(
						StringBuilder()
							.append(
								"${LI}${RED}list${LI}next line but still red because of no reset"
							)
							.append(BR)
							.append("Not in the list any more but STILL RED!")
							.append(BR)
							.append("${RESET}Now it's good :)")
							.toString()
					)
					.build()
			)
			.addPage(
				CraftingRecipePage.builder()
					.mainRecipe(Items.STICK)
					.text(Component.translatable("item.minecraft.stick"))
					.build()
			)
			.display("One!!!", Items.APPLE)
			.save(consumer, "entry_one_kotlin")

	}

}