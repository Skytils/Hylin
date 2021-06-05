package com.ryanhcode.kopixel.skyblock.communityupgrades

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.*
import java.util.*

/**
 * Represents one of a Hypixel Skyblock Profile's community upgrade
 * @param json A JsonObject to construct data from
 */
class CommunityUpgradeState(json: JsonObject) {
    val upgrade = json.getString("upgrade")
    val tier = json.getInt("tier")
    val started = Date(json.getLong("started_ms"))
    val startedBy = json.getString("started_by").toUUID()
    val claimed = Date(json.getLong("claimed_ms"))
    val claimedBy = json.getString("claimed_by").toUUID()
    val fastTracked = json.getBoolean("fasttracked")

    override fun toString(): String {
        return "CommunityUpgradeState(upgrade='$upgrade', tier=$tier, started=$started, startedBy=$startedBy, claimed=$claimed, claimedBy=$claimedBy, fastTracked=$fastTracked)"
    }
}
