package me.beanbag.nuker

import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap
import me.beanbag.nuker.command.ICommand
import me.beanbag.nuker.command.commands.*
import me.beanbag.nuker.handlers.InventoryHandler
import me.beanbag.nuker.module.Module
import me.beanbag.nuker.module.modules.*
import me.beanbag.nuker.module.modules.nuker.Nuker
import net.minecraft.client.MinecraftClient
import net.minecraft.util.Formatting
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color

object ModConfigs {
    //Mod specific
    val modColor = Formatting.BLUE.colorValue ?: Color(0, 0, 255).rgb
    const val MOD_NAME = "Nuker"
    const val COMMAND_PREFIX = "&&"

    val mc: MinecraftClient = MinecraftClient.getInstance()
    var meteorIsPresent = false
    var meteorIsLoaded = false
    var rusherIsPresent = false
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)

    val inventoryHandler = InventoryHandler()

    val commands: List<ICommand> = listOf(
        HelpCommand(),
        HelpModulesCommand(),
        HelpModuleCommand(),
        HelpModuleSettingCommand(),
        ListCommand(),
        ListModuleCommand(),
        ToggleModuleCommand(),
        SetModuleSettingCommand(),
        SetModuleListSettingCommand(),
    )

    var modules: MutableMap<Class<out Module>, Module> =
        listOf(
            CoreConfig,
            Nuker,
            SourceRemover(),
            UnfocusedCPU(),
            FastBreak(),
            EquipmentSaver(),
        ).associateByTo(Reference2ReferenceOpenHashMap()) { it.javaClass }

    fun getModuleByName(name: String): Module? {
        return modules.values.find { it.name.equals(name, true) }
    }

    fun <T:Module>getModuleByClass(clazz: Class<out T>): T? {
        @Suppress("UNCHECKED_CAST")
        return modules[clazz] as T?
    }
}