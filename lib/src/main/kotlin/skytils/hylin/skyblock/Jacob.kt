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

package skytils.hylin.skyblock

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.getInt
import skytils.hylin.extension.getJsonObject

class Jacob(json:JsonObject) {
    val medals: Medals by json.byExternal<Medals>("medals_inv")
    val perks: Perks by json.byExternal<Perks>()
    val contests: List<Contest> by lazy {
        if (json["contest"] == null) return@lazy listOf()
        json.getJsonObject("contests").entrySet().map {
            val dateAndItem = it.key.split(':').also { arr ->
                (arr as MutableList).add(2, arr[1].substringAfter('_'))
                arr[1] = arr[1].substringBefore('_')
            }
            val value = it.value.asJsonObject
            Contest(
                SkyblockDate(
                    dateAndItem[2].toInt(),
                    dateAndItem[1].toInt(),
                    dateAndItem[0].toInt(),
                ),
                dateAndItem[3],
                value.getInt("collected"),
                value["claimed_reward"]?.asBoolean ?: false,
                value["claimed_position"]?.asInt,
                value["claimed_participants"]?.asInt
            )
        }
    }
    val talked: Boolean by json.byBoolean()
    val uniqueGolds: List<String> by json.byList<String>("unique_golds2")

    override fun toString(): String {
        return "Medals: G-${medals.gold}, S-${medals.silver}, B-${medals.bronze}\n" +
                "Perks: Drops-${perks.doubleDrops}, Level-${perks.farmingLevelCap}\n" +
                "Contests: ${contests.size}\n" +
                "Talked: ${talked}\n" +
                "Uniques: ${uniqueGolds.joinToString(", ")}"
    }

    class Medals(json: JsonObject) {
        val gold: Int by json.byInt()
        val silver: Int by json.byInt()
        val bronze: Int by json.byInt()
    }
    class Perks(json: JsonObject) {
        val doubleDrops: Int? by json.byInt("double_drops")
        val farmingLevelCap: Int? by json.byInt("farming_level_cap")
    }
    data class Contest(
        val date: SkyblockDate,
        val type: String,
        val collected: Int,
        val claimed: Boolean,
        val position: Int?,
        val participants: Int?
    )
    data class SkyblockDate(val day: Int, val month: Int, val year: Int)
}
