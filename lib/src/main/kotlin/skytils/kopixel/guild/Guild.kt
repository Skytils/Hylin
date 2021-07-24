package skytils.kopixel.guild

import com.google.gson.JsonObject
import skytils.kopixel.extension.converter.*

class Guild(json: JsonObject) {
    val id                  by json.byString("_id")
    val name                by json.byString()
    val tag                 by json.byString("tag")
    val tagColor            by json.byString("tagColor")
    val created             by json.byDate()
    val achievements        by json.byMap<Int>()
    val guildXPByGameType   by json.byMap<String>("guildExpByGameType")
    val xp                  by json.byInt("exp")
    val public              by json.byBoolean("publiclyListed")
    val preferredGames      by json.byList<String>()
    val guildRanks          by json.byExternalList<GuildRank>("ranks")
    val members             by json.byExternalList<GuildMember>()

    override fun toString(): String {
        return "Guild(\n" +
                "\tid='$id', \n" +
                "\tname='$name', \n" +
                "\ttag='$tag', \n" +
                "\ttagColor='$tagColor', \n" +
                "\tcreated=$created, \n" +
                "\tachievements=$achievements, \n" +
                "\tguildXPByGameType=$guildXPByGameType, \n" +
                "\txp=$xp, \n" +
                "\tpublic=$public, \n" +
                "\tpreferredGames=$preferredGames, \n" +
                "\tguildRanks=$guildRanks, \n" +
                "\tmembers=$members\n" +
                ")"
    }
}
