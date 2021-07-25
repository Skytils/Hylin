package skytils.kopixel.extension

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.*
import kotlin.reflect.KClass

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
@Suppress("UNCHECKED_CAST")
fun <T> JsonElement.getWithGeneric(clazz: KClass<*>): T {
    return when (clazz) {
        String::class -> asString as T
        Boolean::class -> asBoolean as T
        Int::class -> asInt as T
        Long::class -> asLong as T
        Date::class -> Date(asLong) as T
        UUID::class -> asString.toUUID() as T
        JsonObject::class -> asJsonObject as T
        else -> error("Invalid generic")
    }
}


/**
 * Get a string with a key
 * @param key Key/name of property
 */
fun JsonObject.getString(key: String): String = this.get(key).asString

/**
 * Get an int with a key
 * @param key Key/name of property
 */
fun JsonObject.getInt(key: String): Int = this.get(key).asInt

/**
 * Get a long with a key
 * @param key Key/name of property
 */
fun JsonObject.getLong(key: String): Long = this.get(key).asLong

/**
 * Get a float with a key
 * @param key Key/name of property
 */
fun JsonObject.getFloat(key: String): Float = this.get(key).asFloat


/**
 * Get an array with a key
 * @param key Key/name of property
 */
fun JsonObject.getArray(key: String): JsonArray = this.get(key).asJsonArray

/**
 * Get a boolean with a key
 * @param key Key/name of property
 */
fun JsonObject.getBoolean(key: String): Boolean = this.get(key).asBoolean

/**
 * Get a JsonObject with a key
 * @param key Key/name of property
 */
fun JsonObject.getJsonObject(key: String): JsonObject = this.get(key).asJsonObject


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

