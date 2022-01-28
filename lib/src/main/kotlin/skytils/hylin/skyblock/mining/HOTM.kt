/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2022  Skytils
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

package skytils.hylin.skyblock.mining

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.*

class HOTM(json: JsonObject) {
    val tokens by json.byInt()
    val experience by json.byLong()
    val spentTokens by json.byInt("tokens_spent")
    val spentMithrilPowder by json.byLong("powder_spent_mithril")
    val spentGemstonePowder by json.byLong("powder_spent_gemstone")
    // TODO: what's the difference between powder_mithril and powder_mithril_total? same for powder_gemstone and powder_gemstone_total
    val mithrilPowder by json.byLong("powder_mithril")
    val gemstonePowder by json.byLong("powder_gemstone")
    val lastHOTMReset by json.byDate("last_reset")
    val lastCrystalHollowsAccess by json.byDate("greater_mines_last_access")
    val selectedPickaxeAbility by json.byString("selected_pickaxe_ability")
    private val nodes: Map<String, Any> by json.byMap("nodes")
    val hotmLevel = nodes["special_0"] as Int
    val toggledPerks = nodes.filter { it.key.startsWith("toggle_") && it.value is Boolean }.entries.associate {
        it.key.substringAfter("toggle_") to it.value as Boolean
    }
    @Suppress("unchecked_cast")
    val perks = nodes.filter { it.value is Int && it.value in 0..50 && !it.key.startsWith("special_") && !it.key.startsWith("toggle_") } as Map<String, Int>
}
