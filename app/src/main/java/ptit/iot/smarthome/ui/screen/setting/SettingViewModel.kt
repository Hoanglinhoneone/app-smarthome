package ptit.iot.smarthome.ui.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ptit.iot.smarthome.data.repository.ActionRepository
import ptit.iot.smarthome.data.repository.BrightnessRepository
import ptit.iot.smarthome.data.repository.LightRepository
import ptit.iot.smarthome.utils.helper.checkMaxLux
import ptit.iot.smarthome.utils.helper.checkMinLux
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val brightnessRepository: BrightnessRepository,
    private val lightRepository: LightRepository,
    private val actionRepository: ActionRepository
) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val TAG = "SettingViewModel"
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {
        _uiState.value = UiState(
            minLux = getMinLux(),
            maxLux = getMaxLux()
        )
    }

    /* **********************************************************************
     * Function
     ***********************************************************************/
    private fun getMinLux(): Float {
        Log.d(TAG, "getMinLux: ${brightnessRepository.getMinLux()}")
        return brightnessRepository.getMinLux()
    }

    private fun getMaxLux(): Float {
        Log.d(TAG, "getMaxLux: ${brightnessRepository.getMaxLux()}")
        return brightnessRepository.getMaxLux()
    }

    fun setMinLux(value: Float) {
        if (value == _uiState.value.minLux) return
        if (value.checkMinLux(_uiState.value.maxLux)) {
            brightnessRepository.setMinLux(value)
            _uiState.value = _uiState.value.copy(minLux = value)
        } else {
            _uiState.value =
                _uiState.value.copy(error = "Giới hạn mở đèn phải lớn hơn 0 và nhỏ hơn giới hạn mở đèn")
        }
    }

    fun setMaxLux(value: Float) {
        if (value == _uiState.value.maxLux) return
        if (value.checkMaxLux(_uiState.value.minLux)) {
            brightnessRepository.setMaxLux(value)
            _uiState.value = _uiState.value.copy(maxLux = value)
        } else {
            _uiState.value =
                _uiState.value.copy(error = "Giới hạn tắt đèn phải lớn hơn giới hạn mở đèn và nhỏ hơn dạng Float")
        }
    }

    fun deleteLightData() {
        viewModelScope.launch {
            lightRepository.deleteAllLights()
        }
    }

    fun deleteActionData() {
        viewModelScope.launch {
            actionRepository.deleteAllAction()
        }
    }

}

data class UiState(
    val minLux: Float = 0.0F,
    val maxLux: Float = 0.0F,
    val error: String = ""
)