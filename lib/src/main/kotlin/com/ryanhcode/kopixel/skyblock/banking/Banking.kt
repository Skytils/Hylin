package com.ryanhcode.kopixel.skyblock.banking

import com.google.gson.JsonObject
import com.ryanhcode.kopixel.extension.converter.byExternalList
import com.ryanhcode.kopixel.extension.converter.byFloat
import com.ryanhcode.kopixel.extension.converter.byInt
import com.ryanhcode.kopixel.extension.getArray
import com.ryanhcode.kopixel.extension.getInt

/**
 * Represents a Hypixel Skyblock Profile's Bank API
 * @param json A JsonObject to construct banking data from
 */
class Banking(json: JsonObject) {
    val balance         by json.byFloat("balance")
    val transactions    by json.byExternalList<Transaction>()

    override fun toString(): String {
        return "Banking(\n\tbalance=$balance, \n\ttransactions=$transactions\n)"
    }
}
