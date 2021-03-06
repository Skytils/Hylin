/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2021  Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package skytils.hylin

import kotlinx.coroutines.runBlocking
import skytils.hylin.HylinAPI.Companion.createHylinAPI
import skytils.hylin.extension.toUUID
import java.io.File
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class HylinAPITest {

    val testKey = System.getenv("HYPIXEL_KEY")

    @Test
    fun getName() = runBlocking {
        val api = createHylinAPI(testKey)
        assertEquals("skyf", api.getNameSync("7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID()))
    }

    @Test
    fun getUUID() = runBlocking {
        val api = createHylinAPI(testKey)
        assertEquals("7c86a219-cdab-49a9-bd44-e34279bd590b".toUUID(), api.getUUIDSync("skyf"))
    }

    @Test
    fun testHOTM() = runBlocking {
        println(testKey)
        val api = createHylinAPI(testKey)

        val member = api.getLatestSkyblockProfileForMemberSync("ujc")!!
        println(member.hotm)
    }

    @Test
    fun testJacob() = runBlocking {
        val api = createHylinAPI(testKey)

        val member = api.getLatestSkyblockProfileForMemberSync("ujc")!!
        println(member.jacob)
    }

    @Test
    fun getSkyblockProfilesTime() = runBlocking {
        val api = createHylinAPI(testKey)
        val prevTime = System.currentTimeMillis()
        api.getSkyblockProfilesSync("skyf")
        println("time ${System.currentTimeMillis() - prevTime}")
    }

    @Test
    fun scanTest(): Unit = runBlocking {

        // Make a new Hylin API in this scope
        val api = createHylinAPI(testKey)

        fun scan(uuid: UUID) {
            // Grab the profiles of the player asynchronously
            api.getSkyblockProfiles(uuid).whenComplete { profiles ->
                profiles.forEach { profile ->
                    profile.scan { uuid, member, inv, item ->
                        if (item.colorable && item.isExotic)
                            println("Exotic Found: \n\t${item.id} \n\t${item.hexColor} \n\t$uuid \n\t${inv.name}")
                    }
                }
            }
        }
        scan("cab60d114bd84d5fbcc46383ee8f6ed1".toUUID())
    }

    @Test
    fun status() {
        val api = createHylinAPI(testKey)

        println(api.statusSync("skyf"))
    }

    @Test
    fun guildTest() {
        val api = createHylinAPI(testKey)
        assertEquals(null, api.guildSync("skyf"))
    }

    @Test
    fun dungeonsTest() {
        val api = createHylinAPI(testKey)

        println("Writing to file")
        File("test.txt").writeText(api.getLatestSkyblockProfileForMemberSync("skyf")?.dungeons.toString())
        println("finished writing")
    }

    @Test
    fun bazaarTest() {
        val api = createHylinAPI(testKey)

        println("Writing to file")
        File("test.txt").writeText(api.getBazaarDataSync().toString())
        println("finished writing")
    }
}