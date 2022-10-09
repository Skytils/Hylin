package gg.skytils.hylin.hypixel

import kotlinx.serialization.Serializable

@Serializable
abstract class HypixelResponse {
    abstract val success: Boolean
}