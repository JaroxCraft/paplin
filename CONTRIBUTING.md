# Contributing to Paplin

## Prerequisites

- Java 25+ (the Gradle toolchain downloads it automatically if missing)
- Git

## First-time setup

```bash
git clone https://github.com/JaroxCraft/paplin
cd paplin
./gradlew build       # sanity check — should pass
```

Install the ktlint pre-commit hook so formatting is caught before CI:

```bash
./gradlew addKtlintCheckGitPreCommitHook
```

This adds a hook that runs `ktlintCheck` on staged `.kt` files before every commit. If it fails, run `./gradlew ktlintFormat`, stage the changes, and commit again.

## Day-to-day development

```bash
./gradlew build               # compile + check
./gradlew ktlintFormat        # auto-format (run before committing)
./gradlew ktlintCheck         # check-only (what CI and the hook run)
```

Branch names: `feat/your-feature`, `fix/issue-description`, `chore/what-changed`.

## Testing downstream in the example project

There is no in-library test suite. To test DSL changes against a real plugin:

1. Publish to local Maven:
   ```bash
   ./gradlew publishToMavenLocal
   # publishes de.jarox:paplin:{paplinVersion}+{minecraftVersion} to ~/.m2
   ```

2. In the example project, temporarily add `mavenLocal()` to the `repositories` block in `build.gradle.kts` — before `maven { url = ... }` so it takes priority.

3. Build and run:
   ```bash
   ./gradlew build
   ./gradlew runServer
   ```

4. Remove `mavenLocal()` before committing the example project.

## How versioning works

Published artifact: `de.jarox:paplin:{paplinVersion}+{minecraftVersion}`

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

| Situation | `paplinVersion` | `minecraftVersion` |
|---|---|---|
| MC update, no API change | unchanged | bump in `libs.versions.toml` |
| New DSL feature | MINOR bump | maybe |
| Breaking API change | MAJOR bump | maybe |
| Bug fix only | PATCH bump | unchanged |
| Tooling/CI only | unchanged | unchanged — no release |

## Release process

> Releases are tag-driven. The CI release workflow triggers on `v*` tags and handles publishing, verification, and GitHub Release creation automatically.

**Step-by-step:**

1. Merge all intended changes to `master`. CI must be green.

2. Decide what changed (see table above) and bump the relevant version(s):
   ```properties
   # gradle.properties
   paplinVersion=1.1.0
   ```
   ```toml
   # gradle/libs.versions.toml
   [versions]
   minecraft = "26.1.2"
   ```

3. Commit directly to `master`:
   ```bash
   git add gradle.properties gradle/libs.versions.toml
   git commit -m "chore: release 1.1.0+26.1.2"
   git push origin master
   ```

4. Tag the commit. The tag must exactly match the versions in the project files:
   ```bash
   git tag v1.1.0+26.1.2
   git push origin v1.1.0+26.1.2
   ```
   The release workflow validates this before doing anything. If tag ≠ project files, the workflow fails immediately with a clear error — nothing is published.

5. Watch the Actions tab. The workflow:
   - Validates tag vs `gradle.properties` and `gradle/libs.versions.toml`
   - Runs `./gradlew check`
   - Publishes to Repsy
   - Polls until the artifact appears (up to 60 seconds)
   - Creates the GitHub Release with auto-generated notes

6. Update the example project: bump `paplin` and (if applicable) `minecraft` in `gradle/libs.versions.toml`.

## Bumping the Minecraft version

1. Update `minecraft` in `gradle/libs.versions.toml`
2. Update `paperweight-userdev` in `gradle/libs.versions.toml` if a new plugin version is available
3. Fix any compilation errors from API changes
4. Run `./gradlew ktlintFormat && ./gradlew check`
5. Follow the normal release process above

## `@NMS` policy

Any code that calls Paper NMS internals must be annotated `@OptIn(NMS::class)` at the call site. Note this in the PR description — NMS usage is fragile across MC versions and needs explicit visibility.

## If a release goes wrong

If the workflow publishes a broken artifact:

1. Yank the release on GitHub (mark it as a pre-release or delete it)
2. Contact Repsy support if the artifact needs removal from the registry
3. Fix the issue on `master`
4. Delete the bad tag locally and remotely:
   ```bash
   git tag -d v1.1.0+26.1.2
   git push origin :refs/tags/v1.1.0+26.1.2
   ```
5. Re-tag after the fix is pushed
