package de.jarox.paplin

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIPaperConfig
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
abstract class PaplinPlugin : JavaPlugin() {
    /**
     * Method to be overridden for custom load logic.
     * This method is called during the plugin's load phase.
     * @see JavaPlugin.onLoad
     */
    open fun load() {}

    /**
     * This method is called during the plugin's enable phase.
     * @see JavaPlugin.onEnable
     */
    open fun enable() {}

    /**
     * This method is called during the plugin's disable phase.
     * @see JavaPlugin.onDisable
     */
    open fun disable() {}

    /**
     * Initializes the CommandAPI, then calls the load method.
     */
    final override fun onLoad() {
        CommandAPI.onLoad(CommandAPIPaperConfig(this))
        load()
    }

    /**
     * Enables the CommandAPI and calls the enable method.
     */
    final override fun onEnable() {
        CommandAPI.onEnable()
        enable()
    }

    /**
     * Calls the disable method and disables the CommandAPI gracefully.
     */
    final override fun onDisable() {
        try {
            disable()
        } finally {
            CommandAPI.onDisable()
        }
    }
}
