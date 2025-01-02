# Paplin

**Paplin** is a lightweight Kotlin-based library for developing Minecraft Paper plugins. It simplifies the development process by seamlessly integrating Kotlin's powerful language features with the Paper API, enabling fast, clean, and efficient plugin creation.

## Features

- 🛠️ **Kotlin Integration**: Leverage Kotlin's concise and expressive syntax.
- 📦 **Lightweight**: Minimal overhead, focusing on enhancing functionality without unnecessary complexity.
- 🚀 **Developer-Friendly**: Simplifies common plugin development tasks.

## Getting Started

### Installation

Clone the [template repository](https://github.com/JaroxCraft/paplin-example-project)

or

Add Paplin as a dependency in your `build.gradle.kts`:

```kotlin
repositories {
    maven {
        name = "Paplin"
        url = uri("https://repo.repsy.io/mvn/jaroxcraft/paplin")
    }
}

dependencies {
    implementation("de.jarox:paplin:<latest release>")
}
```

### Example Usage

Create a simple plugin using Paplin:

```kotlin
import de.jarox.paplin.PaplinPlugin
import de.jarox.paplin.chat.component
import de.jarox.paplin.command.argument
import de.jarox.paplin.command.command
import de.jarox.paplin.command.runs
import de.jarox.paplin.event.listen
import de.jarox.paplin.extension.broadcast
import net.kyori.adventure.text.Component
import org.bukkit.event.block.BlockBreakEvent

class MyPlugin : PaplinPlugin() {

    override fun enable() {
        // register a simple command
        command("mycommand") {
            runs {
                // automatically only allow players to execute this command
                player.sendMessage(Component.text("Hello, world!"))
            }
            
            // simple argument
            argument<String>("message") {
                runs {
                    this.source.sender.sendMessage(component(getArgument("message")))
                }
            }
        }

        // register a simple listener
        listen<BlockBreakEvent> {
            broadcast(Component.text("${it.player.name} broke a block!"))
        }
    }
}
```

## License

Paplin is licensed under the [MIT License](LICENSE).