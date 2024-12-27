@file:Suppress("unused")

package de.jarox.paplin.extension

import org.bukkit.Bukkit
import org.bukkit.entity.Player

val onlinePlayers: Collection<Player> get() = Bukkit.getOnlinePlayers()
