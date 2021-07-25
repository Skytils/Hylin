package skytils.kopixel.skyblock.banking

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byDate
import skytils.kopixel.extension.converter.byEnum
import skytils.kopixel.extension.converter.byFloat
import skytils.kopixel.extension.converter.byString

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
