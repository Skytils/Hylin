/*
 * Hylin - Hypixel API Wrapper in Kotlin
 * Copyright (C) 2022  Skytils
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

package skytils.hylin.skyblock.mining

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.getJsonObject
import skytils.hylin.extension.getString
import skytils.hylin.extension.roundToPrecision
import kotlin.math.*

class HOTM(json: JsonObject) {
    val tokens by json.byInt()
    val experience by json.byLong()
    val spentTokens by json.byInt("tokens_spent")
    val spentMithrilPowder by json.byLong("powder_spent_mithril")
    val spentGemstonePowder by json.byLong("powder_spent_gemstone")
    // TODO: what's the difference between powder_mithril and powder_mithril_total? same for powder_gemstone and powder_gemstone_total
    val mithrilPowder by json.byLong("powder_mithril")
    val gemstonePowder by json.byLong("powder_gemstone")
    val lastHOTMReset by json.byDate("last_reset")
    val lastCrystalHollowsAccess by json.byDate("greater_mines_last_access")
    val selectedPickaxeAbility = HOTMSlot.slots.find { it.id == json.getString("selected_pickaxe_ability") }
    val toggledPerks = json.getJsonObject("nodes").entrySet().filter { it.key.startsWith("toggle_") && it.value is JsonPrimitive && (it.value as JsonPrimitive).isBoolean }.associate { entry ->
        HOTMSlot.slots.find { entry.key.substringAfter("toggle_") == it.id }!! to entry.value.asBoolean
    }
    val perks = json.getJsonObject("nodes").entrySet().filter { it.value is JsonPrimitive && (it.value as JsonPrimitive).isNumber && !it.key.startsWith("toggle_") }.associate { entry ->
        HOTMSlot.slots.find { entry.key == it.id }!! to entry.value.asInt
    }

    // Stat descriptions and calculation from SKyCrypt
    sealed class HOTMSlot(val id: String, val name: String, val slotNum: Int) {
        sealed class Perk(id: String, name: String, val maxLevel: Int, slotNum: Int) : HOTMSlot(id, name, slotNum) {
            sealed class SpecialPerk(id: String, name: String, maxLevel: Int, slotNum: Int) : Perk(id, name, maxLevel, slotNum) {
                object PeakOfTheMountain : SpecialPerk("special_0", "Peak of the Mountain", 5, 22) {
                    override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
                }

                override fun getItem(level: Int): ItemStack {
                    return when (level) {
                        0 -> ItemStack(Blocks.bedrock).setStackDisplayName("§c${name}")
                        else -> ItemStack(Blocks.redstone_block, level).setStackDisplayName("§${if (level == maxLevel) 'a' else 'c'}${name}")
                    }
                }
            }
            object MiningSpeed2 : Perk("mining_speed_2", "Mining Speed II", 50, 2) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${40 * level} §6⸕ Mining Speed§7.")
            }
            object PowderBuff : Perk("powder_buff", "Powder Buff", 50, 4) {
                override fun getLore(level: Int) = listOf("§7Gain §a${level}% §7more Mithril Powder and Gemstone Powder.")
            }
            object MiningFortune2 : Perk("mining_fortune_2", "Mining Fortune II", 50, 6) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${5 * level} §6☘ Mining Fortune§7.")
            }
            object LonesomeMiner : Perk("lonesome_miner", "Lonesome Miner", 45, 11) {
                override fun getLore(level: Int) = listOf("§7Increases §c❁ Strength, §9☣ Crit Chance, §9☠ Crit Damage, §a❈ Defense, and §c❤ Health §7statistics gain by §a${round(5 + (level - 1) * 0.5)}% §7while in the Crystal Hollows.")
            }
            object Professional : Perk("professional", "Professional", 140, 12) {
                override fun getLore(level: Int) = listOf("§7Gain §a+${(5 * level) + 50}§7 §6⸕ Mining Speed§7 when mining Gemstones.")
            }
            object Mole : Perk("mole", "Mole", 190, 13) {
                override fun getLore(level: Int): List<String> {
                    val chance = 50 + (level - 1) * 5
                    var blocks = 1 + chance / 100
                    var percent = chance - (chance / 100) * 100
                    if (percent == 0) {
                        blocks -= 1
                        percent = 100
                    }

                    val blockString= when (blocks) {
                        1 -> "1"
                        2 -> "a 2nd"
                        3 -> "a 3rd"
                        else -> "a ${blocks}th"
                    }

                    return listOf("§7When mining hard stone, you have a §a${percent}%§7 chance to mine §a${blocks}§7 adjacent hard stone block.")
                }
            }
            object Fortunate : Perk("fortunate", "Fortunate", 20, 14) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${5 * level}§7 §6☘ Mining Fortune§7 when mining Gemstone.")
            }
            object GreatExplorer : Perk("great_explorer", "Great Explorer", 20, 15) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${20 + (level - 1) * 4}%§7 §7chance to find treasure.")
            }
            object GoblinKiller : Perk("goblin_killer", "Goblin Killer", 1, 20) {
                override fun getLore(level: Int) = listOf("§7Killing a §6Golden Goblin §7gives §2200 §7extra §2Mithril Powder§7, while killing other Goblins gives some based on their wits.")
            }
            object StarPowder : Perk("star_powder", "Star Powder", 1, 24) {
                override fun getLore(level: Int) = listOf("§7Mining Mithril Ore near §5Fallen Crystals §7gives §a+3 §7extra Mithril Powder.")
            }
            object SkyMall : Perk("daily_effect", "Sky Mall", 1, 28) {
                override fun getLore(level: Int) = listOf(      "§7Every SkyBlock day, you receive a random buff in the §2Dwarven Mines§7.",
                    "",
                    "§7Possible Buffs",
                    "§8 ■ §7Gain §a+100 §6⸕ Mining Speed.",
                    "§8 ■ §7Gain §a+50 §6☘ Mining Fortune.",
                    "§8 ■ §7Gain §a+15% §7chance to gain extra Powder while mining.",
                    "§8 ■ §7Reduce Pickaxe Ability cooldown by §a20%",
                    "§8 ■ §7§a10x §7chance to find Goblins while mining.",
                    "§8 ■ §7Gain §a5x §9Titanium §7drops.",)
            }
            object MiningMadness : Perk("mining_madness", "Mining Madness", 1, 29) {
                override fun getLore(level: Int) = listOf("§7Grants §a+50 §6⸕ Mining Speed §7and §6☘ Mining Fortune§7.")
            }
            object SeasonedMineman : Perk("mining_experience", "Seasoned Mineman", 100, 30) {
                override fun getLore(level: Int) = listOf("§7Increases your Mining experience gain by §a${(5 + level * 0.1).roundToPrecision(1)}%§7.")
            }
            object EfficientMiner : Perk("efficient_miner", "Efficient Miner", 100, 31) {
                override fun getLore(level: Int) = listOf("§7When mining ores, you have a §a${(10 + level * 0.4).roundToPrecision(1)}%§7 chance to mine §a${ceil((level + 1) / 20.0)} §7adjacent ores.")
            }
            object Orbiter : Perk("experience_orbs", "Orbiter", 80, 32) {
                override fun getLore(level: Int) = listOf("§7When mining ores, you have a §a${(0.2 + level * 0.01).roundToPrecision(2)}%§7 chance to get a random amount of experience orbs.")
            }
            object FrontLoaded : Perk("front_loaded", "Front Loaded", 1, 33) {
                override fun getLore(level: Int) = listOf("§7Grants §a+100 §6⸕ Mining Speed §7and §6☘ Mining Fortune §7for the first §e2,500 §7ores you mine in a day.")
            }
            object PrecisionMining : Perk("precision_mining", "Precision Mining", 1, 34) {
                override fun getLore(level: Int) = listOf("§7When mining ore, a particle target appears on the block that increases your §6⸕ Mining Speed §7by §a30% §7when aiming at it.")
            }
            object LuckOfTheCave : Perk("random_event", "Luck of the Cave", 45, 38) {
                override fun getLore(level: Int) = listOf("§7Increases the chance for you to trigger rare occurrences in §2Dwarven Mines §7by §a${5 + level}%§7.")
            }
            object DailyPowder : Perk("daily_powder", "Daily Powder", 100, 40) {
                override fun getLore(level: Int) = listOf("§7Gain §a${400 + (level - 1) * 36} Powder §7from the first ore you mine every day. Works for all Powder types.")
            }
            object Crystallized : Perk("fallen_star_bonus", "Crystallized", 30, 42) {
                override fun getLore(level: Int): List<String> {
                    val num = 20 + (level - 1) * 6
                    return listOf("§7Grants §a+${num} §6⸕ Mining Speed §7and a §a${num}% §7chance to deal §a+1 §7extra damage near §5Fallen Stars§7.")
                }
            }
            object TitaniumInsanium : Perk("titanium_insanium", "Titanium Insanium", 50, 48) {
                override fun getLore(level: Int) = listOf("§7When mining Mithril Ore, you have a §a${(2 + level * 0.1).roundToPrecision(1)}%§7 chance to convert the block into Titanium Ore.")
            }
            object MiningFortune : Perk("mining_fortune", "Mining Fortune", 50, 49) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${5 * level} §6☘ Mining Fortune§7.")
            }
            object QuickForge : Perk("forge_time", "Quick Forge", 20, 50) {
                override fun getLore(level: Int): List<String> = listOf("§7Decreases the time it takes to forge by §a${if (level == maxLevel) 30 else (10 + 0.5 * level).roundToPrecision(1)}%§7.")
            }
            object MiningSpeed : Perk("mining_speed", "Mining Speed", 50, 58) {
                override fun getLore(level: Int) = listOf("§7Grants §a+${20 * level} §6⸕ Mining Speed§7.")
            }

            override fun getItem(level: Int): ItemStack {
                return when (level) {
                    this.maxLevel -> ItemStack(Items.diamond, level)
                    0 -> ItemStack(Items.coal).setStackDisplayName("§c${name}")
                    else -> ItemStack(Items.emerald, level).setStackDisplayName("§c${name}")
                }
            }
        }
        sealed class PickaxeAbility(id: String, name: String, slotNum: Int) : HOTMSlot(id, name, slotNum) {
            object VeinSeeker : PickaxeAbility("vein_seeker", "Vein Seeker",  10) {
                override fun getLore(level: Int) = listOf(
                    "§6Pickaxe Ability: Vein Seeker",
                    "§7Points in the direction of the nearest vein and grants §a+${1 + level} §6Mining Spread §7for §a${10 + level * 2}s§7.",
                    "§8Cooldown: §a60s"
                )
            }
            object ManiacMiner : PickaxeAbility("maniac_miner", "Maniac Miner", 16) {
                override fun getLore(level: Int) = listOf(
                    "§6Pickaxe Ability: Maniac Miner",
                    "§7Spends all your Mana and grants §a+[Some] §6⸕ Mining Speed §7for every 10 Mana spent, for §a${5 + level * 5}s§7.",
                    "§8Cooldown: §a${if (level <= 1) 60 else 59}s"
                )
            }
            object MiningSpeedBoost : PickaxeAbility("mining_speed_boost", "Mining Speed Boost", 47) {
                override fun getLore(level: Int) = listOf(
                    "§6Pickaxe Ability: Mining Speed Boost",
                    "§7Grants §a+${100 + level * 100}% §6⸕ Mining Speed §7for §a${10 + level * 5}s§7."
                )
            }
            object Pickobulus : PickaxeAbility("pickaxe_toss", "Pickobulus", 51) {
                override fun getLore(level: Int) = listOf(
                    "§6Pickaxe Ability: Pickobulus",
                    "§7Throw your pickaxe to create an explosion on impact, mining all ores within a §a${if (level == 3) 3 else 2}§7 block radius.",
                    "§8Cooldown: §a${if (level <= 1) 120 else 110}s"
                )
            }

            override fun getItem(level: Int): ItemStack {
                return when (level) {
                    0 -> ItemStack(Blocks.coal_block).setStackDisplayName("§c${name}")
                    else -> ItemStack(Blocks.emerald_block, level).setStackDisplayName("§a${name}")
                }
            }
        }
        sealed class HOTMLevel(val hotmLevel: Int) : HOTMSlot("hotm_tier_${hotmLevel}", "Tier $hotmLevel", (9 * 7) - ((hotmLevel - 1) * 9)) {
            object Tier1 : HOTMLevel(1) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier2 : HOTMLevel(2) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier3 : HOTMLevel(3) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier4 : HOTMLevel(4) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier5 : HOTMLevel(5) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier6 : HOTMLevel(6) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }
            object Tier7 : HOTMLevel(7) {
                override fun getLore(level: Int): List<String> = listOf("I was too lazy to put the rewards here")
            }

            override fun getItem(level: Int): ItemStack {
                return when {
                    level == -1 -> ItemStack(Blocks.stained_glass_pane, hotmLevel, EnumDyeColor.YELLOW.metadata).setStackDisplayName("§e${name}")
                    level >= hotmLevel -> ItemStack(Blocks.stained_glass_pane, hotmLevel, EnumDyeColor.LIME.metadata).setStackDisplayName("§a${name}")
                    else -> ItemStack(Blocks.stained_glass_pane, hotmLevel, EnumDyeColor.RED.metadata).setStackDisplayName("§c${name}")
                }
            }
        }

        abstract fun getItem(level: Int): ItemStack
        open fun getLore(level: Int): List<String> = emptyList()

        companion object {
            val slots = hashSetOf<HOTMSlot>()

            init {
                HOTMSlot::class.sealedSubclasses.flatMapTo(slots) { type ->
                    type.sealedSubclasses.mapNotNull { item ->
                        if (item == Perk.SpecialPerk::class) return@mapNotNull Perk.SpecialPerk.PeakOfTheMountain
                        item.objectInstance
                    }
                }
            }
        }
    }

}
