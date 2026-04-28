# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Project Is

Paplin is a Kotlin library that wraps the Paper Minecraft server API with idiomatic Kotlin DSLs. It is published to a Repsy Maven repository and consumed by Minecraft plugin projects. There is no application entrypoint — the library is exercised by downstream plugin projects.

## Commands

```bash
# Build
./gradlew build

# Lint (check only)
./gradlew ktlintCheck

# Auto-format
./gradlew ktlintFormat

# Full CI check (lint + compile)
./gradlew check

# Publish to Repsy (requires REPSY_USER and REPSY_PASSWORD env vars)
./gradlew publish
```

There are no tests — this is a library without a test suite.

## Architecture

Source lives in `src/main/kotlin/de/jarox/paplin/` and is organized into five areas:

### Plugin lifecycle (`PaplinPlugin.kt`)
Abstract base class that plugin authors extend instead of `JavaPlugin`. Provides `load()`, `enable()`, `disable()` lifecycle hooks and wires up the Brigadier command registration on enable.

### Command DSL (`command/`)
A Brigadier wrapper with Kotlin-idiomatic ergonomics:
- `Creation.kt` — `command { }` builder that registers to `BrigadierSupport`
- `Arguments.kt` — reified-type argument resolution (`argument<String>("name")` → correct Brigadier type)
- `ArgumentTypeUtils.kt` — maps Kotlin types to Brigadier `ArgumentType` instances
- `Execution.kt` — `runs { }` infix that provides a `CommandContext` wrapper instead of NMS types
- `CommandContext.kt` — unwraps `CommandSourceStack` into player, world, position, etc.
- `Requires.kt` — `requiresPermission()` permission guards
- `Suggestions.kt` — `suggestSingle()`, `suggestList()` tab-completion helpers
- `BrigadierSupport.kt` — manages the registration queue and syncs command trees on player join; annotated `@NMS`

### Event DSL (`Listeners.kt`)
`listen<EventType> { }` — creates and registers an anonymous listener in one call. `SimpleListener<T>` is an abstract class for structured listeners. Both support `EventPriority` and `ignoreCancelled`.

### Chat components (`ComponentBuilder.kt`)
Fluent builder around Kyori Adventure `Component`. The `component { }` top-level function is the entry point; chained calls set style (bold, italic, color) and append siblings.

### Utilities and annotations
- `ServerExtensions.kt` / `PlayerExtensions.kt` — convenience properties (`server`, `onlinePlayers`, `broadcast()`)
- `NMS.kt` — `@NMS` opt-in annotation marking APIs that may break across Minecraft versions; require `@OptIn(NMS::class)` at call sites

## Key Configuration

- **Minecraft version** is pinned in `gradle.properties` (`minecraftVersion=26.1.2`). Bump it there when upgrading.
- **Java toolchain target:** 21.
- All dependencies are declared via the version catalog at `gradle/libs.versions.toml`.
- Publishing credentials come from environment variables `REPSY_USER` / `REPSY_PASSWORD` — never hardcode them.

## `@NMS` Annotation

Code touching NMS (Net Minecraft Server) internals — currently `BrigadierSupport.kt` — is annotated with `@NMS`. Any new code that calls into the Paper NMS layer must also be annotated and callers must opt in explicitly. This is the library's boundary for version-fragile code.