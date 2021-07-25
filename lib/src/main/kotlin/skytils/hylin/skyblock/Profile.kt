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
import skytils.hylin.extension.converter.byExternal
import skytils.hylin.extension.converter.byExternalMap
import skytils.hylin.extension.converter.byString
import skytils.hylin.extension.toUUID
import skytils.hylin.skyblock.banking.Banking
import skytils.hylin.skyblock.item.Inventory
import skytils.hylin.skyblock.item.InventoryItem
import java.util.*

/**
 * Represents a Hypixel Skyblock Profile
 * @param json A JsonObject to construct this profile from
 */
class Profile(json: JsonObject) {
    val id: String by json.byString("profile_id")
    val cuteName: String by json.byString("cute_name")
    val members by json.byExternalMap<Member>()
    val banking: Banking? by json.byExternal<Banking>()

    inline fun scan(crossinline iterator: (uuid: UUID, member: Member, inv: Inventory, item: InventoryItem) -> Unit) {
        members.forEach { uuid, member ->
            member.inventories.forEach { inv ->
                inv.forEveryItem { item ->
                    iterator(uuid.toUUID(), member, inv, item)
                }
            }
        }
    }

    override fun toString(): String {
        return "Profile(\n" +
                "\tid='$id', \n" +
                "\tcuteName='$cuteName', \n" +
                "\tmembers=$members, \n" +
                "\tbanking=$banking\n" +
                ")"
    }


}