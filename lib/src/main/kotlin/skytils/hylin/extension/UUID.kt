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

import java.math.BigInteger
import java.util.*

/**
 * Convert this String to a UUID
 *
 * @return A UUID from this String
 */
fun String.toUUID(): UUID {
    try {
        val raw = replace("-", "")
        return UUID(
            BigInteger(raw.substring(0, 16), 16).toLong(),
            BigInteger(raw.substring(16), 16).toLong()
        )
    } catch (e: Exception) {
        error("Invalid UUID")
    }
}

fun UUID.nonDashedString(): String {
    return this.toString().replace("-", "")
}