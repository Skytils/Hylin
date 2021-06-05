package com.ryanhcode.kopixel.skyblock.item

import com.ryanhcode.kopixel.skyblock.SkyblockColors
import net.querz.nbt.io.NBTDeserializer
import net.querz.nbt.tag.ByteArrayTag
import net.querz.nbt.tag.CompoundTag
import java.io.ByteArrayInputStream

class InventoryItem(val tag: CompoundTag) {
    fun isBackpack(): Boolean {
        tag.getCompoundTag("ExtraAttributes")?.let { attribute ->
            attribute.entrySet().forEach {
                if("_backpack_data" in it.key) return true
            }
        }
        return false
    }

    fun backpackContents(): Inventory {
        tag.getCompoundTag("ExtraAttributes")?.let { attribute ->
            val items = mutableListOf<InventoryItem>()
            attribute.entrySet().forEach {
                if("_backpack_data" in it.key) {
                    val byt = (it.value as ByteArrayTag).value
                    val rawTag = NBTDeserializer().fromStream(ByteArrayInputStream(byt))
                    val tag = rawTag.tag as CompoundTag
                    items += InventoryItem(tag)
                }
            }
            return Inventory("backpack", items)
        }
        error("This item is not a backpack")
    }

    override fun toString() = "Item(${tag.toString()})"

    fun colorable(): Boolean {
        tag.getCompoundTag("display")?.let { it.getIntTag("color")?.let { colorTag -> return true } }
        return false
    }
    fun color(): Int? {
        tag.getCompoundTag("display")?.let { it.getIntTag("color")?.let { colorTag -> return colorTag.asInt() } }
        return null
    }
    fun id(): String? {
        tag.getCompoundTag("ExtraAttributes")?.let {
            it.getStringTag("id")?.let { idTag ->
                val id = idTag.value.lowercase()
                return id
            }
        }
        return null
    }
    fun hexColor() = Integer.toHexString(color()!!)

    fun exotic() = try { SkyblockColors.isExotic(id()!!, color()!!) } catch(e: Exception) { false }

}