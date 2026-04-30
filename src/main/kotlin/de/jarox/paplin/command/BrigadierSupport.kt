package de.jarox.paplin.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.jarox.paplin.annotation.NMS
import de.jarox.paplin.event.listen
import de.jarox.paplin.extension.craftServer
import de.jarox.paplin.extension.onlinePlayers
import de.jarox.paplin.pluginInstance
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.event.player.PlayerJoinEvent

/**
 * Internal support object for managing Brigadier command registration.
 * Handles command tree synchronization with clients and permission setup.
 */
@OptIn(NMS::class)
object BrigadierSupport {
    @PublishedApi
    internal val commands = LinkedHashSet<LiteralArgumentBuilder<CommandSourceStack>>()

    internal var executedDefaultRegistration = false
        private set

    init {
        listen<PlayerJoinEvent> { event ->
            val player = event.player
            val permAttachment = player.addAttachment(pluginInstance)
            for (command in commands) {
                permAttachment.setPermission("minecraft.command.${command.literal}", true)
            }
        }
    }

    /**
     * Resolves the server's command manager.
     */
    @NMS
    fun resolveCommandManager(): Commands = craftServer.server.commands

    internal fun registerAll() {
        executedDefaultRegistration = true

        if (commands.isEmpty()) return

        for (command in commands) {
            resolveCommandManager().dispatcher.register(command)
        }
        if (onlinePlayers.isNotEmpty()) updateCommandTree()
    }

    /**
     * Updates the command tree for all online players.
     */
    @NMS
    fun updateCommandTree() {
        for (player in onlinePlayers) {
            resolveCommandManager().sendCommands((player as CraftPlayer).handle)
        }
    }
}
