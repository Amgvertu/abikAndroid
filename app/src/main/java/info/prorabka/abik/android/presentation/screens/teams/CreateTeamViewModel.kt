package info.prorabka.abik.android.presentation.screens.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.prorabka.abik.android.data.remote.dto.CreateTeamRequest
import info.prorabka.abik.android.domain.model.City
import info.prorabka.abik.android.domain.model.Region
import info.prorabka.abik.android.domain.repository.GeoRepository
import info.prorabka.abik.android.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CreateTeamUiState(
    val regions: List<Region> = emptyList(),
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class CreateTeamViewModel @Inject constructor(
    private val geoRepository: GeoRepository,
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTeamUiState())
    val uiState: StateFlow<CreateTeamUiState> = _uiState.asStateFlow()

    init {
        loadRegions()
    }

    private fun loadRegions() {
        viewModelScope.launch {
            try {
                val regions = geoRepository.getRegions()
                _uiState.value = _uiState.value.copy(regions = regions)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Ошибка загрузки регионов")
            }
        }
    }

    fun onRegionSelected(regionId: Long) {
        viewModelScope.launch {
            try {
                val cities = geoRepository.getCities(regionId)
                _uiState.value = _uiState.value.copy(cities = cities)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = "Ошибка загрузки городов")
            }
        }
    }

    fun createTeam(name: String, regionId: Long, cityId: Long, logoUrl: String, description: String, season: String) {
        if (name.isBlank() || cityId == 0L || regionId == 0L) {
            _uiState.value = _uiState.value.copy(error = "Заполните обязательные поля")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            try {
                val request = CreateTeamRequest(
                    name = name,
                    cityId = cityId,
                    homeRegionId = regionId,
                    logoUrl = logoUrl.ifBlank { "https://example.com/logo.png" },
                    description = description.ifBlank { null },
                    season = season.ifBlank { null }
                )
                teamRepository.createTeam(request)
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message ?: "Ошибка создания")
            }
        }
    }
}