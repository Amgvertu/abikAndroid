package info.prorabka.abik.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BalanceResponseDto(
    val teamId: Long,
    val userId: Long,
    val userLastName: String? = null,
    val userFirstName: String? = null,
    val amountKopecks: Long,
    val updatedAt: String
)