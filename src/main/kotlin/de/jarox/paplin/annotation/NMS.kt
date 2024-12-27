package de.jarox.paplin.annotation

/**
 * This annotation is used to mark APIs that are subject to change in future versions of Minecraft.
 */
@RequiresOptIn(level = RequiresOptIn.Level.WARNING, message = "This may change in other minecraft versions")
annotation class NMS
