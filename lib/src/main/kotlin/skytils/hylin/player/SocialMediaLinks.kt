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
import skytils.hylin.extension.getJsonObject
import skytils.hylin.extension.getString

/**
 * Represents a Player's social media links
 * @param socials A JsonObject to construct links from
 */
class SocialMediaLinks(socials: JsonObject) {

    val youtube: String?
    val discord: String?
    val instagram: String?
    val twitch: String?
    val twitter: String?
    val forums: String?

    init {
        if (!socials.has("links")) {
            youtube = null
            discord = null
            instagram = null
            twitch = null
            twitter = null
            forums = null

        } else {
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
