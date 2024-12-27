@file:Suppress("unused")

package de.jarox.paplin.event

import de.jarox.paplin.pluginInstance
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

/**
 * Unregisters this listener from all events.
 */
fun Listener.unregister() = HandlerList.unregisterAll(this)

/**
 * Registers an event listener for the specified event type.
 *
 * @param T the type of the event
 * @param priority the priority of the event listener
 * @param ignoreCancelled whether to ignore canceled events
 * @param executor the function to execute when the event is triggered
 */
inline fun <reified T : Event> Listener.register(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    noinline executor: (Listener, Event) -> Unit,
) {
    pluginInstance.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        executor,
        pluginInstance,
        ignoreCancelled,
    )
}

/**
 * A simple event listener that handles a specific type of event.
 *
 * @param T the type of the event
 * @property priority the priority of the event listener
 * @property ignoreCancelled whether to ignore canceled events
 */
abstract class SimpleListener<T : Event>(
    val priority: EventPriority,
    val ignoreCancelled: Boolean,
) : Listener {
    /**
     * Called when the event is triggered.
     *
     * @param event the event
     */
    abstract fun onEvent(event: T)
}

/**
 * Registers this listener.
 *
 * @param T the type of the event
 */
inline fun <reified T : Event> SimpleListener<T>.register() {
    pluginInstance.server.pluginManager.registerEvent(
        T::class.java,
        this,
        priority,
        { _, event -> (event as? T)?.let { onEvent(it) } },
        pluginInstance,
        ignoreCancelled,
    )
}

/**
 * Creates and optionally registers a simple event listener.
 *
 * @param T the type of the event
 * @param priority the priority of the event listener
 * @param ignoreCancelled whether to ignore canceled events
 * @param register whether to register the listener automatically
 * @param onEvent the function to execute when the event is triggered
 * @return the created simple event listener
 */
inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    register: Boolean = true,
    crossinline onEvent: (event: T) -> Unit,
): SimpleListener<T> {
    val listener =
        object : SimpleListener<T>(priority, ignoreCancelled) {
            override fun onEvent(event: T) = onEvent(event)
        }
    if (register) listener.register()
    return listener
}
