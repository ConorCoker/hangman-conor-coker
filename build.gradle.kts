plugins {
    kotlin("jvm") version "1.8.0"
    // plugin for dokka
    id("org.jetbrains.dokka") version "1.8.10"
    // plugin for jacoco
    id("jacoco")
    // Plugin for Ktlint
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"

    application
}

group = "me.conor"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // for generating a dokka site from KDoc
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.8.10")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "MainKt"
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree { it } }
    })
}
