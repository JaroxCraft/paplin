plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    `java-library`
    `maven-publish`

    alias(libs.plugins.paperweight.userdev)

    alias(libs.plugins.ktlint)
}

val minecraftVersion: String by project

group = "de.jarox"
version = "1.0.0-$minecraftVersion"

val ghRepoUrl = "https://github.com/jaroxcraft/paplin"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("${project.property("minecraftVersion")}-R0.1-SNAPSHOT")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    withSourcesJar()
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
