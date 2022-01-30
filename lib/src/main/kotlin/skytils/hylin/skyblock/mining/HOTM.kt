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
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.getString

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
    private val nodes: Map<String, Any> by json.byMap("nodes")
    val toggledPerks = nodes.filter { it.key.startsWith("toggle_") && it.value is Boolean }.entries.associate { entry ->
        HOTMSlot.slots.find { entry.key.substringAfter("toggle_") == it.id }!! to entry.value as Boolean
    }
    val perks = nodes.filter { it.value is Int && !it.key.startsWith("toggle_") }.entries.associate { entry ->
        HOTMSlot.slots.find { entry.key == it.id }!! to entry.value as Int
    }


    sealed class HOTMSlot(val id: String, val name: String, val slotNum: Int) {
        sealed class Perk(id: String, name: String, maxLevel: Int, slotNum: Int) : HOTMSlot(id, name, slotNum) {
            sealed class SpecialPerk(id: String, name: String, maxLevel: Int, slotNum: Int) : Perk(id, name, maxLevel, slotNum) {
                object PeakOfTheMountain : SpecialPerk("special_0", "Peak of the Mountain", 5, 22)
            }
            object MiningSpeed2 : Perk("mining_speed_2", "Mining Speed II", 50, 2)
            object PowderBuff : Perk("powder_buff", "Powder Buff", 50, 4)
            object MiningFortune2 : Perk("mining_fortune_2", "Mining Fortune II", 50, 6)
            object LonesomeMiner : Perk("lonesome_miner", "Lonesome Miner", 45, 11)
            object Professional : Perk("professional", "Professional", 140, 12)
            object Mole : Perk("mole", "Mole", 190, 13)
            object Fortunate : Perk("fortunate", "Fortunate", 20, 14)
            object GreatExplorer : Perk("great_explorer", "Great Explorer", 20, 15)
            object GoblinKiller : Perk("goblin_killer", "Goblin Killer", 1, 20)
            object StarPowder : Perk("star_powder", "Star Powder", 1, 24)
            object SkyMall : Perk("daily_effect", "Sky Mall", 1, 28)
            object MiningMadness : Perk("mining_madness", "Mining Madness", 1, 29)
            object SeasonedMineman : Perk("mining_experience", "Seasoned Mineman", 100, 30)
            object EfficientMiner : Perk("efficient_miner", "Efficient Miner", 100, 31)
            object Orbiter : Perk("experience_orbs", "Orbiter", 80, 32)
            object FrontLoaded : Perk("front_loaded", "Front Loaded", 1, 33)
            object PrecisionMining : Perk("precision_mining", "Precision Mining", 1, 34)
            object LuckOfTheCave : Perk("random_event", "Luck of the Cave", 45, 38)
            object DailyPowder : Perk("daily_powder", "Daily Powder", 100, 40)
            object Crystallized : Perk("fallen_star_bonus", "Crystallized", 30, 42)
            object TitaniumInsanium : Perk("titanium_insanium", "Titanium Insanium", 50, 48)
            object MiningFortune : Perk("mining_fortune", "Mining Fortune", 50, 49)
            object QuickForge : Perk("forge_time", "Quick Forge", 20, 50)
            object MiningSpeed : Perk("mining_speed", "Mining Speed", 50, 58)
        }
        sealed class PickaxeAbility(id: String, name: String, slotNum: Int) : HOTMSlot(id, name, slotNum) {
            object VeinSeeker : PickaxeAbility("vein_seeker", "Vein Seeker",  10)
            object ManiacMiner : PickaxeAbility("maniac_miner", "Maniac Miner", 16)
            object MiningSpeedBoost : PickaxeAbility("mining_speed_boost", "Mining Speed Boost", 47)
            object Pickobulus : PickaxeAbility("pickaxe_toss", "Pickobulus", 51)
        }

        companion object {
            val slots = hashSetOf<HOTMSlot>()

            init {
                HOTMSlot::class.sealedSubclasses.flatMapTo(slots) { type ->
                    type.sealedSubclasses.mapNotNull { item -> item.objectInstance }
                }
            }
        }
    }

}
