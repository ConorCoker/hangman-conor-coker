plugins {
    kotlin("jvm") version "1.8.0"
    //plugin for dokka
    id("org.jetbrains.dokka") version "1.8.10"
    application
}

group = "me.conor"
version = "1.0-SNAPSHOT"

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
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}