package info.prorabka.abik.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateTeamRequest(
    val name: String,
    val cityId: Long,
    val homeRegionId: Long,
    val logoUrl: String,
    val description: String? = null,
    val season: String? = null
)

@Serializable
data class TeamDetailResponseDto(
    val id: Long,
    val name: String,
    val cityName: String,
    val logoUrl: String
)