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

    @NMS
    fun updateCommandTree() {
        for (player in onlinePlayers) {
            resolveCommandManager().sendCommands((player as CraftPlayer).handle)
        }
    }
}
