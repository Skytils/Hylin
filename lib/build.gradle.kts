plugins {
    java
    id("maven-publish")
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.Skytils"
            artifactId = "Hylin"
            version = "LOCAL"

            from(components["java"])
        }
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io/") }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
        }
    }
}