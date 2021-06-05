# KoPixel
[![](https://jitpack.io/v/ryanhcode/KoPixel.svg)](https://jitpack.io/#ryanhcode/KoPixel)

KoPixel is a simple &amp; flexible asynchronous Hypixel API wrapper for Kotlin

## Installation

Add the following to your build.gradle(.kts)

```groovy
dependencies {
    implementation 'com.github.ryanhcode:KoPixel:$kopixel_version'
}
```
Or visit Jitpack and [select a version](https://jitpack.io/#ryanhcode/KoPixel/latest) 

## Usage

In order to obtain a `KoPixelAPI` instance, use NewKoPixelAPI
If you plan on just using the library synchronously, you can use NewKoPixelAPI wherever you like.

```kotlin
val hypixelAPIKey = "..."
val api = NewKoPixelAPI(hypixelAPIKey)
...
```
However, if you are planning to use KoPixel asynchronously, the API must be created inside of a CoroutineScope, or inside of a block such as `runBlocking` which is recommended.
```kotlin
fun main() = runBlocking {
    val hypixelAPIKey = "..."
    val api = NewKoPixelAPI(hypixelAPIKey)
    ...
}
```

KoPixel has both asynchronous and synchronous methods for all functions.

Here you can see a method of synchronously obtaining if a player is online:
```kotlin
val online = api.getSync("skyf").online()
println("Online: $online")
```
And here is a asynchronous method of obtaining if a player is online:
```kotlin
api.get("skyf").whenComplete { player->
    val online = player.online()
    println("Online: $online")
}
```
If you choose to use KoPixel asynchronously, you may also use error handling for all requests.
```kotlin
api.get("skyf").whenComplete { player->
    val online = player.online()
    println("Online: $online")
}.catch { exception ->
    println("woah! there was an error: $exception")
}
```
Here you can see an example of utilizing KoPixel's asynchronous API to scan a player for Exotic Armor:
```kotlin
fun scan(uuid: UUID) {
  // Grab the profiles of the player asynchronously
  api.getSkyblockProfiles(uuid).whenComplete { profiles -> 
    profiles.forEach { profile ->
      profile.scan { uuid, member, inv, item ->
        if (item.colorable() && item.exotic())
          println("Exotic Found: \n\t${item.id()} \n\t${item.hexColor()} \n\t$uuid \n\t${inv.name}")
      }
    } 
  }.catch { exception -> println("Swallowing exception.") }
}
scan("cab60d114bd84d5fbcc46383ee8f6ed1".toUUID())
```
KoPixel currently supports a large portion of the skyblock API and regular player API, although resource, auction, bazaar, and dungeon implementations are planned for the near future.

## License
[MIT](https://choosealicense.com/licenses/mit/)
