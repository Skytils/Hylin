package skytils.kopixel.guild

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.*

class GuildMember(json: JsonObject) {
    val uuid                        by json.byUUID()
    val rank                        by json.byString()
    val joined                      by json.byDate()
    val questParticipation: Int?    by json.byInt()
    val xpHistory                   by json.byMap<Int>("expHistory")

    override fun toString(): String {
        return "GuildMember(uuid=$uuid, rank='$rank', joined=$joined, questParticipation=$questParticipation, xpHistory=$xpHistory)"
    }
}
