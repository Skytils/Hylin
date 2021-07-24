package skytils.kopixel.skyblock.slayer

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byExternal
import skytils.kopixel.extension.converter.byInt

open class StandardSlayer(json: JsonObject) {
    val xp by json.byInt()
    open val claimedLevels by json.byExternal<StandardClaimedSlayerLevels>("claimed_levels")
    val t0Kills: Int? by json.byInt("boss_kills_tier_0")
    val t1Kills: Int? by json.byInt("boss_kills_tier_1")
    val t2Kills: Int? by json.byInt("boss_kills_tier_2")
    val t3Kills: Int? by json.byInt("boss_kills_tier_3")
}
