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

package skytils.hylin.extension.converter

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import skytils.hylin.extension.getWithGeneric
import skytils.hylin.extension.toUUID
import java.lang.reflect.Constructor
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/*
 * The following are all delegates used to grab properties from a JSON object lazily and with little code
 */

internal inline fun <reified T> JsonObject.byExternal(key: String? = null) = this.byConverter<T>(key)
internal inline fun <reified T> JsonObject.byExternal(key: String? = null, default: T = null as T) =
    this.byConverter<T>(key, default)
internal inline fun <reified T> JsonObject.byConverter(key: String? = null) =
    JsonConverterDelegate<T>(T::class, this, key)
internal inline fun <reified T> JsonObject.byConverter(key: String? = null, default: T = null as T) =
    JsonConverterDelegate<T>(T::class, this, key, default)

internal fun JsonObject.byString(key: String? = null) = JsonPropertyDelegate(this, key, "") { it.asString }
internal fun JsonObject.byUUID(key: String? = null) = JsonPropertyDelegate(this, key, UUID.randomUUID()) { it.asString.toUUID() }
internal fun JsonObject.byInt(key: String? = null) = JsonPropertyDelegate(this, key, 0) { it.asInt }
internal fun JsonObject.byFloat(key: String? = null) = JsonPropertyDelegate(this, key, 0f) { it.asFloat }
internal fun JsonObject.byDouble(key: String? = null) = JsonPropertyDelegate(this, key, 0.0) { it.asDouble }
internal fun JsonObject.byBoolean(key: String? = null) = JsonPropertyDelegate(this, key, false) { it.asBoolean }
internal fun JsonObject.byLong(key: String? = null) = JsonPropertyDelegate(this, key, 0L) { it.asLong }
internal fun JsonObject.byDate(key: String? = null) = JsonPropertyDelegate(this, key, Date(0L)) { Date(it.asLong) }

internal inline fun <reified T> JsonObject.byExternalList(key: String? = null) = JsonPropertyDelegate(this, key, listOf()) {
    it.asJsonArray.map { element ->
        val constructor: Constructor<*> = T::class.java.getConstructor(JsonObject::class.java)
            ?: error("External lists's generics must have a proper constructor")
        constructor.newInstance(element) as T
    }.toList()
}

internal inline fun <reified T : Any> JsonObject.byList(key: String? = null) = JsonPropertyDelegate(this, key, listOf()) {
    it.asJsonArray.map { element ->
        element.getWithGeneric(T::class)
    }.toList()
}

internal inline fun <reified T> JsonObject.byExternalMap(key: String? = null, crossinline isValid: (Map.Entry<String, JsonElement>) -> Boolean = { true }) = JsonPropertyDelegate(this, key, mapOf()) {
    val map = mutableMapOf<String, T>()
    val constructor: Constructor<*> = T::class.java.getConstructor(JsonObject::class.java)
        ?: error("External map's generics must have a proper constructor")
    it.asJsonObject.entrySet().filter(isValid).forEach { entry ->
        map[entry.key] = constructor.newInstance(entry.value) as T
    }
    map.toMap()
}

internal inline fun <reified T : Any> JsonObject.byMap(key: String? = null) = JsonPropertyDelegate(this, key, mapOf()) { element ->
    element.asJsonObject.entrySet().associate {
        it.key to it.value.getWithGeneric(T::class)
    }
}

internal inline fun <reified K : Any, reified V : Any> JsonObject.byMapKeyed(key: String? = null) = JsonPropertyDelegate(this, key, mapOf()) { element ->
    element.asJsonObject.entrySet().associate {
        it.key.getWithGeneric(K::class) to it.value.getWithGeneric(V::class)
    }
}

internal fun <T : Enum<T>> JsonObject.byEnum(key: String? = null, klazz: KClass<out Enum<T>>) =
    JsonPropertyDelegate(this, key, klazz.java.enumConstants[0] as T) { j -> klazz.java.enumConstants.find { it.name.equals(j.asString, true) } as T}

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class JsonPropertyDelegate<T>(
    private val json: JsonObject,
    private val key: String?,
    private val default: T = null as T,
    private val lambda: (JsonElement) -> T
) {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        if (value != null) return value as T
        val trueKey = key ?: prop.name
        if ((!json.has(trueKey) && prop.returnType.isMarkedNullable) || json.get(trueKey) == null || json.get(trueKey).isJsonNull) return default
        val ret = lambda(json.get(trueKey))
        value = ret
        return ret
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {}
}


@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class JsonConverterDelegate<T>(private val klazz: KClass<*>, val json: JsonObject, private val key: String?, private val default: T = null as T) {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        if (value != null) return value as T
        val trueKey = key ?: prop.name
        if ((!json.has(trueKey) && prop.returnType.isMarkedNullable) || json.get(trueKey) == null || json.get(trueKey).isJsonNull) return default
        val constructor: Constructor<out Any> = klazz.java.getConstructor(JsonObject::class.java)
            ?: error("byExternal cannot be used on classes without a constructor taking a JsonObject")
        val inst = constructor.newInstance(json.getAsJsonObject(trueKey)) as T
        value = inst
        return inst
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {
        this.value = value
    }
}

