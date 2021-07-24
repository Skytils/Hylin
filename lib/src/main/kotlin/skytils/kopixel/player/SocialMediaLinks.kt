package skytils.kopixel.player

import com.google.gson.JsonObject
import skytils.kopixel.extension.getJsonObject
import skytils.kopixel.extension.getString

/**
 * Represents a Player's social media links
 * @param links A JsonObject to construct links from
 */
class SocialMediaLinks(socials: JsonObject) {

    val youtube: String?
    val discord: String?
    val instagram: String?
    val twitch: String?
    val twitter: String?
    val forums: String?

    init {
        if(!socials.has("links")){
            youtube = null
            discord = null
            instagram = null
            twitch = null
            twitter = null
            forums = null

        }else {
            val links = socials.getJsonObject("links")
            youtube = if (links.has("YOUTUBE")) links.getString("YOUTUBE") else null
            discord = if (links.has("DISCORD")) links.getString("DISCORD") else null
            instagram = if (links.has("INSTAGRAM")) links.getString("INSTAGRAM") else null
            twitch = if (links.has("TWITCH")) links.getString("TWITCH") else null
            twitter = if (links.has("TWITTER")) links.getString("TWITTER") else null
            forums = if (links.has("HYPIXEL")) links.getString("HYPIXEL") else null
        }
    }

    override fun toString(): String {
        return "SocialMediaLinks(youtube=$youtube, discord=$discord, instagram=$instagram, twitch=$twitch, twitter=$twitter, forums=$forums)"
    }
}
