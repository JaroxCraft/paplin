# AGENTS.md

## What this is

Kotlin library wrapping Paper Minecraft server API with idiomatic Kotlin DSLs. Published to GitHub Packages. No application entrypoint — consumed by downstream plugin projects. No test suite.

## Critical facts

- **Java target: 25** (not 21 — CLAUDE.md is stale)
- **Version format:** `$paplinVersion+$minecraftVersion` (uses `+`, SemVer build metadata). The `+` separator is intentional — Maven resolves it fine on Repsy.
- **Default branch is `master`**. CI workflows are configured for `master`.
- **GitHub Packages requires authentication for reads** — set `GITHUB_ACTOR` and `GITHUB_TOKEN` env vars.

## Commands

```bash
./gradlew check          # ktlintCheck + compile (what CI runs)
./gradlew ktlintFormat   # auto-format
./gradlew ktlintCheck    # check-only
./gradlew build          # compile only
./gradlew publish        # requires GITHUB_ACTOR + GITHUB_TOKEN env vars
```

## Releases

Tag-driven. Push a `v*` tag → `release.yml` validates tag matches versions in `gradle.properties` and `gradle/libs.versions.toml`, publishes, polls GitHub Packages for 60s, creates GitHub Release.

**Never hardcode credentials.** They come from `GITHUB_ACTOR` / `GITHUB_TOKEN` env vars.

**The `+` character is NOT valid in GitHub Actions tag filter patterns.** Use `v*` and validate format in the script.

## Versioning

Versions are split across two files:

```properties
# gradle.properties
paplinVersion=1.0.0
```

```toml
# gradle/libs.versions.toml
[versions]
minecraft = "26.1.2"
```

| Situation | paplinVersion | minecraftVersion |
|---|---|---|
| MC update, no API change | unchanged | bump in `libs.versions.toml` |
| New DSL feature | MINOR | maybe |
| Breaking API change | MAJOR | maybe |
| Bug fix | PATCH | unchanged |
| Tooling only | unchanged | unchanged (no release) |

Pre-release tags (e.g. `v1.0.0-beta.1+26.1.2`) create GitHub Releases marked as pre-release.

## Downstream testing

No in-library tests. To verify changes against a real plugin:

1. **Composite build** (fastest, no publish needed):  
   In the example project, run `./gradlew -Ppaplin.local.path=../paplin runServer`.

2. **Maven local**:  
   In Paplin, run `./gradlew publishToMavenLocal`.  
   In the example project, run `./gradlew -PuseMavenLocal runServer`.

## `@NMS` policy

Any code calling Paper NMS internals must be annotated `@OptIn(NMS::class)` at call sites. This marks version-fragile APIs.

## Architecture

Source: `src/main/kotlin/de/jarox/paplin/`

- **Plugin lifecycle:** `PaplinPlugin.kt` — abstract base extending `JavaPlugin`
- **Command DSL:** `command/` — Brigadier wrapper (`command { }`, `argument<T>()`, `runs { }`)
- **Event DSL:** `event/Listeners.kt` — `listen<EventType> { }`
- **Chat:** `chat/ComponentBuilder.kt` — Kyori Adventure fluent builder
- **Extensions:** `extension/` — `server`, `onlinePlayers`, `broadcast()`
- **NMS annotation:** `annotation/NMS.kt` — opt-in for version-fragile APIs

## Config

- Version catalog: `gradle/libs.versions.toml`
- Renovate bot manages dependency updates
- Gradle config cache + parallel + caching enabled
