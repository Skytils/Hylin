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

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants
import skytils.hylin.skyblock.SkyblockColors

class InventoryItem(val tag: NBTTagCompound) {
    override fun toString() = "Item($tag)"

    val colorable
        get() = tag.getCompoundTag("display").hasKey("color", Constants.NBT.TAG_INT)

    val color: Int?
        get() {
            val displayTag = tag.getCompoundTag("display")
            if (displayTag.hasKey("color", Constants.NBT.TAG_INT)) {
                return displayTag.getInteger("color")
            }
            return null
        }

    val id: String?
        get() {
            tag.getCompoundTag("ExtraAttributes")?.let {
                if (it.hasKey("id", Constants.NBT.TAG_STRING)) return it.getString("id")
            }
            return null
        }

    val hexColor: String
        get() = Integer.toHexString(color!!)

    val isExotic
        get() = runCatching {
            SkyblockColors.isExotic(id!!, color!!)
        }.getOrDefault(false)

}