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

import com.google.gson.JsonObject
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonObjectTest {
    @Test
    fun path() {
        val obj = JsonObject()
        val foo = JsonObject()
        val bar = JsonObject()
        bar.addProperty("name", "baz")
        foo.add("bar", bar)
        obj.add("foo", foo)

        assertEquals(obj.path("foo/bar").getString("name"), "baz")
    }

}