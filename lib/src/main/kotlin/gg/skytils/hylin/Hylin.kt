package gg.skytils.hylin

import gg.skytils.hylin.http.HTTPClient
import gg.skytils.hylin.http.HTTPClientImpl
import gg.skytils.hylin.mojang.AshconError
import gg.skytils.hylin.mojang.AshconPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.util.UUID
import kotlin.coroutines.CoroutineContext

class Hylin private constructor(
    var key: String,
    internal val httpClient: HTTPClient = HTTPClientImpl(),
    val cacheNames: Boolean = true,
    override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
): CoroutineScope {
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val nameToUUID: MutableMap<String, UUID> = hashMapOf()
    private val uuidToName: MutableMap<UUID, String> = hashMapOf()

    fun String.getUUID(): UUID =
        getUUIDSync(this)

    fun getUUID(username: String) = async {
        getUUIDSync(username)
    }

    fun getUUIDSync(username: String): UUID {
        if (cacheNames) {
            nameToUUID[username]?.let { return it }
        }
        val uuid = getAshconPlayerSync(username).uuid
        if (cacheNames) {
            nameToUUID[username] = uuid
            uuidToName[uuid] = username
        }
        return uuid
    }

    fun getAshconPlayer(username: String) = async {
        getAshconPlayerSync(username)
    }

    fun getAshconPlayerSync(username: String): AshconPlayer {
        val response = httpClient.getInputStream("https://api.ashcon.app/mojang/v2/user/$username").bufferedReader()
            .use(BufferedReader::readText)
        val ashconError = json.decodeFromString<AshconError>(response)
        if (ashconError.error != null) error("Error getting data for $username")
        return json.decodeFromString(response)
    }
}