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

package skytils.hylin.extension

import skytils.hylin.skyblock.Profile
import java.util.*

/**
 * Gets the latest profile saved out of a list of Profiles or returns `null` if the list is empty
 * @param uuid The uuid of the player
 * @return The last played Skyblock profile of the player
 */
fun List<Profile>.getLatestProfileOrNull(uuid: UUID): Profile? {
    return this.maxByOrNull {
        it.members[uuid.toString().replace("-", "")]?.lastSave?.time ?: 0L
    }
}