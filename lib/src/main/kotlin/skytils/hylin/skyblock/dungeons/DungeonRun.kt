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

package skytils.hylin.skyblock.dungeons

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.converter.byInt
import skytils.hylin.extension.converter.byList
import skytils.hylin.extension.converter.byLong
import skytils.hylin.extension.getInt
import skytils.hylin.extension.getJsonObject

class DungeonRun(json: JsonObject) {
    val timestamp by json.byDate("timestamp")
    val score = Score(
        json.getInt("score_exploration"),
        json.getInt("score_speed"),
        json.getInt("score_skill"),
        json.getInt("score_bonus")
    )
    val dungeonClass by json.byEnum("dungeon_class", Dungeon.DungeonClass::class)
    val teammates by json.byList<String>("teammates")
    val timeElapsed by json.byLong("elapsed_time")
    val damageDealt: Double? by json.byDouble("damage_dealt")
    val deaths by json.byInt("deaths")
    val mobsKilled by json.byInt("mobs_killed")
    val secretsFound by json.byInt("secrets_found")
    val damageMitigated: Double? by json.byDouble("damage_mitigated")
    val healing: Double? by json.byDouble("ally_healing")

    data class Score(
        val exploration: Int,
        val speed: Int,
        val skill: Int,
        val bonus: Int
    ) {
        override fun toString(): String {
            return (exploration + speed + skill + bonus).toString()
        }
    }

    override fun toString(): String {
        return "\nD: $timestamp - S: $score - C: ${dungeonClass.className}\n" +
                "T: $timeElapsed - Dmg: $damageDealt - D: $deaths - S: $secretsFound"
    }
}