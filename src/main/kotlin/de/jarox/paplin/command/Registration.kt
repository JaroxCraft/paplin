@file:Suppress("unused")

package de.jarox.paplin.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import de.jarox.paplin.annotation.NMS
import net.minecraft.commands.CommandSourceStack

/**
 * Registers this command at the [CommandDispatcher] of the server.
 *
 * @param sendToPlayers whether the new command tree should be sent to
 * all players, this is true by default, but you can disable it if you are
 * calling this function as the server is starting
 */
@NMS
fun LiteralArgumentBuilder<CommandSourceStack>.register(sendToPlayers: Boolean = true) {
    if (!BrigadierSupport.executedDefaultRegistration) {
        BrigadierSupport.commands += this
    } else {
        BrigadierSupport.resolveCommandManager().dispatcher.register(this)
        if (sendToPlayers) {
            BrigadierSupport.updateCommandTree()
        }
    }
}
