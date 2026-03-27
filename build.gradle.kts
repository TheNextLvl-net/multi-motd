plugins {
    id("java")
    id("com.gradleup.shadow") version "9.4.1"
}

group = "net.thenextlvl.motd"
version = "1.0.1"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks.compileJava {
    options.release.set(21)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.thenextlvl.net/releases")
    maven("https://repo.thenextlvl.net/snapshots")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")

    implementation("net.thenextlvl.core:files:4.0.0-pre1")
}

tasks.shadowJar {
    minimize()
}