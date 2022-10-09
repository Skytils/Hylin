package gg.skytils.hylin.player

import gg.skytils.hylin.hypixel.HypixelResponse
import gg.skytils.hylin.util.UUIDasStringSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
class PlayerResponse (
    override val success: Boolean
): HypixelResponse()

@Serializable
class Player(
    val _id: String,
    val displayName: String,
    @Serializable(with = UUIDasStringSerializer::class)
    val uuid: UUID,
    val firstLogin: Long,
    @SerialName("rank")
    val specialRank: SpecialRank? = null,
    val monthlyPackageRank: MonthlyPackageRank? = null,
    val packageRank: PackageRank? = null,
    val achievements: Map<String, Int>
) {
    val rank by lazy {
        if (packageRank == PackageRank.MVP_PLUS && monthlyPackageRank == MonthlyPackageRank.SUPERSTAR) {
            return@lazy PackageRank.MVP_PLUS_PLUS
        }
        return@lazy packageRank ?: PackageRank.NONE
    }
}

