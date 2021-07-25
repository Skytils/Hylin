package skytils.kopixel.skyblock

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.byDate
import skytils.kopixel.extension.converter.byEnum

/**
 * Represents a quest in skyblock
 * @param json A JsonObject to construct this quest from
 */
class SkyblockQuest(json: JsonObject) {
    val status by json.byEnum("status", ObjectiveStatus::class)
    val activatedAt by json.byDate("activated_at")
    val completedAt by json.byDate("completed_at")
}
