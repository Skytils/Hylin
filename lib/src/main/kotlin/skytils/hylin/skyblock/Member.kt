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
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.getJsonObject
import skytils.hylin.skyblock.item.Inventory
import skytils.hylin.skyblock.misc.Griffin
import skytils.hylin.skyblock.slayer.Slayers

/**
 * Represents a Hypixel Skyblock member
 * @param json A JsonObject to construct this member from
 */
@Suppress("MemberVisibilityCanBePrivate")
class Member(val json: JsonObject) {
    val lastSave by json.byDate("last_save")
    val stats: Map<String, Int>? by json.byMap()
    val collection: Map<String, Int>? by json.byMap()
    val objectives by json.byExternalMap<SkyblockObjective>()
    val tutorial by json.byList<String>()
    val quests by json.byExternalMap<SkyblockQuest>()
    val purse by json.byInt("coin_purse")
    val fairySouls by json.byInt("fairy_souls_collected")
    val pets by json.byExternalList<Pet>("pets")
    val craftedMinions: List<String>? by json.byList("crafted_generators")
    val visitedZones by json.byList<String>("visited_zones")
    val slayers by json.byExternal<Slayers>("slayer_bosses")
    val skills by lazy { Skills(json) }
    val unlockedCollections: List<String>? by json.byList("unlocked_coll_tiers")

    // Inventories
    val inventories = mutableListOf<Inventory>()
    val enderChest = inventory("ender_chest", "ender_chest_contents")
    val inventory = inventory("inventory", "inv_contents")
    val armor = inventory("armor", "inv_armor")
    val wardrobe = inventory("wardrobe", "wardrobe_contents")
    val vault = inventory("vault", "personal_vault_contents")
    val storage: Map<String, Inventory>? by json.byExternalMap("backpack_contents")

    // Misc
    val griffin by json.byExternal<Griffin>()

    init {
        storage?.forEach {
            inventories += it.value
        }
    }


    fun inventory(name: String, path: String): Inventory? {
        if (json.has(path)) {
            val inv = Inventory(name, json.getJsonObject(path))
            inventories.add(inv)
            return inv
        }
        return null
    }

    override fun toString(): String {
        return "Member(\n" +
                "\tlastSave=$lastSave, \n" +
                "\tstats=$stats, \n" +
                "\tcollection=$collection, \n" +
                "\tobjectives=$objectives, \n" +
                "\ttutorial=$tutorial, \n" +
                "\tquests=$quests, \n" +
                "\tpurse=$purse, \n" +
                "\tfairySouls=$fairySouls, \n" +
                "\tpets=$pets, \n" +
                "\tcraftedMinions=$craftedMinions, \n" +
                "\tvisitedZones=$visitedZones, \n" +
                "\tslayers=$slayers, \n" +
                "\tskills=$skills, \n" +
                "\tunlockedCollections=$unlockedCollections, \n" +
                "\tenderChest=$enderChest, \n" +
                "\tinventory=$inventory, \n" +
                "\tarmor=$armor, \n" +
                "\twardrobe=$wardrobe, \n" +
                "\tvault=$vault, \n" +
                "\tstorage=$storage\n" +
                ")"
    }

    // TODO: Dungeons external model


}
