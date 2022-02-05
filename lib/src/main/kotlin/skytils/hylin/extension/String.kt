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

package skytils.hylin.extension

import java.util.*
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Any> String.getWithGeneric(clazz: KClass<out T>): T {
    return when (clazz) {
        String::class -> this as T
        Boolean::class -> toBooleanStrict() as T
        Int::class -> toInt() as T
        Long::class -> toLong() as T
        Float::class -> toFloat() as T
        Double::class -> toDouble() as T
        Date::class -> Date(toLong()) as T
        UUID::class -> toUUID() as T
        else -> error("Invalid generic")
    }
}