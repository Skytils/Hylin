package skytils.kopixel.skyblock

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.*
import skytils.kopixel.skyblock.item.Tier
import java.util.*

/**
 * Represents a Pet in a Member
 * @param json A JsonObject to construct data from
 */
class Pet(json: JsonObject) {
    val uuid: UUID?         by json.byUUID()
    val type                by json.byString()
    val xp                  by json.byFloat("exp")
    val active              by json.byBoolean()
    val tier                by json.byEnum("tier", Tier::class)
    val heldItem: String?   by json.byString()
    val candyUsed           by json.byInt()
    val skin: String?       by json.byString()

    override fun toString(): String {
        return "Pet(\n" +
                "\tuuid=$uuid, \n" +
                "\ttype='$type', \n" +
                "\txp=$xp, \n" +
                "\tactive=$active, \n" +
                "\ttier=$tier, \n" +
                "\theldItem=$heldItem, \n" +
                "\tcandyUsed=$candyUsed, \n" +
                "\tskin=$skin\n" +
                ")"
    }


}
