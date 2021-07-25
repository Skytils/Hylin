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

package skytils.hylin.skyblock.slayer

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byExternal

class Slayers(json: JsonObject) {
    val revenant by json.byExternal<RevenantSlayer>("zombie")
    val tarantula by json.byExternal<StandardSlayer>("spider")
    val enderman by json.byExternal<StandardSlayer>("enderman")
    val sven by json.byExternal<StandardSlayer>("wolf")
}
