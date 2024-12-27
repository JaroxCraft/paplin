plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    `java-library`
    `maven-publish`

    alias(libs.plugins.paperweight.userdev)

    alias(libs.plugins.ktlint)
}

group = "de.jarox"
version = "1.0.0"

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

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

publishing {

    repositories {
        maven {
            name = "repsy"
            url = uri("https://repo.repsy.io/mvn/jaroxcraft/paplin")

            val credentials = System.getenv("REPSY_USER") to System.getenv("REPSY_PASSWORD")
            println("REPSY_USER: ${credentials.first}")
            println("REPSY_PASSWORD: ${credentials.second}")

            credentials {
                username = credentials.first
                password = credentials.second
            }
        }
    }

    publications {
        register<MavenPublication>(project.name) {
            from(components["java"])

            artifact(tasks["dokkaJavadocJar"])

            this.groupId = project.group.toString()
            this.artifactId = project.name.lowercase()
            this.version = project.version.toString()
        }
    }
}
