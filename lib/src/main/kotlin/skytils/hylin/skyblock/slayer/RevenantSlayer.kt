package skytils.hylin.skyblock.slayer

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byExternal
import skytils.hylin.extension.converter.byInt

class RevenantSlayer(json: JsonObject) : StandardSlayer(json) {
    override val claimedLevels by json.byExternal<RevenantClaimedSlayerLevels>("claimed_levels")
    val t4Kills: Int? by json.byInt("boss_kills_tier_4")
}
