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
import skytils.hylin.extension.getFloat

/**
 * Represents all skills of a Member
 * @param json A JsonObject to construct data from
 */
class Skills(private val json: JsonObject) {
    val miningXP = skill("mining")
    val combatXP = skill("combat")
    val foragingXP = skill("foraging")
    val tamingXP = skill("taming")
    val alchemyXP = skill("alchemy")
    val farmingXP = skill("farming")
    val enchantingXP = skill("enchanting")
    val fishingXP = skill("fishing")
    val carpentryXP = skill("carpentry")
    val runecraftingXP = skill("runecrafting")

    fun skill(s: String): Float? = if (json.has("experience_skill_$s")) json.getFloat("experience_skill_$s") else null
}
