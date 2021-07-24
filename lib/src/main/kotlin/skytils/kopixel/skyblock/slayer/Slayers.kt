package skytils.kopixel.skyblock.slayer

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byExternal

class Slayers(json: JsonObject) {
    val revenant by json.byExternal<RevenantSlayer>("zombie")
    val tarantula by json.byExternal<StandardSlayer>("spider")
    val enderman by json.byExternal<StandardSlayer>("enderman")
    val sven by json.byExternal<StandardSlayer>("wolf")
}
