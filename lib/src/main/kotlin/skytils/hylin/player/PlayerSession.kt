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

package skytils.hylin.player

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byBoolean
import skytils.hylin.extension.converter.byString

class PlayerSession(json: JsonObject) {
    val online by json.byBoolean()
    val gameType: String? by json.byString()
    val mode: String? by json.byString()
    val map: String? by json.byString()

    override fun toString(): String {
        return "PlayerSession(online=$online, gameType=$gameType, mode=$mode, map=$map)"
    }
}
