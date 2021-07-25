package skytils.kopixel.skyblock.item

import com.google.gson.JsonObject
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants
import skytils.kopixel.extension.getString
import java.io.ByteArrayInputStream
import java.util.*

/**
 * Represents an inventory in skyblock as a list of NBT tags.
 */
class Inventory(val name: String, var items: MutableList<InventoryItem> = mutableListOf<InventoryItem>()) {
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
            cmpndTag.keySet.forEach { item ->
                cmpndTag.getTag(item)?.let { itTag ->
                    if (itTag is NBTTagCompound) items.add(InventoryItem(itTag))
                }
            }
        }
    }

    override fun toString(): String {
        return "Inventory(name=$name,items=$items)"
    }

    inline fun forEveryItem(iterator: (InventoryItem) -> Unit) {
        items.forEach {
            iterator(it)
        }
    }
}