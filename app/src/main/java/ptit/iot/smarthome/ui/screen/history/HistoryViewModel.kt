package ptit.iot.smarthome.ui.screen.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.data.entity.LightEntity
import ptit.iot.smarthome.data.repository.ActionRepository
import ptit.iot.smarthome.data.repository.LightRepository
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val lightRepository: LightRepository,
    private val actionRepository: ActionRepository
) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {
        viewModelScope.launch {
            lightRepository.getLights().collect{ listLight ->
                _uiState.value =  _uiState.value.copy(lights = listLight)
            }
        }
        getData()
    }

    /* **********************************************************************
     * Private Function
     ***********************************************************************/
    private fun getData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                actions = actionRepository.getActions()
            )
        }
    }
}

data class HistoryUiState(
    val lights: List<LightEntity> = emptyList(),
    val actions: List<ActionEntity> = emptyList()
)