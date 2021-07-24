package skytils.kopixel.skyblock.slayer

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byBoolean

open class StandardClaimedSlayerLevels(json: JsonObject) {
    val level1: Boolean? by json.byBoolean("level_1")
    val level2: Boolean? by json.byBoolean("level_2")
    val level3: Boolean? by json.byBoolean("level_3")
    val level4: Boolean? by json.byBoolean("level_4")
    val level5: Boolean? by json.byBoolean("level_5")
    val level6: Boolean? by json.byBoolean("level_6")
    val level7: Boolean? by json.byBoolean("level_7")
    val level8: Boolean? by json.byBoolean("level_8")
    val level9: Boolean? by json.byBoolean("level_9")
}
