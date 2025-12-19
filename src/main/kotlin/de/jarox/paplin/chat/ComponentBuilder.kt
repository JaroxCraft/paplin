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

class ComponentBuilder(
    val baseCmp: Component,
) {
    constructor(baseTxt: String) : this(Component.text(baseTxt))

    var bold: Boolean? = null
    var italic: Boolean? = null
    var underline: Boolean? = null
    var strikethrough: Boolean? = null
    var obfuscate: Boolean? = null

    var color: TextColor? = null

    var siblingText = empty()

    inline fun text(
        text: String = "",
        builder: ComponentBuilder.() -> Unit = { },
    ) {
        siblingText = siblingText.append(ComponentBuilder(text).apply(builder).build())
    }

    inline fun component(
        component: Component,
        builder: ComponentBuilder.() -> Unit = { },
    ) {
        siblingText = siblingText.append(ComponentBuilder(component).apply(builder).build())
    }

    fun newLine() {
        siblingText = siblingText.append(newline())
    }

    fun emptyLine() {
        newLine()
        newLine()
    }

    fun build(): Component =
        if (siblingText.children().isNotEmpty()) {
            baseCmp.stylize().append(siblingText.stylize())
        } else {
            baseCmp.stylize()
        }

    private fun Component.stylize(): Component {
        var style = style()
        val decorations = style.decorations().toMutableMap()

        decorations[TextDecoration.BOLD] = TextDecoration.State.byBoolean(bold)
        decorations[TextDecoration.ITALIC] = TextDecoration.State.byBoolean(italic)
        decorations[TextDecoration.UNDERLINED] = TextDecoration.State.byBoolean(underline)
        decorations[TextDecoration.STRIKETHROUGH] = TextDecoration.State.byBoolean(strikethrough)
        decorations[TextDecoration.OBFUSCATED] = TextDecoration.State.byBoolean(obfuscate)

        style = style.decorations(decorations)

        color?.let { style = style.color(it) }

        return this.style(style)
    }
}
