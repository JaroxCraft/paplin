@file:Suppress("unused")

package de.jarox.paplin.extension

import de.jarox.paplin.annotation.NMS
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.CraftServer

/**
 * Returns the current server instance.
 */
val server get() = Bukkit.getServer()

/**
 * Returns the current server instance cast to CraftServer.
 * This provides access to NMS APIs.
 *
 * @throws ClassCastException if the server is not a CraftServer
 */
@NMS
val craftServer get() = server as CraftServer

/**
 * Broadcasts a message to all players on the server.
 *
 * @param msg the component to broadcast
 */
fun broadcast(msg: Component) = server.broadcast(msg)
