package info.prorabka.abik.android.presentation.screens.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.prorabka.abik.android.domain.model.Team
import info.prorabka.abik.android.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TeamsUiState {
    data object Loading : TeamsUiState
    data class Error(val message: String) : TeamsUiState
    data class Success(val teams: List<Team>) : TeamsUiState
}

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val repository: TeamRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TeamsUiState>(TeamsUiState.Loading)
    val uiState: StateFlow<TeamsUiState> = _uiState.asStateFlow()

    init {
        loadTeams()
    }

    fun loadTeams() {
        _uiState.value = TeamsUiState.Loading
        viewModelScope.launch {
            try {
                val teams = repository.getMyTeams()
                _uiState.value = TeamsUiState.Success(teams)
            } catch (e: Exception) {
                _uiState.value = TeamsUiState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }
}