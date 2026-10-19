package info.prorabka.abik.android.data.repository

import info.prorabka.abik.android.data.remote.api.GeoApi
import info.prorabka.abik.android.domain.model.City
import info.prorabka.abik.android.domain.model.Region
import info.prorabka.abik.android.domain.repository.GeoRepository
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val api: GeoApi
) : GeoRepository {
    override suspend fun getRegions(): List<Region> =
        api.getRegions().map { Region(it.id, it.name) }

    override suspend fun getCities(regionId: Long): List<City> =
        api.getCities(regionId).map { City(it.id, it.name) }
}