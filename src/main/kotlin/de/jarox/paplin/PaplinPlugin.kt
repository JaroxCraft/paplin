@file:Suppress("EmptyMethod")

package de.jarox.paplin

import de.jarox.paplin.command.BrigadierSupport
import org.bukkit.plugin.java.JavaPlugin

/**
 * Global reference to the currently loaded PaplinPlugin instance.
 *
 * @throws IllegalStateException if accessed before the plugin is loaded
 */
lateinit var pluginInstance: PaplinPlugin
    private set

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
     * Initializes the PluginInstance and calls the load method.
     * Supports plugin reloads by allowing reassignment.
     */
    final override fun onLoad() {
        // Allow reassignment to support plugin reloads
        pluginInstance = this
        load()
    }

    /**
     * Calls the enable method.
     */
    final override fun onEnable() {
        enable()

        if (this.isEnabled) {
            BrigadierSupport.registerAll()
        }
    }

    /**
     * Calls the disable method.
     */
    final override fun onDisable() {
        disable()
    }
}
