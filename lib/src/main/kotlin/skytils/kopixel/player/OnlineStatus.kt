package skytils.kopixel.player

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byExternal
import skytils.kopixel.extension.converter.byUUID

class OnlineStatus(json: JsonObject) {
    val uuid by json.byUUID()
    val session by json.byExternal<PlayerSession>()

    override fun toString(): String {
        return "OnlineStatus(uuid=$uuid, session=$session)"
    }
}
