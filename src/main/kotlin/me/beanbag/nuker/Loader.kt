package me.beanbag.nuker

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import me.beanbag.nuker.modules.Module
import me.beanbag.nuker.modules.Nuker

class Loader : ModInitializer {

    companion object {
        val mc: MinecraftClient = MinecraftClient.getInstance()
        var meteorIsPresent = false
        val LOGGER: Logger = LoggerFactory.getLogger("Nuker")

        var modules: MutableMap<Class<out Module>, Module> =
            listOf(Nuker).associateByTo(Reference2ReferenceOpenHashMap()) { it.javaClass }
    }

    override fun onInitialize() {
        meteorIsPresent = FabricLoader.getInstance().getModContainer("meteor-client").isPresent

        ClientTickEvents.START_CLIENT_TICK.register {
            modules.values.forEach {
                if (it.enabled) {
                    it.onTick()
                }
            }
        }

        LOGGER.info("Initialized Nuker!")
    }
}