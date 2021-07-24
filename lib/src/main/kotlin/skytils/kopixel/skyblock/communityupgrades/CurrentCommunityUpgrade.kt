package skytils.kopixel.skyblock.communityupgrades

import com.google.gson.JsonObject
import skytils.kopixel.extension.getInt
import skytils.kopixel.extension.getLong
import skytils.kopixel.extension.getString
import skytils.kopixel.extension.toUUID
import java.util.*

/**
 * Represents a Hypixel Skyblock Profile's currently processing community upgrade
 * @param json A JsonObject to construct data from
 */
class CurrentCommunityUpgrade(json: JsonObject) {
    val upgrade = json.getString("upgrade")
    val newTier = json.getInt("tier")
    val start = Date(json.getLong("start_ms"))
    val initiator = json.getString("who_started").toUUID()

    override fun toString(): String {
        return "CurrentCommunityUpgrade(upgrade='$upgrade', newTier=$newTier, start=$start, initiator=$initiator)"
    }
}
