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

package skytils.hylin.skyblock.misc

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byExternalList
import skytils.hylin.extension.converter.byInt
import skytils.hylin.extension.converter.byLong

/**
 * Represents a Hypixel Skyblock Profile's Mythological Ritual Data
 * @param json A JsonObject to construct data from
 */
class Griffin(json: JsonObject) {
    val burrows by json.byExternalList<Burrow>()

    override fun toString(): String {
        return """Griffin(
            #   burrows=$burrows
        #)""".trimMargin("#")
    }
}

/***
 * Represents a Griffin Burrow from the Mythological Ritual event
 * @param json A JsonObject to construct data from
 * @property timestamp the epoch of when this Burrow was generated
 * @property x the X coordinate of the burrow
 * @property y the y coordinate of the burrow
 * @property z the z coordinate of the burrow
 * @property type the type of burrow, 0 denotes an empty burrow, 1 denotes a mob, and 2 denotes a treasure
 * @property tier the tier of Griffin used, -1 means there was no Griffin, while all rarities are 0-index based
 * @property chain the 0-indexed order of this burrow in the chain of 4
 */
class Burrow(json: JsonObject) {
    val timestamp by json.byLong("ts")
    val x by json.byInt()
    val y by json.byInt()
    val z by json.byInt()
    val type by json.byInt()
    val tier by json.byInt()
    val chain by json.byInt()

    override fun toString(): String {
        return """Burrow(
            #   timestamp=$timestamp, 
            #   x=$x, 
            #   y=$y, 
            #   z=$z, 
            #   type=$type, 
            #   tier=$tier, 
            #   chain=$chain
            #)""".trimMargin("#")
    }
}