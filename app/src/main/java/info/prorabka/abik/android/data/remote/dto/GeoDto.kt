package info.prorabka.abik.android.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegionDto(
    val id: Long,
    val name: String,
    val code: String
)

@Serializable
data class CityDto(
    val id: Long,
    val regionId: Long,
    val name: String
)