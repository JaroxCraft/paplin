@file:Suppress("unused")

package de.jarox.paplin.scheduler

import de.jarox.paplin.PaplinPlugin
import de.jarox.paplin.command.CommandContext
import de.jarox.paplin.pluginInstance
import org.bukkit.scheduler.BukkitTask

/**
 * Runs a task synchronously on the main server thread, optionally delayed.
 *
 * @param ticks the delay in server ticks before execution (default: 0 = immediate)
 * @param block the code to execute on the main thread
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun PaplinPlugin.runSync(
    ticks: Long = 0,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskLater(this, block, ticks)

/**
 * Runs a task asynchronously off the main server thread.
 *
 * @param block the code to execute asynchronously
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun PaplinPlugin.runAsync(block: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, block)

/**
 * Runs a repeating task synchronously on the main server thread.
 *
 * @param interval the interval in server ticks between executions
 * @param delay the initial delay in server ticks before the first execution (default: 0)
 * @param block the code to execute repeatedly on the main thread
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun PaplinPlugin.runTimer(
    interval: Long,
    delay: Long = 0,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskTimer(this, block, delay, interval)

/**
 * Runs a repeating task asynchronously off the main server thread.
 *
 * @param interval the interval in server ticks between executions
 * @param delay the initial delay in server ticks before the first execution (default: 0)
 * @param block the code to execute repeatedly off the main thread
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun PaplinPlugin.runAsyncTimer(
    interval: Long,
    delay: Long = 0,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, block, delay, interval)

/**
 * Convenience function to run a task on the next tick from within a command context.
 *
 * This is equivalent to `runSync(ticks = 1)` and is useful for deferring work
 * to the next server tick, e.g., to avoid modifying world state during certain events.
 *
 * @param block the code to execute on the main thread on the next tick
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun CommandContext.runNextTick(block: () -> Unit): BukkitTask = server.scheduler.runTaskLater(pluginInstance, block, 1)

/**
 * Convenience function to run a task after a specified delay from within a command context.
 *
 * @param ticks the delay in server ticks before execution
 * @param block the code to execute on the main thread after the delay
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun CommandContext.runLater(
    ticks: Long,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskLater(pluginInstance, block, ticks)
