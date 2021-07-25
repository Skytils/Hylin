package skytils.kopixel

import kotlinx.coroutines.runBlocking
import skytils.kopixel.KoPixelAPI.Companion.NewKoPixelAPI
import skytils.kopixel.extension.toUUID
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class KoPixelAPITest {

    val testKey = "redacted"

    @Test
    fun getName() = runBlocking {
        val api = NewKoPixelAPI(testKey)
        assertEquals(api.getUUIDSync("skyf"), "7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID())
    }

    @Test
    fun getUUID() = runBlocking {
        val api = NewKoPixelAPI(testKey)
        assertEquals(api.getNameSync("7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID()), "skyf")
    }

    @Test
    fun getSkyblockProfilesTime() = runBlocking {
        val api = NewKoPixelAPI(testKey)
        val prevTime = System.currentTimeMillis()
        api.getSkyblockProfilesSync("skyf")
        println("time ${System.currentTimeMillis() - prevTime}")
    }

    @Test
    fun scanTest(): Unit = runBlocking {

        // Make a new KoPixel API in this scope
        val api = NewKoPixelAPI(testKey)

        fun scan(uuid: UUID) {
            // Grab the profiles of the player asynchronously
            api.getSkyblockProfiles(uuid).whenComplete { profiles ->
                profiles.forEach { profile ->
                    profile.scan { uuid, member, inv, item ->
                        if (item.colorable() && item.exotic())
                            println("Exotic Found: \n\t${item.id()} \n\t${item.hexColor()} \n\t$uuid \n\t${inv.name}")
                    }
                }
            }
        }
        scan("cab60d114bd84d5fbcc46383ee8f6ed1".toUUID())
    }

    @Test
    fun status() {
        val api = NewKoPixelAPI(testKey)

        println(api.statusSync("skyf"))
    }

    @Test
    fun guildTest() {
        val api = NewKoPixelAPI(testKey)
        assertEquals(api.guildSync("skyf"), null)
    }
}