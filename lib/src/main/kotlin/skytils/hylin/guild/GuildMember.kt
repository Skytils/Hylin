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
import skytils.hylin.extension.converter.*

class GuildMember(json: JsonObject) {
    val uuid by json.byUUID()
    val rank by json.byString()
    val joined by json.byDate()
    val questParticipation: Int? by json.byInt()
    val xpHistory by json.byMap<Int>("expHistory")

    override fun toString(): String {
        return "GuildMember(uuid=$uuid, rank='$rank', joined=$joined, questParticipation=$questParticipation, xpHistory=$xpHistory)"
    }
}
