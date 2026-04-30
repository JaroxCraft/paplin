@file:Suppress("unused")

package de.jarox.paplin.extension

import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * Returns a collection of all currently online players.
 */
val onlinePlayers: Collection<Player> get() = Bukkit.getOnlinePlayers()
