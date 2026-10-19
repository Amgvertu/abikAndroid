package info.prorabka.abik.android.domain.repository
import info.prorabka.abik.android.domain.model.City
import info.prorabka.abik.android.domain.model.Region

interface GeoRepository {
    suspend fun getRegions(): List<Region>
    suspend fun getCities(regionId: Long): List<City>
}