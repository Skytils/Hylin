plugins {
    java
    kotlin("jvm") version "1.7.10"
    id("maven-publish")
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

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}