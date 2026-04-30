@file:Suppress("MemberVisibilityCanBePrivate", "Unused")

package de.jarox.paplin.chat

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.text.Component.newline
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

/**
 * Opens a [ComponentBuilder].
 *
 * @param baseText the text you want to begin with, you can also let it empty
 * @param builder the builder which can be used to set the style and add child text components
 */
inline fun component(
    baseText: String = "",
    builder: ComponentBuilder.() -> Unit = { },
) = ComponentBuilder(baseText).apply(builder).build()

/**
 * Fluent builder for creating Adventure [Component] objects with style support.
 *
 * @property baseCmp the base component to build upon
 */
class ComponentBuilder(
    val baseCmp: Component,
) {
    constructor(baseTxt: String) : this(Component.text(baseTxt))

    /** Whether the text should be bold. */
    var bold: Boolean? = null

    /** Whether the text should be italic. */
    var italic: Boolean? = null

    /** Whether the text should be underlined. */
    var underline: Boolean? = null

    /** Whether the text should be strikethrough. */
    var strikethrough: Boolean? = null

    /** Whether the text should be obfuscated. */
    var obfuscate: Boolean? = null

    /** The text color. */
    var color: TextColor? = null

    /** The text being built up. */
    var siblingText = empty()

    /**
     * Appends a text component with optional styling.
     *
     * @param text the text to append
     * @param builder the builder for styling the new text
     */
    inline fun text(
        text: String = "",
        builder: ComponentBuilder.() -> Unit = { },
    ) {
        siblingText = siblingText.append(ComponentBuilder(text).apply(builder).build())
    }

    /**
     * Appends an existing component with optional styling.
     *
     * @param component the component to append
     * @param builder the builder for styling the component
     */
    inline fun component(
        component: Component,
        builder: ComponentBuilder.() -> Unit = { },
    ) {
        siblingText = siblingText.append(ComponentBuilder(component).apply(builder).build())
    }

    /** Appends a newline character. */
    fun newLine() {
        siblingText = siblingText.append(newline())
    }

    /** Appends two newline characters (empty line). */
    fun emptyLine() {
        newLine()
        newLine()
    }

    /**
     * Builds and returns the final component.
     *
     * @return the built component
     */
    fun build(): Component =
        if (siblingText.children().isNotEmpty()) {
            baseCmp.stylize().append(siblingText.stylize())
        } else {
            baseCmp.stylize()
        }

    private fun Component.stylize(): Component {
        val newStyle =
            style()
                .decorations(
                    mapOf(
                        TextDecoration.BOLD to TextDecoration.State.byBoolean(bold),
                        TextDecoration.ITALIC to TextDecoration.State.byBoolean(italic),
                        TextDecoration.UNDERLINED to TextDecoration.State.byBoolean(underline),
                        TextDecoration.STRIKETHROUGH to TextDecoration.State.byBoolean(strikethrough),
                        TextDecoration.OBFUSCATED to TextDecoration.State.byBoolean(obfuscate),
                    ),
                ).let { s -> color?.let(s::color) ?: s }

        return style(newStyle)
    }
}
