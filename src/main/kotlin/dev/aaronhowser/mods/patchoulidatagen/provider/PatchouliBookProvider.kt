package dev.aaronhowser.mods.patchoulidatagen.provider

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry
import net.minecraft.core.RegistryAccess
import net.minecraft.data.CachedOutput
import net.minecraft.data.DataGenerator
import net.minecraft.data.DataProvider
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

abstract class PatchouliBookProvider(
	protected val generator: DataGenerator,
	protected val registryAccess: RegistryAccess,
	protected val bookName: String,
	protected val modId: String
) : DataProvider {

	protected val gson: Gson = GsonBuilder().setPrettyPrinting().create()

	override fun getName(): String {
		return "Patchouli Book: $bookName"
	}

	override fun run(output: CachedOutput): CompletableFuture<*> {
		val dataFolder = generator.packOutput

		val bookLocations = mutableSetOf<String>()
		val bookDefaultPath = "assets/$modId/patchouli_books/$bookName/en_us"

		val futures = mutableListOf<CompletableFuture<*>>()

		val elementConsumer: Consumer<PatchouliBookElement> = Consumer { element ->
			val addedSuccessfully = bookLocations.add(element.getSaveName())

			if (!addedSuccessfully) {
				val rl = ResourceLocation.fromNamespaceAndPath(modId, element.getSaveName())
				error("Duplicate book element [$rl]")
			}

			when (element) {
				is PatchouliBookEntry -> {
					val entryFolder = resolvePath(
						dataFolder,
						"$bookDefaultPath/entries/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, entryFolder)
				}

				is PatchouliBookCategory -> {
					val categoryFolder = resolvePath(
						dataFolder,
						"$bookDefaultPath/categories/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, categoryFolder)
				}

				is PatchouliBook -> {
					val headerFolder = resolvePath(
						dataFolder,
						"data/$modId/patchouli_books/$bookName/${element.getSaveName()}.json"
					)

					saveData(futures, gson, output, element, headerFolder)
				}
			}

		}

		buildPages(elementConsumer)

		return CompletableFuture.allOf(*futures.toTypedArray())
	}

	private fun <T : PatchouliBookElement> saveData(
		futures: MutableList<CompletableFuture<*>>,
		gson: Gson,    // unused currently
		cache: CachedOutput,
		bookElement: T,
		bookElementPath: Path
	) {
		val future = DataProvider.saveStable(cache, bookElement.toJson(registryAccess), bookElementPath)
		futures.add(future)
	}

	private fun resolvePath(path: PackOutput, pathOther: String): Path {
		return path.outputFolder.resolve(pathOther)
	}

	abstract fun buildPages(consumer: Consumer<PatchouliBookElement>)

	companion object {
		const val RESET = "$()"
		const val BR = "$(br)"
		const val BR2 = "$(br2)"
		const val LI = "$(li)"
		const val LI2 = "$(li2)"
		const val LI3 = "$(li3)"

		const val OBFUSCATED = "$(k)"
		const val BOLD = "$(l)"
		const val STRIKETHROUGH = "$(m)"
		const val UNDERLINE = "$(n)"
		const val ITALIC = "$(o)"

		enum class TextColor(val code: String) {
			BLACK("$(0)"),
			DARK_BLUE("$(1)"),
			DARK_GREEN("$(2)"),
			DARK_AQUA("$(3)"),
			DARK_RED("$(4)"),
			DARK_PURPLE("$(5)"),
			GOLD("$(6)"),
			GRAY("$(7)"),
			DARK_GRAY("$(8)"),
			BLUE("$(9)"),
			GREEN("$(a)"),
			AQUA("$(b)"),
			RED("$(c)"),
			LIGHT_PURPLE("$(d)"),
			YELLOW("$(e)"),
			WHITE("$(f)");

			override fun toString(): String {
				return code
			}
		}

		@JvmStatic
		fun colored(textColor: TextColor, text: String): String {
			return "${textColor.code}$text$RESET"
		}

		@JvmStatic
		fun internalLink(
			linkTo: String,
			text: String
		): String = "$(l:$linkTo)$text$(/l)"

		@JvmStatic
		fun internalLink(linkTo: String, anchor: String, text: String): String = "$(l:$linkTo#$anchor)$text$(/l)"

		@JvmStatic
		fun keybind(keybind: String): String = "$(k:$keybind)"

		@JvmStatic
		fun tooltip(tooltip: String, text: String): String = "$(t:$tooltip)$text$(/t)"

		@JvmStatic
		fun command(command: String, text: String): String = "$(c:/$command)$text$(/c)"

		@JvmStatic
		fun lines(vararg lines: String): String = lines.joinToString(BR)

		@JvmStatic
		fun doubleSpacedLines(vararg lines: String): String = lines.joinToString(BR2)
	}

}