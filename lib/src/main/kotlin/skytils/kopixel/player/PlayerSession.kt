package skytils.kopixel.player

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byBoolean
import skytils.kopixel.extension.converter.byString

class PlayerSession(json: JsonObject) {
    val online by json.byBoolean()
    val gameType: String? by json.byString()
    val mode: String? by json.byString()
    val map: String? by json.byString()

    override fun toString(): String {
        return "PlayerSession(online=$online, gameType=$gameType, mode=$mode, map=$map)"
    }
}
