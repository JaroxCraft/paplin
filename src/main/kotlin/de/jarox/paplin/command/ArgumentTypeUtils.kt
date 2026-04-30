@file:Suppress("ktlint:standard:no-wildcard-imports")

package de.jarox.paplin.command

import com.mojang.brigadier.arguments.*

/**
 * Utility functions for working with Brigadier argument types.
 */
object ArgumentTypeUtils {
    /**
     * Creates a Brigadier [ArgumentType] from a reified Kotlin type.
     *
     * @throws IllegalArgumentException if the type does not have a corresponding default argument type
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T> fromReifiedType() =
        when (T::class) {
            Boolean::class -> BoolArgumentType.bool()
            Int::class -> IntegerArgumentType.integer()
            Long::class -> LongArgumentType.longArg()
            Float::class -> FloatArgumentType.floatArg()
            Double::class -> DoubleArgumentType.doubleArg()
            String::class -> StringArgumentType.string()

            else -> throw IllegalArgumentException(
                "The specified type '${T::class.qualifiedName}' does not have corresponding default argument type",
            )
        } as ArgumentType<T>
}
