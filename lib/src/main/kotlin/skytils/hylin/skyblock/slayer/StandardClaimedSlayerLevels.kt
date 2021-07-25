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
import skytils.hylin.extension.converter.byBoolean

open class StandardClaimedSlayerLevels(json: JsonObject) {
    val level1: Boolean? by json.byBoolean("level_1")
    val level2: Boolean? by json.byBoolean("level_2")
    val level3: Boolean? by json.byBoolean("level_3")
    val level4: Boolean? by json.byBoolean("level_4")
    val level5: Boolean? by json.byBoolean("level_5")
    val level6: Boolean? by json.byBoolean("level_6")
    val level7: Boolean? by json.byBoolean("level_7")
    val level8: Boolean? by json.byBoolean("level_8")
    val level9: Boolean? by json.byBoolean("level_9")
}
