@file:Suppress("unused")

package de.jarox.paplin.event

import de.jarox.paplin.PluginInstance
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

fun Listener.unregister() = HandlerList.unregisterAll(this)

inline fun <reified T : Event> Listener.register(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline executor: (Listener, Event) -> Unit,
) {
    PluginInstance.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        executor,
        PluginInstance,
        ignoreCancelled
    )
}

abstract class SimpleListener<T : Event>(
    val priority: EventPriority,
    val ignoreCancelled: Boolean,
) : Listener {
    abstract fun onEvent(event: T)
}

inline fun <reified T : Event> SimpleListener<T>.register() {
    PluginInstance.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        { _, event -> (event as? T)?.let { onEvent(it) } },
        PluginInstance,
        ignoreCancelled
    )
}

inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    register: Boolean = true,
    crossinline onEvent: (event: T) -> Unit,
): SimpleListener<T> {
    val listener = object : SimpleListener<T>(priority, ignoreCancelled) {
        override fun onEvent(event: T) = onEvent(event)
    }
    if (register) listener.register()
    return listener
}