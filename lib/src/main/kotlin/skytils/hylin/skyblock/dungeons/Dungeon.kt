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
import skytils.hylin.extension.converter.byDouble
import skytils.hylin.extension.converter.byExternal
import skytils.hylin.extension.getJsonObject

class Dungeon(json: JsonObject) {

    constructor(normal: JsonObject?, master: JsonObject?) : this(JsonObject().also { it.add("normal", normal); it.add("master", master) })

    val experience: Double? by json.getJsonObject("normal").byDouble("experience")
    val normal: DungeonBase? by json.byExternal<DungeonBase>("normal")
    val master: DungeonBase? by json.byExternal<DungeonBase>("master")

    override fun toString(): String {
        return "XP: $experience\n" +
                "Normal Stats: $normal\n" +
                "Master Stats: $master"
    }

    enum class DungeonClass(val className: String) {
        healer("healer"),
        berserk("berserk"),
        mage("mage"),
        archer("archer"),
        tank("tank");

        override fun toString(): String {
            return this.className
        }
    }
}