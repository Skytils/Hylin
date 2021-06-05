package com.ryanhcode.kopixel.skyblock

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.ryanhcode.kopixel.extension.*
import com.ryanhcode.kopixel.extension.converter.byExternal
import com.ryanhcode.kopixel.extension.converter.byExternalMap
import com.ryanhcode.kopixel.extension.converter.byString
import com.ryanhcode.kopixel.skyblock.banking.Banking
import com.ryanhcode.kopixel.skyblock.item.Inventory
import com.ryanhcode.kopixel.skyblock.item.InventoryItem
import java.util.*

/**
 * Represents a Hypixel Skyblock Profile
 * @param json A JsonObject to construct this profile from
 */
class Profile(json: JsonObject) {
    val id: String          by json.byString("profile_id")
    val cuteName: String    by json.byString("cute_name")
    val members             by json.byExternalMap<Member>()
    val banking: Banking?   by json.byExternal<Banking>()

    inline fun scan(crossinline iterator: (uuid: UUID, member: Member, inv: Inventory, item: InventoryItem) -> Unit) {
        members.forEach { uuid, member ->
            member.inventories.forEach { inv ->
                inv.forEveryItem(backpacks = true) { item ->
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