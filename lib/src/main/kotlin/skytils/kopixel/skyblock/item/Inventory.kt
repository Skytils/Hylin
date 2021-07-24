package skytils.kopixel.skyblock.item

import com.google.gson.JsonObject
import skytils.kopixel.extension.*
import net.querz.nbt.io.NBTDeserializer
import net.querz.nbt.tag.CompoundTag
import java.io.ByteArrayInputStream
import java.util.*

/**
 * Represents an inventory in skyblock as a list of NBT tags.
 */
class Inventory(val name: String, var items: MutableList<InventoryItem> = mutableListOf<InventoryItem>()) {
    constructor(json: JsonObject): this("storage_backpack", json)

    constructor(name: String, data: JsonObject): this(name, data.getString("data"))

    constructor(name: String, data: String): this(name) {
        val bytes = data.toByteArray(charset("UTF-8"))
        val decodedBytes = Base64.getDecoder().decode(bytes)

        val rawTag = NBTDeserializer().fromStream(ByteArrayInputStream(decodedBytes))
        val tag = rawTag.tag as CompoundTag

        // Iterate through every item
        tag.getListTag("i").forEach {
            (it as CompoundTag).forEach { item ->
                item.value?.let { itTag->
                    if(itTag is CompoundTag) items.add(InventoryItem(itTag))
                }
            }
        }
    }

    override fun toString(): String {
        return "Inventory(name=$name,items=$items)"
    }

    inline fun forEveryItem(backpacks: Boolean = true, iterator: (InventoryItem) -> Unit) {
        items.forEach {
            iterator(it)
            if(backpacks && it.isBackpack()) forEveryBackpackItem(it, iterator)
        }
    }
    inline fun forEveryBackpackItem(backpack: InventoryItem, iterator: (InventoryItem) -> Unit) {
        backpack.backpackContents().items.forEach {
            iterator(it)
        }
    }
}