package com.ryanhcode.kopixel.player

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.*
import com.ryanhcode.kopixel.extension.converter.*
import com.ryanhcode.kopixel.player.rank.PackageRank
import com.ryanhcode.kopixel.player.rank.SpecialRank
import java.util.*
import kotlin.collections.HashMap

/**
 * Represents a Hypixel Player
 * @param json A JsonObject to construct this player from
 */
class Player(json: JsonObject) {
    val player = json.path("player")

    val name                        by player.byString("displayname")
    val uuid                        by player.byUUID("uuid")
    val aliases                     by player.byList<String>("knownAliases")
    val socials                     by player.byExternal<SocialMediaLinks>("socialMedia")

    val firstLogin                  by player.byDate()
    val lastLogin                   by player.byDate()
    val lastLogout                  by player.byDate()

    val specialRank: SpecialRank?   by player.byEnum("rank", SpecialRank::class)
    val rank                        by player.byEnum("newPackageRank", PackageRank::class)

    val networkXP                   by player.byInt("networkExp")
    val achievementPoints           by player.byInt("achievementPoints")
    val achievements                by player.byMap<Int>()
    val achievementsOneTime         by player.byList<String>()
    val karma                       by player.byInt("karma")

    val mcVersionRp                 by player.byString()
    val petConsumables              by player.byMap<Int>()
    val settings                    by player.byMap<Int>()
    val mostRecentGameType          by player.byString()

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
