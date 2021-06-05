package com.ryanhcode.kopixel.skyblock.communityupgrades

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.getArray
import com.ryanhcode.kopixel.extension.getJsonObject

/**
 * Represents a Hypixel Skyblock Profile's state regarding community upgrades
 * @param json A JsonObject to construct data from
 */
class CommunityUpgrades(json: JsonObject) {
    val currentCommunityUpgrade: CurrentCommunityUpgrade? =
        if(json.has("currently_upgrading") && !json.get("currently_upgrading").isJsonNull)
            CurrentCommunityUpgrade(json.getJsonObject("currently_upgrading"))
        else
            null

    val upgrades = json.getArray("upgrade_states").map { CommunityUpgradeState(it.asJsonObject) }
    override fun toString(): String {
        return "CommunityUpgrades(\n" +
                "\tcurrentCommunityUpgrade=$currentCommunityUpgrade, \n" +
                "\tupgrades=$upgrades\n" +
                ")"
    }


}