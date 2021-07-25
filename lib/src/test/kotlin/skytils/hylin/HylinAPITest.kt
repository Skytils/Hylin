package skytils.hylin

import kotlinx.coroutines.runBlocking
import skytils.hylin.HylinAPI.Companion.NewHylinAPI
import skytils.hylin.extension.toUUID
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HylinAPITest {

    val testKey = "redacted"

    @Test
    fun getName() = runBlocking {
        val api = NewHylinAPI(testKey)
        assertEquals(api.getUUIDSync("skyf"), "7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID())
    }

    @Test
    fun getUUID() = runBlocking {
        val api = NewHylinAPI(testKey)
        assertEquals(api.getNameSync("7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID()), "skyf")
    }

    @Test
    fun getSkyblockProfilesTime() = runBlocking {
        val api = NewHylinAPI(testKey)
        val prevTime = System.currentTimeMillis()
        api.getSkyblockProfilesSync("skyf")
        println("time ${System.currentTimeMillis() - prevTime}")
    }

    @Test
    fun scanTest(): Unit = runBlocking {

        // Make a new Hylin API in this scope
        val api = NewHylinAPI(testKey)

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
        val api = NewHylinAPI(testKey)

        println(api.statusSync("skyf"))
    }

    @Test
    fun guildTest() {
        val api = NewHylinAPI(testKey)
        assertEquals(api.guildSync("skyf"), null)
    }
}