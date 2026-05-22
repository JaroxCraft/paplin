@file:Suppress("unused")

package de.jarox.paplin.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask

/**
 * Runs a task synchronously on the main server thread, optionally delayed.
 *
 * @param ticks the delay in server ticks before execution (default: 0 = immediate)
 * @param block the code to execute on the main thread
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun Plugin.runSync(
    ticks: Long = 0,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskLater(this, block, ticks)

/**
 * Runs a task asynchronously off the main server thread.
 *
 * @param block the code to execute asynchronously
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun Plugin.runAsync(block: () -> Unit): BukkitTask = server.scheduler.runTaskAsynchronously(this, block)

/**
 * Runs a repeating task synchronously on the main server thread.
 *
 * @param interval the interval in server ticks between executions
 * @param delay the initial delay in server ticks before the first execution (default: 0)
 * @param block the code to execute repeatedly on the main thread
 * @return the scheduled [BukkitTask] for cancellation or inspection
 */
fun Plugin.runTimer(
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
fun Plugin.runAsyncTimer(
    interval: Long,
    delay: Long = 0,
    block: () -> Unit,
): BukkitTask = server.scheduler.runTaskTimerAsynchronously(this, block, delay, interval)
