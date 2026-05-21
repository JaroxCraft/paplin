plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    `java-library`
    `maven-publish`

    alias(libs.plugins.paperweight.userdev)

    alias(libs.plugins.ktlint)
}

val paplinVersion: String by project
val minecraftVersion = libs.versions.minecraft.get()

group = "de.jarox"
version = "$paplinVersion+$minecraftVersion"

val ghRepoUrl = "https://github.com/jaroxcraft/paplin"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("$minecraftVersion.build.+")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    withSourcesJar()
}

publishing {

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/JaroxCraft/paplin")

            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>(project.name) {
            from(components["java"])

            pom {
                this.url.set(ghRepoUrl)

                developers {
                    developer {
                        id.set("jaroxcraft")
                        name.set("JaroxCraft")
                        email.set("jarox@jarox.de")
                    }
                }
                licenses {
                    license {
                        name = "MIT License"
                        url = "$ghRepoUrl/blob/master/LICENSE"
                    }
                }
                issueManagement {
                    system = "GitHub"
                    url = ghRepoUrl
                }
                scm {
                    url = ghRepoUrl
                    connection = "scm:git:$ghRepoUrl.git"
                    developerConnection = "scm:git:$ghRepoUrl.git"
                }
                this.organization {
                    this.name = "Paplin"
                }
            }

            this.groupId = project.group.toString()
            this.artifactId = project.name.lowercase()
            this.version = project.version.toString()
        }
    }
}
