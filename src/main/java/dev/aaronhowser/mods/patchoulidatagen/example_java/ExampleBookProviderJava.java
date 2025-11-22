package dev.aaronhowser.mods.patchoulidatagen.example_java;

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen;
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBook;
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookCategory;
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookElement;
import dev.aaronhowser.mods.patchoulidatagen.book_element.PatchouliBookEntry;
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.CraftingRecipePage;
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.TextPage;
import dev.aaronhowser.mods.patchoulidatagen.provider.PatchouliBookProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ExampleBookProviderJava extends PatchouliBookProvider {

	public ExampleBookProviderJava(
			@NotNull DataGenerator generator,
			@NotNull HolderLookup.Provider holderLookupProvider,
			@NotNull String bookName,
			@NotNull String modId
	) {
		super(generator, holderLookupProvider, bookName, modId);
	}

	@Override
	public void buildPages(@NotNull Consumer<@NotNull PatchouliBookElement> consumer) {

		PatchouliBook book = PatchouliBook.builder()
				.setBookText(
						PatchouliDataGen.MOD_ID,
						"Generated via Java!",
						"This book was generated using the Patchouli DataGen library in Java."
				)
				.creativeTab("minecraft:tools_and_utilities")
				.save(consumer);

		PatchouliBookCategory categoryOne = PatchouliBookCategory.builder()
				.book(book)
				.setDisplay(
						"Category One Java",
						"This is the first category in the Java-generated book.",
						Items.DIRT
				)
				.save(consumer, "category_one");

		PatchouliBookCategory innerCategory = PatchouliBookCategory.builder()
				.book(book)
				.setDisplay(
						"Inner Category",
						"This is a sub-category inside Category One.",
						Items.COBBLESTONE
				)
				.parent(categoryOne)
				.save(consumer, "inner_category");

		TextPage textPage = TextPage.builder()
				.title("Welcome to Kotlin DataGen")
				.text("test")
				.build();

		CraftingRecipePage craftingPage = CraftingRecipePage.builder()
				.mainRecipe(Items.STICK)
				.text(Component.translatable("item.minecraft.stick"))
				.build();

		PatchouliBookEntry.builder()
				.category(categoryOne)
				.addPage(textPage)
				.addPage(craftingPage)
				.display("One!!!", Items.APPLE)
				.save(consumer, "entry_one_java");
	}
}
