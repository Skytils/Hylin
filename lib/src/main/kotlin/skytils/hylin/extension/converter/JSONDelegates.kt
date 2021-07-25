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
internal inline fun <reified T> JsonObject.byConverter(key: String? = null) =
    JsonConverterDelegate<T>(T::class, this, key)

internal fun JsonObject.byString(key: String? = null) = JsonPropertyDelegate(this, key) { it.asString }
internal fun JsonObject.byUUID(key: String? = null) = JsonPropertyDelegate(this, key) { it.asString.toUUID() }
internal fun JsonObject.byInt(key: String? = null) = JsonPropertyDelegate(this, key) { it.asInt }
internal fun JsonObject.byFloat(key: String? = null) = JsonPropertyDelegate(this, key) { it.asFloat }
internal fun JsonObject.byBoolean(key: String? = null) = JsonPropertyDelegate(this, key) { it.asBoolean }
internal fun JsonObject.byLong(key: String? = null) = JsonPropertyDelegate(this, key) { it.asLong }
internal fun JsonObject.byDate(key: String? = null) = JsonPropertyDelegate(this, key) { Date(it.asLong) }

internal inline fun <reified T> JsonObject.byExternalList(key: String? = null) = JsonPropertyDelegate(this, key) {
    it.asJsonArray.map {
        val constructor: Constructor<*> = T::class.java.getConstructor(JsonObject::class.java)
            ?: error("External lists's generics must have a proper constructor")
        constructor.newInstance(it) as T
    }.toList()
}

internal inline fun <reified T> JsonObject.byList(key: String? = null) = JsonPropertyDelegate(this, key) {
    it.asJsonArray.map {
        it.getWithGeneric<T>(T::class)
    }.toList()
}

internal inline fun <reified T> JsonObject.byExternalMap(key: String? = null) = JsonPropertyDelegate(this, key) {
    val map = mutableMapOf<String, T>()
    it.asJsonObject.entrySet().forEach {
        val constructor: Constructor<*> = T::class.java.getConstructor(JsonObject::class.java)
            ?: error("External map's generics must have a proper constructor")
        map.put(it.key, constructor.newInstance(it.value) as T)
    }
    map.toMap()
}

internal inline fun <reified T> JsonObject.byMap(key: String? = null) = JsonPropertyDelegate(this, key) { element ->
    val map = mutableMapOf<String, T>()
    element.asJsonObject.entrySet().forEach { map[it.key] = it.value.getWithGeneric(T::class) as T }
    map.toMap()
}

internal fun <T : Enum<T>> JsonObject.byEnum(key: String? = null, klazz: KClass<out Enum<T>>) =
    JsonPropertyDelegate<T>(this, key) { java.lang.Enum.valueOf(klazz.java, it.asString) as T }

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class JsonPropertyDelegate<T>(
    private val json: JsonObject,
    private val key: String?,
    private val lambda: (JsonElement) -> T
) {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        if (value != null) return value as T
        val trueKey = key ?: prop.name
        if ((!json.has(trueKey) && prop.returnType.isMarkedNullable) || json.get(trueKey).isJsonNull) return null as T
        val ret = lambda(json.get(trueKey))
        value = ret
        return ret
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {}
}


@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
class JsonConverterDelegate<T>(private val klazz: KClass<*>, val json: JsonObject, private val key: String?) {
    private var value: T? = null
    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        if (value != null) return value as T
        val trueKey = key ?: prop.name
        if ((!json.has(trueKey) && prop.returnType.isMarkedNullable) || json.get(trueKey) == null || json.get(trueKey).isJsonNull) return null as T
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

