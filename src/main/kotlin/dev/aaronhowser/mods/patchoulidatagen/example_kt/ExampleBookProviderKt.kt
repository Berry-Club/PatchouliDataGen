package dev.aaronhowser.mods.patchoulidatagen.example_kt

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import dev.aaronhowser.mods.patchoulidatagen.multiblock.PatchouliMultiblock
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.CraftingRecipePage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.MultiblockPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.SpotlightPage
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider.Companion.TextColor
import net.minecraft.core.Direction
import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistryAccess
import net.minecraft.core.component.DataComponents
import net.minecraft.data.DataGenerator
import net.minecraft.network.chat.Component
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.EndRodBlock
import net.neoforged.neoforge.common.Tags
import java.util.function.Consumer

class ExampleBookProviderKt(
	generator: DataGenerator,
	registries: HolderLookup.Provider,
	bookName: String,
	modId: String
) : PatchouliBookProvider(generator, registries, bookName, modId) {

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
			.category(innerCategory)
			.display(
				entryName = "Artificial End Portal",
				icon = Items.END_PORTAL_FRAME
			)
			.addPage(
				MultiblockPage.builder()
					.name("Artificial End Portal")
					.multiblock(
						"Artificial End Portal",
						PatchouliMultiblock.builder()
							.setSymmetrical()
							.pattern(
								arrayOf(
									"     ",
									"     ",
									"  E  ",
									"     ",
									"     ",
								),
								arrayOf(
									"     ",
									"     ",
									"  R  ",
									"     ",
									"     ",
								),
								arrayOf(
									"     ",
									"     ",
									"     ",
									"     ",
									"     ",
								),
								arrayOf(
									"     ",
									"     ",
									"     ",
									"     ",
									"     ",
								),
								arrayOf(
									"BBBBB",
									"B   B",
									"B 0 B",
									"B   B",
									"BBBBB"
								),
								arrayOf(
									"     ",
									" EEE ",
									" EEE ",
									" EEE ",
									"     ",
								)
							)
							.map('B', Tags.Blocks.OBSIDIANS)
							.map('E', Tags.Blocks.END_STONES)
							.map('R', Blocks.END_ROD, EndRodBlock.FACING, Direction.DOWN)
							.build()
					)
					.build()
			)
			.save(consumer, "artificial_end_portal")

		PatchouliBookEntry.builder()
			.category(categoryOne)
			.addPage(
				SpotlightPage.builder()
					.addItemTag(ItemTags.COAL_ORES)
					.addItemLike(Items.DIAMOND_SHOVEL)
					.addItemStack(Items.APPLE.defaultInstance.copyWithCount(5))
					.addItemStack(
						Items.GOLDEN_HOE
							.defaultInstance
							.apply {
								set(DataComponents.ITEM_NAME, Component.literal("Custom Named Golden Hoe"))
							}
					)
					.text("Test")
					.build()
			)
			.display("Test", Items.DIAMOND)
			.save(consumer, "multiple_items_test")

		PatchouliBookEntry.builder()
			.category(categoryOne)
			.addPage(
				TextPage.builder()
					.title("Welcome to Kotlin DataGen")
					.text(
						lines(
							"This is a line of text.",
							"Here's another line of text.",
							"${TextColor.DARK_GREEN}DARK GREEN${RESET} text!"
						)
					)
					.build()
			)
			.addPage(
				TextPage.builder()
					.text(
						StringBuilder()
							.append(
								"${LI}${TextColor.RED}list${LI}next line but still red because of no reset"
							)
							.append(BR)
							.append("Not in the list any more but STILL RED!")
							.append(BR)
							.append("${RESET}Now it's good :)")
							.append(BR)
							.append(colored(TextColor.GOLD, "This is gold text using the Color enum!"))
							.toString()
					)
					.build()
			)
			.addPage(
				CraftingRecipePage.builder()
					.mainRecipe(Items.STICK)
					.text(Component.translatable("item.minecraft.stick"))
					.anchor("anchor")
					.build()
			)
			.display("One!!!", Items.APPLE)
			.save(consumer, "entry_one_kotlin")

	}

}