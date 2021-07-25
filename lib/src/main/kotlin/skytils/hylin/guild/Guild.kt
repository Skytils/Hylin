/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2021  Skytils
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package skytils.hylin.guild

import com.google.gson.JsonObject
import skytils.hylin.extension.converter.*

class Guild(json: JsonObject) {
    val id by json.byString("_id")
    val name by json.byString()
    val tag by json.byString("tag")
    val tagColor by json.byString("tagColor")
    val created by json.byDate()
    val achievements by json.byMap<Int>()
    val guildXPByGameType by json.byMap<String>("guildExpByGameType")
    val xp by json.byInt("exp")
    val public by json.byBoolean("publiclyListed")
    val preferredGames by json.byList<String>()
    val guildRanks by json.byExternalList<GuildRank>("ranks")
    val members by json.byExternalList<GuildMember>()

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
