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

package skytils.hylin.guild

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byBoolean
import skytils.hylin.extension.converter.byDate
import skytils.hylin.extension.converter.byInt
import skytils.hylin.extension.converter.byString

class GuildRank(json: JsonObject) {
    val name by json.byString()
    val default by json.byBoolean()
    val tag by json.byString()
    val created by json.byDate()
    val priority by json.byInt()

    override fun toString(): String {
        return "GuildRank(name='$name', default=$default, tag='$tag', created=$created, priority=$priority)"
    }
}
