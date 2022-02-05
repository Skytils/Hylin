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
import skytils.hylin.extension.converter.byExternalList
import skytils.hylin.extension.converter.byInt
import skytils.hylin.extension.converter.byList
import skytils.hylin.extension.getJsonObject
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
open class DungeonBase(val json: JsonObject) {
    /**
     * This is not available on a master dungepn
     */
    val timesPlayed: Map<Int, Long>? by json.byMapKeyed("times_played")
    /**
     * This is not available on a master dungepn
     */
    val watcherKills: Map<Int, Long>? by json.byMapKeyed("watcher_kills")
    val completions: Map<Int, Long>? by json.byMapKeyed("tier_completions")
    val milestoneCompletions: Map<Int, Long>? by json.byMapKeyed("milestone_completions")
    val highestCompletion: Int? by json.byInt("highest_tier_completed")
    val fastestTime: Map<Int, Duration>? by json.byMapKeyed("fastest_time")
    val fastestTimeS: Map<Int, Duration>? by json.byMapKeyed("fastest_time_s")
    val fastestTimeSPlus: Map<Int, Duration>? by json.byMapKeyed("fastest_time_s_plus")
    val bestRuns = if (json.has("best_runs")) bestRuns(json.getJsonObject("best_runs")) else null
    val bestScores: Map<Int, Int>? by json.byMapKeyed("best_score")
    val mobsKilled: Map<Int, Int>? by json.byMapKeyed("mobs_killed")
    val highestDamages by lazy { getHighestDamages(json) }
    val mostHealing: Map<Int, Double>? by json.byMapKeyed("most_healing")

    private fun bestRuns(json: JsonObject): Map<Int, List<DungeonRun>> {
        return json.entrySet().associate {
            val list by json.byExternalList<DungeonRun>(it.key)
            it.key.toInt() to list
        }
    }

    private fun getHighestDamages(json: JsonObject): Map<DungeonClass, Map<String, Double>> {
        val res: Map<DungeonClass, Map<String, Double>> = DungeonClass.values().associate {
            if (!json.has("most_damage_${it.className}")) return@associate it to emptyMap<String, Double>()
            val a by json.byMap<Double>("most_damage_${it.className}")
            it to a
        }
        return res
    }

    override fun toString(): String {
        return "Completions: $completions\n" +
                "Milestone Completions: $milestoneCompletions\n" +
                "Highest Completions: $highestCompletion\n" +
                "Fastest Times: $fastestTime\n" +
                "Fastest Times S: $fastestTimeS\n" +
                "Fastest Time S+: $fastestTimeSPlus\n" +
                "Best Runs: $bestRuns\n" +
                "Best Scores: $bestScores\n" +
                "Mobs Killed: $mobsKilled\n" +
                "Highest Damages: $highestDamages\n" +
                "Most Healing: $mostHealing"
    }
}