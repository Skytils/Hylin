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
