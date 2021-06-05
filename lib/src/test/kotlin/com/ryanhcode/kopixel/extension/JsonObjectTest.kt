package com.ryanhcode.kopixel.extension

import com.google.gson.JsonObject
import org.junit.Test

import org.junit.Assert.*

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