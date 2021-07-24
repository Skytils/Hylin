package skytils.kopixel.extension

import java.math.BigInteger
import java.util.UUID

/**
 * Convert this String to a UUID
 *
 * @return A UUID from this String
 */
fun String.toUUID(): UUID {
    try {
        val raw = replace("-", "")
        return UUID(
            BigInteger(raw.substring(0, 16), 16).toLong(),
            BigInteger(raw.substring(16), 16).toLong()
        )
    } catch (e: Exception) {
        error("Invalid UUID")
    }
}