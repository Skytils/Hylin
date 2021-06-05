package com.ryanhcode.kopixel.skyblock

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.*
import com.ryanhcode.kopixel.extension.converter.*
import java.util.*

/**
 * Represents an objective in skyblock
 * @param json A JsonObject to construct this objective from
 */
class SkyblockObjective(json: JsonObject) {
    val status          by json.byEnum("status", ObjectiveStatus::class)
    val progress        by json.byInt("progress")
    val completedAt     by json.byDate("completed_at")

    override fun toString(): String {
        return "SkyblockObjective(\n" +
                "\tstatus='$status', \n" +
                "\tprogress=$progress, \n" +
                "\tcompletedAt=$completedAt\n" +
                ")"
    }


}
