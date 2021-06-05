package com.ryanhcode.kopixel.request

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.ryanhcode.kopixel.KoPixelAPI
import com.ryanhcode.kopixel.extension.getBoolean
import com.ryanhcode.kopixel.extension.getString
import java.net.URL
import kotlin.system.measureTimeMillis
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.util.zip.GZIPInputStream


/**
 * Handler for API connections and reading JSON
 */
class ConnectionHandler {

    /**
     * Reads an endpoint and parses as JSON
     *
     * @param endpoint Endpoint relative to the Hypixel API
     * @return A JsonObject of the parsed result
     */
    fun readJSON(endpoint: String): JsonObject {
        val url = URL(endpoint)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.setRequestProperty("Accept-Encoding", "gzip")
        val reader: Reader = if ("gzip" == con.contentEncoding) {
            InputStreamReader(GZIPInputStream(con.inputStream))
        } else {
            InputStreamReader(con.inputStream)
        }

        try {
            val parsed = JsonParser.parseReader(reader).asJsonObject
            return parsed
        } catch (e: JsonParseException) {
            error("Error caught during JSON parsing from \"${KoPixelAPI.endpoint}/$endpoint\"")
        } catch (e: JsonSyntaxException) {
            error("Error caught in JSON syntax from \"${KoPixelAPI.endpoint}/$endpoint\"")
        }
    }

    fun hypixelJSON(endpoint: String): JsonObject {
        val readJSON = readJSON(endpoint)
        if(!readJSON.getBoolean("success")) {
            throw HypixelAPIException(endpoint, readJSON.getString("error"))
        }
        return readJSON
    }

}