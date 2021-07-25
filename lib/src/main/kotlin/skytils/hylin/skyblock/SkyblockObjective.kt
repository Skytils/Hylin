package skytils.hylin.skyblock

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.byDate
import skytils.hylin.extension.converter.byEnum
import skytils.hylin.extension.converter.byInt

/**
 * Represents an objective in skyblock
 * @param json A JsonObject to construct this objective from
 */
class SkyblockObjective(json: JsonObject) {
    val status by json.byEnum("status", ObjectiveStatus::class)
    val progress by json.byInt("progress")
    val completedAt by json.byDate("completed_at")

    override fun toString(): String {
        return "SkyblockObjective(\n" +
                "\tstatus='$status', \n" +
                "\tprogress=$progress, \n" +
                "\tcompletedAt=$completedAt\n" +
                ")"
    }
}
