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

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*
import kotlin.reflect.KClass
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/**
 * Get a JSON Primitive of type T
 * @param key Key/name of property
 * @return The property of type T
 */
@Suppress("UNCHECKED_CAST")
fun <T> JsonObject.getWithGeneric(key: String, clazz: KClass<*>): T {
    return when (clazz) {
        String::class -> getString(key) as T
        Boolean::class -> getBoolean(key) as T
        Int::class -> getInt(key) as T
        Long::class -> getLong(key) as T
        Date::class -> Date(getLong(key)) as T
        UUID::class -> getString(key).toUUID() as T
        JsonObject::class -> getJsonObject(key) as T
        List::class -> getArray(key).map { it.asString }.toList() as T
        else -> error("Invalid generic")
    }
}

/**
 * Get this as a JSON Primitive of type T
 * @return This property of type T
 */
@OptIn(ExperimentalTime::class)
@Suppress("UNCHECKED_CAST")
fun <T : Any> JsonElement.getWithGeneric(clazz: KClass<out T>): T {
    return when (clazz) {
        String::class -> asString as T
        Boolean::class -> asBoolean as T
        Int::class -> asInt as T
        Long::class -> asLong as T
        Float::class -> asFloat as T
        Double::class -> asDouble as T
        Date::class -> Date(asLong) as T
        UUID::class -> asString.toUUID() as T
        JsonObject::class -> asJsonObject as T
        Duration::class -> Duration.milliseconds(asLong) as T
        else -> error("Invalid generic")
    }
}


/**
 * Get a string with a key
 * @param key Key/name of property
 */
fun JsonObject.getString(key: String): String = this[key].asString

/**
 * Get a string with a key and a default
 * @param key Key/name of property
 * @param default fallback value, defaults to an empty string
 */
fun JsonObject.getOptionalString(key: String, default: String = ""): String = this[key]?.asString ?: default

/**
 * Get an int with a key
 * @param key Key/name of property
 */
fun JsonObject.getInt(key: String): Int = this[key].asInt

/**
 * Get a long with a key
 * @param key Key/name of property
 */
fun JsonObject.getLong(key: String): Long = this[key].asLong

/**
 * Get a float with a key
 * @param key Key/name of property
 */
fun JsonObject.getFloat(key: String): Float = this[key].asFloat

/**
 * Get a double with a key
 * @param key Key/name of property
 */
fun JsonObject.getDouble(key: String): Double = this[key].asDouble


/**
 * Get an array with a key
 * @param key Key/name of property
 */
fun JsonObject.getArray(key: String): JsonArray = this[key].asJsonArray

/**
 * Get a boolean with a key
 * @param key Key/name of property
 */
fun JsonObject.getBoolean(key: String): Boolean = this[key].asBoolean

/**
 * Get a JsonObject with a key
 * @param key Key/name of property
 */
fun JsonObject.getJsonObject(key: String): JsonObject = this[key].asJsonObject


/**
 * Get a JsonObject at the end of a path, split by periods or /
 * @param path Path to desired JsonObject
 */
fun JsonObject.path(path: String): JsonObject {
    val split = path.split(".", "/")
    var currentObject = this

    split.forEach { key ->
        currentObject = currentObject.getJsonObject(key)
    }
    return currentObject
}

