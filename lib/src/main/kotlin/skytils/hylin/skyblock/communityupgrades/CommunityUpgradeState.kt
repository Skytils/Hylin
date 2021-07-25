/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2021  Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package skytils.hylin.skyblock.communityupgrades

import com.google.gson.JsonObject
import skytils.hylin.extension.*
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
