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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import skytils.hylin.extension.*
import skytils.hylin.guild.Guild
import skytils.hylin.mojang.AshconException
import skytils.hylin.mojang.AshconPlayer
import skytils.hylin.player.OnlineStatus
import skytils.hylin.player.Player
import skytils.hylin.request.AsyncRequest
import skytils.hylin.request.ConnectionHandler
import skytils.hylin.skyblock.Member
import skytils.hylin.skyblock.Profile
import skytils.hylin.skyblock.bazaar.BazaarData
import java.util.*


/**
 * Main class for all Hypixel API functions
 *
 * @param key A Hypixel API key
 */
class HylinAPI private constructor(var key: String, private val cacheNames: Boolean = true, val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {

    companion object {

        /**
         * Extension function for a CoroutineScope to create a new Hylin API inside of it
         * @param key A Hypixel API key
         * @param cacheNames Whether or not to cache username to UUID conversions
         * @return A HypixelAPI instance
         */
        fun CoroutineScope.createHylinAPI(key: String, cacheNames: Boolean = true): HylinAPI =
            HylinAPI(key, cacheNames, this)

        /**
         * Function for creating a new Hylin API
         * @param key A Hypixel API key
         * @param cacheNames Whether or not to cache username to UUID conversions
         * @return A HypixelAPI instance
         */
        fun createHylinAPI(key: String, cacheNames: Boolean = true): HylinAPI = HylinAPI(key, cacheNames)

        const val endpoint = "https://api.hypixel.net"
    }

    internal val connectionHandler = ConnectionHandler()
    internal val namesToUUIDs = hashMapOf<String, UUID>()
    internal val UUIDsToNames = hashMapOf<UUID, String>()

    /**
     * Clears the username to UUID conversion cache
     */
    fun clearConversionCache() {
        namesToUUIDs.clear()
        UUIDsToNames.clear()
    }

    /**
     * Get a player object from Electroid's API
     *
     * @param identifiable The player's UUID, dashed or non-dashed, or username
     * @return The player's username
     */
    fun getMojangPlayer(identifiable: String) = AsyncRequest(scope) {
        getMojangPlayerSync(identifiable)
    }.launch()

    /**
     * Get a player object via Electroid's API synchronouslyy
     *
     * @param identifiable The player's UUID, dashed or non-dashed, or username
     * @return The player's username
     */
    fun getMojangPlayerSync(identifiable: String): AshconPlayer {
        val json = connectionHandler.readJSON("https://api.ashcon.app/mojang/v2/user/$identifiable")
        if (json.has("error")) {
            throw AshconException(json)
        }
        return AshconPlayer(json)
    }


    /**
     * Get a players username with their UUID via ELectroid's API synchronously
     *
     * @param uuid The player's UUID
     * @return The player's username
     */
    fun getName(uuid: UUID) = AsyncRequest(scope) {
        getNameSync(uuid)
    }.launch()

    /**
     * Get a players username with their UUID via Electroid's API synchronously
     *
     * @param uuid The player's UUID
     * @return The player's username
     */
    fun getNameSync(uuid: UUID, bypassCache: Boolean = false): String {
        // Check if uuid is already cached
        if (!bypassCache && uuid in UUIDsToNames) {
            return UUIDsToNames[uuid]!!
        } else {
            val name = getMojangPlayerSync(uuid.toString()).username
            if (cacheNames) {
                // Cache to both hashmaps
                namesToUUIDs[name] = uuid
                UUIDsToNames[uuid] = name
            }
            return name
        }
    }

    /**
     * Get a players UUID with their username via Electroid's API
     *
     * @param name The player's username
     * @return The player's UUID
     */
    fun getUUID(name: String, bypassCache: Boolean = false) = AsyncRequest(scope) {
        getUUIDSync(name, bypassCache)
    }.launch()

    /**
     * Get a players UUID with their username via Electroid's API synchronously
     *
     * @param name The player's username
     * @return The player's UUID
     */
    fun getUUIDSync(name: String, bypassCache: Boolean = false): UUID {
        // Check if name is already cached
        if (!bypassCache && name in namesToUUIDs) {
            return namesToUUIDs[name]!!
        } else {
            val uuid = getMojangPlayerSync(name).uuid
            if (cacheNames) {
                // Cache to both hashmaps
                namesToUUIDs[name] = uuid
                UUIDsToNames[uuid] = name
            }
            return uuid
        }
    }

    /**
     * Get a player with their UUID
     *
     * @param uuid The UUID of a player
     * @return A player object representing the player
     */
    fun getPlayer(uuid: UUID): AsyncRequest<Player> = AsyncRequest(scope) {
        getPlayerSync(uuid)
    }.launch()

    /**
     * Get a player with their username
     *
     * @param name The username of a player
     * @return A player object representing the player
     */
    fun getPlayer(name: String): AsyncRequest<Player> =
        AsyncRequest(scope) { getPlayerSync(getUUIDSync(name)) }.launch()

    /**
     * Get a player with their username synchronously
     *
     * @param name The username of a player
     * @return A player object representing the player
     */
    fun getPlayerSync(name: String) = getPlayerSync(getUUIDSync(name))

    /**
     * Get a player with their UUID synchronously
     *
     * @param uuid The UUID of a player
     * @return A player object representing the player
     */
    fun getPlayerSync(uuid: UUID) =
        Player(connectionHandler.hypixelJSON("$endpoint/player?key=$key&uuid=$uuid"))


    /**
     * Get a player with either a username or a UUID
     *
     * @param name A username or UUID in String form of a player
     */
    fun get(name: String) = if (name.length <= 16) getPlayer(name) else getPlayer(name.toUUID())

    /**
     * Get a player with either a username or a UUID synchronously
     *
     * @param name A username or UUID in String form of a player
     */
    fun getSync(name: String) = if (name.length <= 16) getPlayerSync(name) else getPlayerSync(name.toUUID())

    /**
     * Get a player's skyblock profiles with their UUID
     *
     * @param uuid The UUID of a player
     * @return A list of the player's skyblock profiles
     */
    fun getSkyblockProfiles(uuid: UUID) = AsyncRequest(scope) {
        getSkyblockProfilesSync(uuid)
    }.launch()

    /**
     * Get a player's skyblock profiles with their name
     *
     * @param name The username of a player
     * @return A list of the player's skyblock profiles
     */
    fun getSkyblockProfiles(name: String) = AsyncRequest(scope) {
        getSkyblockProfilesSync(getUUIDSync(name))
    }.launch()

    /**
     * Get a player's skyblock profiles with their UUID synchronously
     *
     * @param uuid The UUID of a player
     * @return A list of the player's skyblock profiles
     */
    fun getSkyblockProfilesSync(uuid: UUID): List<Profile> {
        val profiles =
            connectionHandler.hypixelJSON("$endpoint/skyblock/profiles?key=$key&uuid=$uuid")
                .getArray("profiles")
        return profiles.map { Profile(it.asJsonObject) }
    }

    /**
     * Get a player's skyblock profiles with their name synchronously
     *
     * @param name The username of a player
     * @return A list of the player's skyblock profiles
     */
    fun getSkyblockProfilesSync(name: String): List<Profile> = getSkyblockProfilesSync(getUUIDSync(name))

    /**
     * Gets a player's latest played skyblock profile with their UUID
     *
     * @param uuid the UUID of a player
     * @return The latest played skyblock profile
     */
    fun getLatestSkyblockProfile(uuid: UUID) = AsyncRequest(scope) {
        getLatestSkyblockProfileSync(uuid)
    }.launch()

    /**
     * Gets a player's latest played skyblock profile with their name
     *
     * @param name the username of a player
     * @return The latest played skyblock profile
     */
    fun getLatestSkyblockProfile(name: String) = AsyncRequest(scope) {
        getLatestSkyblockProfileSync(getUUIDSync(name))
    }.launch()

    /**
     * Gets a player's latest played skyblock profile with their UUID synchronously
     *
     * @param uuid the UUID of a player
     * @return The latest played skyblock profile
     */
    fun getLatestSkyblockProfileSync(uuid: UUID): Profile? {
        return getSkyblockProfilesSync(uuid).firstOrNull {
            it.selected
        }
    }

    /**
     * Gets a player's latest played skyblock profile with their name
     *
     * @param uuid the username of a player
     * @return The latest played skyblock profile
     */
    fun getLatestSkyblockProfileSync(name: String): Profile? = getLatestSkyblockProfileSync(getUUIDSync(name))

    /**
     * Gets the member object from a player's latest played skyblock profile
     * with their UUID
     *
     * @param uuid the UUID of a player
     * @return Their member object from their latest played skyblock profile
     */
    fun getLatestSkyblockProfileForMember(uuid: UUID) = AsyncRequest(scope) {
        getLatestSkyblockProfileForMemberSync(uuid)
    }.launch()

    /**
     * Gets the member object from a player's latest played skyblock profile
     * with their username
     *
     * @param name the username of a player
     * @return Their member object from their latest played skyblock profile
     */
    fun getLatestSkyblockProfileForMember(name: String) = AsyncRequest(scope) {
        getLatestSkyblockProfileForMemberSync(getUUIDSync(name))
    }.launch()

    /**
     * Gets the member object from a player's latest played skyblock profile
     * with their UUID synchronously
     *
     * @param uuid the UUID of a player
     * @return Their member object from their latest played skyblock profile
     */
    fun getLatestSkyblockProfileForMemberSync(uuid: UUID): Member? {
        return getLatestSkyblockProfileSync(uuid)?.members?.get(uuid.nonDashedString())
    }

    /**
     * Gets the member object from a player's latest played skyblock profile
     * with their username synchronously
     *
     * @param name the username of a player
     * @return Their member object from their latest played skyblock profile
     */
    fun getLatestSkyblockProfileForMemberSync(name: String) = getLatestSkyblockProfileForMemberSync(getUUIDSync(name))

    /**
     * Gets the bazaar object from Skyblock
     * This endpoint does not require an API key
     *
     * @return The bazaar data
     */
    fun getBazaarData() = AsyncRequest(scope) {
        getBazaarDataSync()
    }.launch()

    /**
     * Gets the bazaar object from Skyblock synchronously
     * This endpoint does not require an API key
     *
     * @return The bazaar data
     */
    fun getBazaarDataSync(): BazaarData {
        return BazaarData(connectionHandler.hypixelJSON("$endpoint/skyblock/bazaar"))
    }

    /**
     * Get the current online status of a player
     * @param uuid The uuid of a player
     * @return The online status of a player
     */
    fun status(uuid: UUID) = AsyncRequest(scope) {
        statusSync(uuid)
    }

    /**
     * Get the current online status of a player synchronously
     * @param uuid The uuid of a player
     * @return The online status of a player
     */
    fun statusSync(uuid: UUID): OnlineStatus {
        val json =
            connectionHandler.hypixelJSON("$endpoint/status?key=$key&uuid=$uuid")
        return OnlineStatus(json)
    }

    /**
     * Get the current online status of a player synchronously
     * @param name The username of a player
     * @return The online status of a player
     */
    fun status(name: String) = AsyncRequest(scope) {
        statusSync(name)
    }

    /**
     * Get the current online status of a player synchronously
     * @param name The username of a player
     * @return The online status of a player
     */
    fun statusSync(name: String) = statusSync(getUUIDSync(name))

    /**
     * Get the guild of a player
     * @param uuid UUID of a player
     * @return The guild of the player
     */
    fun guild(uuid: UUID) = AsyncRequest(scope) {
        guildSync(uuid)
    }

    /**
     * Get the guild of a player
     * @param name Username of a player
     * @return The guild of the player
     */
    fun guild(name: String) = guild(getUUIDSync(name))

    /**
     * Get the guild of a player synchronously
     * @param uuid UUID of a player
     * @return The guild of the player
     */
    fun guildSync(uuid: UUID): Guild? {
        val json =
            connectionHandler.hypixelJSON("$endpoint/guild?key=$key&player=$uuid")
        if (json.get("guild").isJsonNull) return null
        return Guild(json.getJsonObject("guild"))
    }

    /**
     * Get the guild of a player synchronously
     * @param name Username of a player
     * @return The guild of the player
     */
    fun guildSync(name: String) = guildSync(getUUIDSync(name))

    /**
     *  Gets the friends of a player
     *  @param uuid The UUID of a player
     *  @return A list of friends of a player
     */
    fun friends(uuid: UUID) = AsyncRequest(scope) {
        friendsSync(uuid)
    }.launch()

    /**
     *  Gets the friends of a player
     *  @param name The username of a player
     *  @return A list of friends of a player
     */
    fun friends(name: String) = friends(getUUIDSync(name))

    /**
     *  Gets the friends of a player synchronously
     *  @param uuid The UUID of a player
     *  @return A list of friends of a player
     */
    fun friendsSync(uuid: UUID): List<UUID> {
        // Read the contents of the endpoint as JSON
        val json =
            connectionHandler.hypixelJSON("$endpoint/friends?key=$key&uuid=$uuid")

        return json["records"].asJsonArray.map { record ->
            val uuidSender = record.asJsonObject.getString("uuidSender")
            val uuidReceiver = record.asJsonObject.getString("uuidReceiver")
            if (uuidSender == uuid.toString()) uuidReceiver.toUUID() else uuidSender.toUUID()
        }
    }

    /**
     *  Gets the friends of a player synchronously
     *  @param name The username of a player
     *  @return A list of friends of a player
     */
    fun friendsSync(name: String) = friendsSync(getUUIDSync(name))
}
