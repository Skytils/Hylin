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
import skytils.hylin.skyblock.item.Tier
import java.util.*

/**
 * Represents a Pet in a Member
 * @param json A JsonObject to construct data from
 */
class Pet(json: JsonObject) {
    val uuid: UUID? by json.byUUID()
    val type by json.byString()
    val xp by json.byFloat("exp")
    val active by json.byBoolean()
    val tier by json.byEnum("tier", Tier::class)
    val heldItem: String? by json.byString()
    val candyUsed by json.byInt()
    val skin: String? by json.byString()

    override fun toString(): String {
        return "Pet(\n" +
                "\tuuid=$uuid, \n" +
                "\ttype='$type', \n" +
                "\txp=$xp, \n" +
                "\tactive=$active, \n" +
                "\ttier=$tier, \n" +
                "\theldItem=$heldItem, \n" +
                "\tcandyUsed=$candyUsed, \n" +
                "\tskin=$skin\n" +
                ")"
    }


}
