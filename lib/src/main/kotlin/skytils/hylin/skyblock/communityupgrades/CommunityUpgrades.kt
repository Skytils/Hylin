package skytils.hylin.skyblock.communityupgrades

import com.google.gson.JsonObject
import skytils.hylin.extension.getArray
import skytils.hylin.extension.getJsonObject

/**
 * Represents a Hypixel Skyblock Profile's state regarding community upgrades
 * @param json A JsonObject to construct data from
 */
class CommunityUpgrades(json: JsonObject) {
    val currentCommunityUpgrade: CurrentCommunityUpgrade? =
        if (json.has("currently_upgrading") && !json.get("currently_upgrading").isJsonNull)
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