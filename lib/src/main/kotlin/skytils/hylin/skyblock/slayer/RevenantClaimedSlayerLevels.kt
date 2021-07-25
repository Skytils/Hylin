package skytils.hylin.skyblock.slayer

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byBoolean

class RevenantClaimedSlayerLevels(json: JsonObject) : StandardClaimedSlayerLevels(json) {
    val level7Special: Boolean? by json.byBoolean("level_7_special")
    val level8Special: Boolean? by json.byBoolean("level_8_special")
    val level9Special: Boolean? by json.byBoolean("level_9_special")
}