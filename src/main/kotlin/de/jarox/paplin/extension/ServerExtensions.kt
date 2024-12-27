@file:Suppress("unused")

package de.jarox.paplin.extension

import de.jarox.paplin.annotation.NMS
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.CraftServer

val server get() = Bukkit.getServer()

@NMS
val craftServer get() = server as CraftServer

fun broadcast(msg: Component) = server.broadcast(msg)
