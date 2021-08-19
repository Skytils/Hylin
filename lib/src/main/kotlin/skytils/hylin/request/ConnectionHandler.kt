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

package skytils.hylin.request

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import skytils.hylin.extension.getBoolean
import skytils.hylin.extension.getString


/**
 * Handler for API connections and reading JSON
 */
class ConnectionHandler {

    val parser = JsonParser()

    /**
     * Reads an endpoint and parses as JSON
     *
     * @param endpoint Endpoint relative to the Hypixel API
     * @return A JsonObject of the parsed result
     */
    fun readJSON(endpoint: String): JsonObject {
        try {
            return parser.parse(HttpClients.createDefault().use {
                HttpGet(endpoint).run {
                    addHeader("User-Agent", "Hylin/1.0.0")
                    it.execute(this)
                }.use {
                    EntityUtils.toString(it.entity, Charsets.UTF_8).apply {
                        EntityUtils.consume(it.entity)
                    }
                }
            }).asJsonObject
        } catch (e: JsonParseException) {
            error("Error caught during JSON parsing from \"$endpoint\"")
        } catch (e: JsonSyntaxException) {
            error("Error caught in JSON syntax from \"$endpoint\"")
        }
    }

    fun hypixelJSON(endpoint: String): JsonObject {
        val readJSON = readJSON(endpoint)
        if (!readJSON.getBoolean("success")) {
            throw HypixelAPIException(endpoint, readJSON.getString("cause"))
        }
        return readJSON
    }

}