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

@Deprecated("No longer uses save time", ReplaceWith("List<Profile>.getSelectedSkyblockProfile(uuid: UUID)"),level = DeprecationLevel.HIDDEN)
fun List<Profile>.getLatestSkyblockProfile(uuid: UUID) = this.firstOrNull(Profile::selected)

fun List<Profile>.getSelectedSkyblockProfile(uuid: UUID) = this.firstOrNull(Profile::selected)