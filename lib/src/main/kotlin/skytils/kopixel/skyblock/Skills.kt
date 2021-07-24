package skytils.kopixel.skyblock

import com.google.gson.JsonObject
import skytils.kopixel.extension.*

/**
 * Represents all skills of a Member
 * @param json A JsonObject to construct data from
 */
class Skills(private val json: JsonObject) {
    val miningXP = skill("mining")
    val combatXP = skill("combat")
    val foragingXP = skill("foraging")
    val tamingXP = skill("taming")
    val alchemyXP = skill("alchemy")
    val farmingXP = skill("farming")
    val enchantingXP = skill("enchanting")
    val fishingXP = skill("fishing")
    val carpentryXP = skill("carpentry")
    val runecraftingXP = skill("runecrafting")

    fun skill(s: String): Float? = if(json.has("experience_skill_$s")) json.getFloat("experience_skill_$s") else null
}
