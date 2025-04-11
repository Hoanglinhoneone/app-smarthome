package ptit.iot.smarthome.ui.screen.stats

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ptit.iot.smarthome.data.entity.LightEntity
import ptit.iot.smarthome.data.repository.LightRepository
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class StatsViewModel @Inject constructor(
    private val lightRepository: LightRepository,
) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val TAG = "StatsViewModel"
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _lights = MutableStateFlow<List<LightEntity>>(emptyList())
    val lights: StateFlow<List<LightEntity>> = _lights.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {

        viewModelScope.launch {
            launch {
                lightRepository.getLightsStats().collect { lightStats ->
                    val listLightReverse = lightStats.reversed()
                    Log.d(TAG, "lightStats: $lightStats")
                    _uiState.value = _uiState.value.copy(lights = lightStats)
                    val dataFake: MutableList<Pair<Int, Double>> =
                        emptyList<Pair<Int, Double>>().toMutableList()
                    for (i in 1..listLightReverse.size) {
                        dataFake.add(Pair(i, listLightReverse[i - 1].light.toDouble()))
                    }
                    _uiState.value = _uiState.value.copy(data = dataFake)
                }
            }
            launch {
                getLightsInDay()
            }

        }
    }
    suspend fun getLightsInDay() {
        val listLight = lightRepository.getLightsInDay()
        Log.d(TAG, "lightStats: $listLight")
        val dataFake: MutableList<Pair<Int, Double>> =
            emptyList<Pair<Int, Double>>().toMutableList()
        for (i in 1..listLight.size) {
            dataFake.add(Pair(i, listLight[i - 1].light.toDouble()))
        }
        _uiState.value = _uiState.value.copy(dataLightsDay = dataFake)
    }
}

data class UiState(
    val dataLightsDay: List<Pair<Int, Double>> = emptyList(),
    val data: List<Pair<Int, Double>> = emptyList(),
    val lights: List<LightEntity> = emptyList(),
)