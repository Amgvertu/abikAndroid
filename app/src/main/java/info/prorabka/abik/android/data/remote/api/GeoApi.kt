package info.prorabka.abik.android.data.remote.api

import info.prorabka.abik.android.data.remote.dto.CityDto
import info.prorabka.abik.android.data.remote.dto.RegionDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GeoApi {
    @GET("v1/geo/regions")
    suspend fun getRegions(): List<RegionDto>

    @GET("v1/geo/regions/{regionId}/cities")
    suspend fun getCities(@Path("regionId") regionId: Long): List<CityDto>
}