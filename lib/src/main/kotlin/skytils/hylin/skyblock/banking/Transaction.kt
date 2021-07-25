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

package skytils.hylin.skyblock.banking

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byDate
import skytils.hylin.extension.converter.byEnum
import skytils.hylin.extension.converter.byFloat
import skytils.hylin.extension.converter.byString

/**
 * Represents a Hypixel Skyblock banking transaction
 * @param json A JsonObject to construct this transaction from
 */
class Transaction(json: JsonObject) {
    val amount by json.byFloat("amount")
    val date by json.byDate("timestamp")
    val action by json.byEnum("action", BankingAction::class)
    val initiator by json.byString("initiator_name")

    override fun toString(): String {
        return "Transaction(amount=$amount, date=$date, action=$action, initiator='$initiator')"
    }
}
