package com.ryanhcode.kopixel.skyblock

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.converter.*
import com.ryanhcode.kopixel.extension.converter.byExternalList
import com.ryanhcode.kopixel.extension.converter.byExternalMap
import com.ryanhcode.kopixel.extension.converter.byInt
import com.ryanhcode.kopixel.extension.converter.byMap
import com.ryanhcode.kopixel.extension.getJsonObject
import com.ryanhcode.kopixel.extension.path
import com.ryanhcode.kopixel.skyblock.item.Inventory
import com.ryanhcode.kopixel.skyblock.item.InventoryItem
import com.ryanhcode.kopixel.skyblock.slayer.Slayers

/**
* Represents a Hypixel Skyblock member
* @param json A JsonObject to construct this member from
*/
@Suppress("MemberVisibilityCanBePrivate")
class Member(val json: JsonObject) {
    val lastSave                        by json.byDate("last_save")
    val stats: Map<String, Int>?        by json.byMap<Int>()
    val collection: Map<String, Int>?   by json.byMap<Int>()
    val objectives                      by json.byExternalMap<SkyblockObjective>()
    val tutorial                        by json.byList<String>()
    val quests                          by json.byExternalMap<SkyblockQuest>()
    val purse                           by json.byInt("coin_purse")
    val fairySouls                      by json.byInt("fairy_souls_collected")
    val pets                            by json.byExternalList<Pet>("pets")
    val craftedMinions: List<String>?   by json.byList<String>("crafted_generators")
    val visitedZones                    by json.byList<String>("visited_zones")
    val slayers                         by json.byExternal<Slayers>("slayer_bosses")
    val skills                          by lazy { Skills(json) }
    val unlockedCollections: List<String>? by json.byList<String> ("unlocked_coll_tiers")

    // Inventories
    val inventories = mutableListOf<Inventory>()
    val enderChest =    inventory("ender_chest","ender_chest_contents")
    val inventory =     inventory("inventory","inv_contents")
    val armor =         inventory("armor","inv_armor")
    val wardrobe =      inventory("wardrobe","wardrobe_contents")
    val vault =         inventory("vault","personal_vault_contents")
    val storage: Map<String, Inventory>? by json.byExternalMap<Inventory>("backpack_contents")

    init {
        storage?.forEach {
            inventories += it.value
        }
    }


    fun inventory(name: String, path: String): Inventory? {
        if(json.has(path)) {
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
