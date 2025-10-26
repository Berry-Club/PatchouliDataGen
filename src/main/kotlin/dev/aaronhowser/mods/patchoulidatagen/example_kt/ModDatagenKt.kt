package dev.aaronhowser.mods.patchoulidatagen.example_kt

import dev.aaronhowser.mods.patchoulidatagen.PatchouliDataGen
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = PatchouliDataGen.MOD_ID)
object ModDatagenKt {

	@SubscribeEvent
	fun onGatherData(event: GatherDataEvent) {
		val generator = event.generator
		val bookProvider = ExampleBookProviderKt(generator, "example_book_kt", PatchouliDataGen.MOD_ID)
		generator.addProvider(event.includeClient(), bookProvider)
	}

}