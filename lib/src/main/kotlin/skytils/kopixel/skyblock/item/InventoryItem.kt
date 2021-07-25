package skytils.kopixel.skyblock.item

import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.Constants
import skytils.kopixel.skyblock.SkyblockColors

class InventoryItem(val tag: NBTTagCompound) {
    override fun toString() = "Item($tag)"

    fun colorable(): Boolean {
        return tag.getCompoundTag("display").hasKey("color", Constants.NBT.TAG_INT)
    }

    fun color(): Int? {
        val displayTag = tag.getCompoundTag("display")
        if (displayTag.hasKey("color", Constants.NBT.TAG_INT)) {
            return displayTag.getInteger("color")
        }
        return null
    }

    fun id(): String? {
        tag.getCompoundTag("ExtraAttributes")?.let {
            if (it.hasKey("id", Constants.NBT.TAG_STRING)) return it.getString("id")
        }
        return null
    }

    fun hexColor() = Integer.toHexString(color()!!)

    fun exotic() = try {
        SkyblockColors.isExotic(id()!!, color()!!)
    } catch (e: Exception) {
        false
    }

}