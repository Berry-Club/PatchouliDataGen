package dev.aaronhowser.mods.patchoulidatagen.example_java;

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = PatchouliDataGen.MOD_ID)
public class ModDatagenJava {

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExampleBookProviderJava bookProvider = new ExampleBookProviderJava(generator, "example_book_java", PatchouliDataGen.MOD_ID);
		generator.addProvider(event.includeClient(), bookProvider);
	}

}
