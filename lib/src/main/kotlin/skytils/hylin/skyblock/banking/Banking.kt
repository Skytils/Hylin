package skytils.hylin.skyblock.banking

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byExternalList
import skytils.hylin.extension.converter.byFloat

/**
 * Represents a Hypixel Skyblock Profile's Bank API
 * @param json A JsonObject to construct banking data from
 */
class Banking(json: JsonObject) {
    val balance by json.byFloat("balance")
    val transactions by json.byExternalList<Transaction>()

    override fun toString(): String {
        return "Banking(\n\tbalance=$balance, \n\ttransactions=$transactions\n)"
    }
}
