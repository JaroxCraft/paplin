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

tasks.register<Jar>("dokkaHtmlJar") {
    group = "documentation"
    description = "Assembles a JAR archive containing the Dokka HTML documentation."
    dependsOn(tasks.dokkaGenerateHtml)
    from(tasks.dokkaGenerateHtml.map { it.outputs.files })
    archiveClassifier.set("javadoc")
}

publishing {

    repositories {
        maven {
            name = "repsy"
            url = uri("https://repo.repsy.io/mvn/jaroxcraft/paplin")

            val credentials = System.getenv("REPSY_USER") to System.getenv("REPSY_PASSWORD")

            credentials {
                username = credentials.first
                password = credentials.second
            }
        }
    }

    publications {
        register<MavenPublication>(project.name) {
            from(components["java"])
            artifact(tasks["dokkaHtmlJar"])

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
