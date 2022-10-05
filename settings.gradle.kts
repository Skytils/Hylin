pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "sonatype"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
        maven { url = uri("https://maven.minecraftforge.net/") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Hylin"
include("lib")