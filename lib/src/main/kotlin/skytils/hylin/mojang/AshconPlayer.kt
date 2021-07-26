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

package skytils.hylin.mojang

import com.google.gson.JsonObject
import org.apache.commons.lang3.time.DateFormatUtils
import skytils.hylin.extension.converter.*
import skytils.hylin.extension.converter.byBoolean
import skytils.hylin.extension.converter.byExternalList
import skytils.hylin.extension.converter.byString
import skytils.hylin.extension.converter.byUUID
import skytils.hylin.extension.getJsonObject
import skytils.hylin.extension.getString
import java.util.Date

class AshconPlayer(json: JsonObject) {
    val uuid by json.byUUID()
    val username by json.byString()
    val usernameHistory: List<NameChange> by json.byExternalList("username_history")

    val textures by json.byExternal<TextureData>()

    val createdAt: Date? by lazy {
        val createdDate by json.byString("created_at")
        if (createdDate == null) return@lazy null
        DateFormatUtils.ISO_DATE_FORMAT.parse(createdDate)
    }

    class NameChange(json: JsonObject) {
        val username by json.byString()
        val changedAt: Date? by lazy {
            val completeDate: String? by json.byString("changed_at")
            if (completeDate == null) return@lazy null
            return@lazy DateFormatUtils.ISO_DATETIME_FORMAT.parse(completeDate)
        }
    }

    class TextureData(json: JsonObject) {
        val slim by json.byBoolean()
        val custom by json.byBoolean()

        private val skinObj = json.getJsonObject("skin")
        val skinUrl by skinObj.byString("url")
        val skinData by skinObj.byString("data")

        private val capeObj by lazy {
            if (!json.has("cape")) return@lazy null
            return@lazy json.getJsonObject("cape")
        }
        val capeUrl by lazy {
            if (capeObj == null) return@lazy null
            val url by capeObj!!.byString("url")
            return@lazy url
        }
        val capeData by lazy {
            if (capeObj == null) return@lazy null
            val data by capeObj!!.byString("data")
            return@lazy data
        }

        private val rawObj = json.getJsonObject("raw")
        val rawData = rawObj.getString("data")
        val rawSignature = rawObj.getString("signature")

    }
}