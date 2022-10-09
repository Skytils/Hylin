package gg.skytils.hylin.mojang

import gg.skytils.hylin.util.URLasStringSerializer
import gg.skytils.hylin.util.UUIDasStringSerializer
import kotlinx.serialization.Serializable
import java.net.URL
import java.util.*

@Serializable
internal class AshconError(
    val code: Int?,
    val error: String?,
    val reason: String?
)

@Serializable
class AshconPlayer (
    @Serializable(with = UUIDasStringSerializer::class)
    val uuid: UUID,
    val username: String,
    val textures: Textures
)

@Serializable
class Textures(
    val custom: Boolean,
    val slim: Boolean,
    val skin: Texture,
    val cape: Texture
)

@Serializable
class Texture(
    @Serializable(with = URLasStringSerializer::class)
    val url: URL,
    val data: String
)