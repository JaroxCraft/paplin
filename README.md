# Paplin

**Paplin** is a lightweight Kotlin-based library for developing Minecraft Paper plugins. It simplifies the development process by seamlessly integrating Kotlin's powerful language features with the Paper API, enabling fast, clean, and efficient plugin creation.

## Features

- 🛠️ **Kotlin Integration**: Leverage Kotlin's concise and expressive syntax.
- 📦 **Lightweight**: Minimal overhead, focusing on enhancing functionality without unnecessary complexity.
- 🚀 **Developer-Friendly**: Simplifies common plugin development tasks.

## Getting Started

### Installation

Clone the [template repository](https://github.com/new?template_name=paplin-example-project&template_owner=JaroxCraft)

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

See the [ExamplePlugin.kt](https://github.com/JaroxCraft/paplin-example-project/blob/master/src/main/kotlin/de/jarox/paplin/example/ExamplePlugin.kt) in the `paplin-example-project` repository for a complete usage example.

## Documentation

- **API Reference**: Available at [jaroxcraft.github.io/paplin](https://jaroxcraft.github.io/paplin)

## License

Paplin is licensed under the [MIT License](LICENSE).