package info.prorabka.abik.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TeamResponseDto(
    val id: Long,
    val name: String,
    val cityId: Long,
    val cityName: String,
    val homeRegionId: Long,
    val homeRegionName: String,
    val logoUrl: String,
    val status: String,
    val createdAt: String
)