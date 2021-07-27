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

package skytils.hylin.skyblock.bazaar

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byDouble
import skytils.hylin.extension.converter.byLong
import skytils.hylin.extension.converter.byString

class BazaarStatus(json: JsonObject) {
    val productId by json.byString()
    val sellPrice by json.byDouble()
    val sellVolume by json.byLong()
    val sellMovingWeek by json.byLong()
    val sellOrders by json.byLong()
    val buyPrice by json.byDouble()
    val buyVolume by json.byLong()
    val buyMovingWeek by json.byLong()
    val buyOrders by json.byLong()

    override fun toString(): String {
        return """BazaarStatus(
            #   productId=$productId, 
            #   sellPrice=$sellPrice, 
            #   sellVolume=$sellVolume, 
            #   sellMovingWeek=$sellMovingWeek, 
            #   sellOrders=$sellOrders, 
            #   buyPrice=$buyPrice, 
            #   buyVolume=$buyVolume, 
            #   buyMovingWeek=$buyMovingWeek, 
            #   buyOrders=$buyOrders
            #)""".trimMargin("#")
    }
}