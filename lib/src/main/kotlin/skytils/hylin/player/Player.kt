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

package skytils.hylin.player

import com.google.gson.JsonObject
import net.minecraft.util.EnumChatFormatting
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.path
import skytils.hylin.player.rank.MonthlyPackageRank
import skytils.hylin.player.rank.PackageRank
import skytils.hylin.player.rank.SpecialRank

/**
 * Represents a Hypixel Player
 * @param json A JsonObject to construct this player from
 */
class Player(json: JsonObject) {
    val player = json.path("player")

    val name by player.byString("displayname")
    val uuid by player.byUUID("uuid")
    val aliases by player.byList<String>("knownAliases")
    val socials by player.byExternal<SocialMediaLinks>("socialMedia")

    val firstLogin by player.byDate()
    val lastLogin by player.byDate()
    val lastLogout by player.byDate()

    val specialRank: SpecialRank? by player.byEnum("rank", SpecialRank::class)
    val monthlyPackageRank: MonthlyPackageRank? by player.byEnum("monthlyPackageRank", MonthlyPackageRank::class)
    val plusColor by lazy {
        val rankPlusColor: String? by player.byString()
        return@lazy EnumChatFormatting.getValueByName(rankPlusColor ?: "RED")
    }
    val mvpPlusPlusColor by lazy {
        val monthlyRankColor: String? by player.byString()
        return@lazy EnumChatFormatting.getValueByName(monthlyRankColor ?: "GOLD")
    }
    val rank by lazy {
        val packageRank: PackageRank? by player.byEnum("newPackageRank", PackageRank::class)
        if (packageRank == PackageRank.MVP_PLUS && monthlyPackageRank == MonthlyPackageRank.SUPERSTAR) {
            return@lazy PackageRank.MVP_PLUS_PLUS
        }
        return@lazy packageRank ?: PackageRank.NONE
    }
    val rankPrefix by lazy {
        val prefix: String? by player.byString()
        return@lazy prefix ?: when (specialRank ?: rank) {
            PackageRank.VIP -> "§a[VIP]"
            PackageRank.VIP_PLUS -> "§a[VIP§6+§a]"
            PackageRank.MVP -> "§b[MVP]"
            PackageRank.MVP_PLUS -> "§b[MVP${plusColor}+§b]"
            PackageRank.MVP_PLUS_PLUS -> "${mvpPlusPlusColor}[MVP${plusColor}++${mvpPlusPlusColor}]"
            SpecialRank.HELPER-> "§9[HELPER]"
            SpecialRank.MODERATOR -> "§2[MOD]"
            SpecialRank.GAME_MASTER -> "§2[GM]"
            SpecialRank.ADMIN -> "§c[ADMIN]"
            SpecialRank.YOUTUBER -> "§c[§fYOUTUBE§c]"
            else -> "§7"
        }
    }

    val networkXP by player.byDouble("networkExp")
    val achievementPoints by player.byInt("achievementPoints")
    val achievements by player.byMap<Int>()
    val achievementsOneTime by player.byList<String>()
    val karma by player.byDouble("karma")

    val mcVersionRp by player.byString()
    val petConsumables by player.byMap<Int>()
    val settings by player.byMap<Int>()
    val mostRecentGameType by player.byString()

    /**
     * Checks if this player is online
     * @return If the player is online or not
     */
    fun online(): Boolean {
        return lastLogin > lastLogout
    }

    override fun toString(): String {
        return "Player(\n" +
                "\tname='$name', \n" +
                "\tuuid=$uuid, \n" +
                "\taliases=$aliases, \n" +
                "\tsocials=$socials, \n" +
                "\tfirstLogin=$firstLogin, \n" +
                "\tlastLogin=$lastLogin, \n" +
                "\tlastLogout=$lastLogout, \n" +
                "\tspecialRank=$specialRank, \n" +
                "\trank=$rank, \n" +
                "\tnetworkXP=$networkXP, \n" +
                "\tachievementPoints=$achievementPoints, \n" +
                "\tachievements=$achievements, \n" +
                "\tachievementsOneTime=$achievementsOneTime, \n" +
                "\tkarma=$karma\n" +
                ")"
    }


}
