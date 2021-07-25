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
import skytils.hylin.extension.converter.JsonPropertyDelegate
import skytils.hylin.extension.converter.byDouble
import skytils.hylin.extension.converter.byEnum
import skytils.hylin.extension.converter.byList
import skytils.hylin.extension.getJsonObject

class DungeonStats(json: JsonObject) {
    val selectedClass : Dungeon.DungeonClass? by json.byEnum("selected_dungeon_class", Dungeon.DungeonClass::class)
    val firstTalks: List<String>? by json.byList<String>("dungeons_blah_blah")
    val classExperiences: List<Class>? by lazy {
        json["player_classes"].asJsonObject.entrySet().map {
            Class(it.value.asJsonObject.byDouble("experience"), Dungeon.DungeonClass.valueOf(it.key))
        }
    }
    val dungeons: List<Dungeon> = dungeon(json.getJsonObject("dungeon_types"))

    fun dungeon(json: JsonObject): List<Dungeon> {
        val res = json.entrySet().map {
            if (it.key.startsWith("master_")) return@map null
            val masterKey = "master_${it.key}"
            return@map Dungeon(
                json.getJsonObject(it.key),
                if (json.has(masterKey)) json.getJsonObject(masterKey) else null)
        }.toMutableList().filterNotNull()
        return res
    }

    override fun toString(): String {
        return "Selected Class: ${selectedClass}\n" +
                "First talks: $firstTalks\n" +
                "Class Experiences: $classExperiences\n" +
                "Dungeons: $dungeons"
    }

    // TODO add dungeon journals

    data class Class(private val experienceIn : JsonPropertyDelegate<Double>, val Class: Dungeon.DungeonClass) {
        val experience : Double? by experienceIn
        override fun toString(): String {
            return "${Class.className}: $experience"
        }
    }
}