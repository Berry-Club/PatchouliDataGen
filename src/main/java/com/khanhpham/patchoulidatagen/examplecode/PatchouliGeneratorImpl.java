package com.khanhpham.patchoulidatagen.examplecode;


import com.khanhpham.patchoulidatagen.provider.PatchouliBookProvider;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookCategory;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookElement;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookEntry;
import dev.aaronhowser.mods.patchoulidatagen.book_element.BookHeader;
import dev.aaronhowser.mods.patchoulidatagen.multiblock.PatchouliMultiblock;
import dev.aaronhowser.mods.patchoulidatagen.page.defaults.*;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class PatchouliGeneratorImpl extends PatchouliBookProvider {
	public PatchouliGeneratorImpl(
			DataGenerator generator, ExistingFileHelper fileHelper,
			String bookName, String modid
	) {
		super(generator, fileHelper, bookName, modid);
	}

	@Override
	protected void buildPages(Consumer<BookElement> consumer) {
		BookHeader header = BookHeader.builder()
				.enableI18n()
				.setBookComponent(
						modid, translate("test_book.name"),
						translate("test_book.landing_text")
				)
				.build(consumer);

		BookCategory category1 = BookCategory.builder()
				.header(header)
				.setDisplay(
						Component.translatable("test_category"),
						Component.translatable("test_category.desc"), Items.DIRT
				)
				.save(consumer, "test");
		BookCategory category2 = BookCategory.Builder.category()
				.header(header)
				.setDisplay(
						"Test Second Category", "test Second Category Description",
						Items.BIRCH_WOOD
				)
				.save(consumer, "test_second_category");

		CraftingRecipePage crafting =
				CraftingRecipePage.builder().mainRecipe(Blocks.CRAFTING_TABLE).build();
		MultiblockPage multiblockPage = MultiblockPage.builder()
				.multiblock(
						"Test Multiblock",
						PatchouliMultiblock.builder()
								.pattern("A   0", "A   A")
								.pattern("AAAAA", "AAAAA")
								.map('A', Blocks.DIRT)
								.map('0', Blocks.DIRT)
				)
				.build();
		ImagePage imagePage = ImagePage.builder()
				.addImage(ResourceLocation.withDefaultNamespace("textures/block/acacia_log.png"))
				.build();
		TextPage textPage = TextPage.builder()
				.text("This is a text page")
				.title("Test Page Title")
				.build();
		SmeltingRecipePage smeltingPage = SmeltingRecipePage.builder()
				.mainRecipe(Blocks.STONE)
				.secondaryRecipe(Blocks.SMOOTH_STONE)
				.build();
		SpotlightPage spotlightPage = SpotlightPage.Builder.builder().item(Items.ITEM_FRAME).build();
		EntityPage entityPage = EntityPage.builder().entity(EntityType.CHICKEN).build();
		/**
		 * @see net.minecraft.world.level.block.StairBlock
		 */
		MultiblockPage multiblockPage1 =
				MultiblockPage.builder()
						.multiblock(
								"test_multiblock_2", PatchouliMultiblock.builder()
										.pattern("XXX0XXX")
										.map('X', Blocks.OAK_STAIRS, StairBlock.FACING, Direction.NORTH)
						)
						.build();

		BookEntry.builder()
				.category(category1)
				.display("Test First Entry", Items.DIAMOND_SWORD)
				.addPage(multiblockPage1)
				.addPage(smeltingPage)
				.addPage(spotlightPage)
				.save(consumer, "test_entry_category_one");

		BookEntry.builder()
				.category(category2)
				.display("Test Second Entry", Items.GOLD_BLOCK)
				.addPage(imagePage)
				.addPage(textPage)
				.addPage(multiblockPage)
				.addPage(entityPage)
				.addPage(crafting)
				.save(consumer, "test_entry_category_2");
	}

	private Component translate(String key) {
		return Component.translatable(key);
	}
}
