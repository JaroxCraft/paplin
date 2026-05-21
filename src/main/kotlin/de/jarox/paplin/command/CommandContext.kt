@file:Suppress("unused")
@file:OptIn(NMS::class)

package de.jarox.paplin.command

import com.mojang.brigadier.context.CommandContext
import de.jarox.paplin.annotation.NMS
import net.minecraft.commands.CommandSourceStack
import org.bukkit.Location
import org.bukkit.Server
import org.bukkit.World
import org.bukkit.entity.Player

/**
 * Provides context for command execution, including access to command arguments,
 * the source of the command, and convenient access to common Bukkit objects.
 *
 * @property nmsContext the underlying Brigadier command context
 */
class CommandContext(
    val nmsContext: CommandContext<CommandSourceStack>,
) {
    /**
     * Get the value of the given argument.
     */
    inline fun <reified T> getArgument(name: String): T = nmsContext.getArgument(name, T::class.java)

    /**
     * The source / sender which executed this command.
     */
    val source: CommandSourceStack get() = nmsContext.source

    /**
     * Validates that this command was executed by a player (sends an error message to the sender if this is not the case)
     * and returns the [Player].
     */
    val player: Player
        get() = nmsContext.source.bukkitSender as? Player ?: throw CommandSourceStack.ERROR_NOT_PLAYER.create()

    /**
     * The world where the source of this command currently is in.
     *
     * This could be null and therefore throw an exception if called,
     * e.g. when the command is executed by the console or datapack functions,
     * but these cases are rare.
     */
    val world: World get() = nmsContext.source.bukkitWorld!!

    /**
     * The position of the source from this command.
     */
    val position: Location
        get() =
            with(nmsContext.source.position) {
                Location(nmsContext.source.bukkitWorld, x, y, z)
            }

    /**
     * The current server instance.
     */
    val server: Server get() = nmsContext.source.server.server
}
