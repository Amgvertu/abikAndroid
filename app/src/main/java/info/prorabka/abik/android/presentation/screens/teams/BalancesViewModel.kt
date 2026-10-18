package info.prorabka.abik.android.presentation.screens.teams

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.prorabka.abik.android.domain.model.TeamBalance
import info.prorabka.abik.android.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface BalancesUiState {
    data object Loading : BalancesUiState
    data class Error(val message: String) : BalancesUiState
    data class Success(val balances: List<TeamBalance>) : BalancesUiState
}

@HiltViewModel
class BalancesViewModel @Inject constructor(
    private val repository: TeamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val teamId: Long = savedStateHandle["teamId"] ?: 0L

    private val _uiState = MutableStateFlow<BalancesUiState>(BalancesUiState.Loading)
    val uiState: StateFlow<BalancesUiState> = _uiState.asStateFlow()

    init {
        loadBalances()
    }

    fun loadBalances() {
        if (teamId == 0L) {
            _uiState.value = BalancesUiState.Error("Неверный ID команды")
            return
        }

        _uiState.value = BalancesUiState.Loading
        viewModelScope.launch {
            try {
                val balances = repository.getBalances(teamId)
                _uiState.value = BalancesUiState.Success(balances)
            } catch (e: Exception) {
                _uiState.value = BalancesUiState.Error(e.message ?: "Ошибка загрузки балансов")
            }
        }
    }
}