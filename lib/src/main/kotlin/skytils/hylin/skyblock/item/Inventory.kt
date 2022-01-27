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

package skytils.hylin.skyblock.item

import com.google.gson.JsonObject
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants
import skytils.hylin.extension.getString
import java.io.ByteArrayInputStream
import java.util.*

/**
 * Represents an inventory in skyblock as a list of NBT tags.
 */
class Inventory(val name: String, var items: MutableList<InventoryItem?> = mutableListOf()) {
    constructor(json: JsonObject) : this("storage_backpack", json)

    constructor(name: String, data: JsonObject) : this(name, data.getString("data"))

    constructor(name: String, data: String) : this(name) {
        val bytes = data.toByteArray(charset("UTF-8"))
        val decodedBytes = Base64.getDecoder().decode(bytes)

        val tag = CompressedStreamTools.readCompressed(ByteArrayInputStream(decodedBytes))

        // Iterate through every item
        val list = tag.getTagList("i", Constants.NBT.TAG_COMPOUND)
        for (i in 0 until list.tagCount()) {
            val cmpndTag = list.getCompoundTagAt(i)
            if (cmpndTag.hasNoTags()) items.add(null)
            else items.add(InventoryItem(cmpndTag))
        }
    }

    override fun toString(): String {
        return "Inventory(name=$name,items=$items)"
    }

    inline fun forEveryItem(iterator: (InventoryItem) -> Unit) {
        items.forEach {
            if (it != null) iterator(it)
        }
    }
}