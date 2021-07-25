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
import skytils.hylin.extension.getInt
import skytils.hylin.extension.getLong
import skytils.hylin.extension.getString
import skytils.hylin.extension.toUUID
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
