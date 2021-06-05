package com.ryanhcode.kopixel.skyblock.communityupgrades

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.getInt
import com.ryanhcode.kopixel.extension.getLong
import com.ryanhcode.kopixel.extension.getString
import com.ryanhcode.kopixel.extension.toUUID
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
