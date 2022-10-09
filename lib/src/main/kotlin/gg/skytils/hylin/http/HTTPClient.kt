package gg.skytils.hylin.http

import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

interface HTTPClient {
    fun getInputStream(url: String): InputStream
}

internal class HTTPClientImpl: HTTPClient {
    override fun getInputStream(url: String): InputStream {
        val connection = URL(url).openConnection()
        (connection as? HttpURLConnection)?.run {
            requestMethod = "GET"
        }
        return connection.getInputStream()
    }
}