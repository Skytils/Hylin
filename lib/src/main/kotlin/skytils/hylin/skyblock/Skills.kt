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
    val miningXP by lazy { skill("mining") }
    val combatXP by lazy { skill("combat") }
    val foragingXP by lazy { skill("foraging") }
    val tamingXP by lazy { skill("taming") }
    val alchemyXP by lazy { skill("alchemy") }
    val farmingXP by lazy { skill("farming") }
    val enchantingXP by lazy { skill("enchanting") }
    val fishingXP by lazy { skill("fishing") }
    val carpentryXP by lazy { skill("carpentry") }
    val runecraftingXP by lazy { skill("runecrafting") }

    fun skill(s: String): Float? = if (json.has("experience_skill_$s")) json.getFloat("experience_skill_$s") else null
}
