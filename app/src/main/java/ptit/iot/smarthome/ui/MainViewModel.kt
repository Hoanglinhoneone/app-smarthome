package ptit.iot.smarthome.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.data.entity.LightEntity
import ptit.iot.smarthome.data.repository.ActionRepository
import ptit.iot.smarthome.data.repository.BrightnessRepository
import ptit.iot.smarthome.data.repository.LightRepository
import ptit.iot.smarthome.data.repository.ThemeRepository
import ptit.iot.smarthome.utils.Action
import ptit.iot.smarthome.utils.ActionState
import ptit.iot.smarthome.utils.helper.getCurrentDateTime
import ptit.iot.smarthome.utils.helper.getTimeStamp
import ptit.iot.smarthome.utils.helper.handleVoice
import ptit.iot.smarthome.utils.helper.stt.SpeechEngineListener
import ptit.iot.smarthome.utils.helper.stt.SpeechToTextManager
import ptit.iot.smarthome.utils.model.ColorLed
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val database: FirebaseDatabase,
    private val speechToTextManager: SpeechToTextManager,
    private val brightnessRepository: BrightnessRepository,
    private val themeRepository: ThemeRepository,
    private val lightRepository: LightRepository,
    private val actionRepository: ActionRepository

) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val TAG = "MainViewModel"
    private var tickerJob: Job? = null

    private val _uiSate = MutableStateFlow(UiSate())
    val uiState: StateFlow<UiSate> = _uiSate.asStateFlow()

    private val _isDynamic = MutableStateFlow(false)
    val isDynamic: StateFlow<Boolean> = _isDynamic.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {
        Log.i(TAG, "Init MainViewModel")
        getDynamicTheme()
        startReadData()
        getStateLight()
        speechToTextManager.initSpeechRecognizer()
    }

    /* **********************************************************************
     * Private Function
     ***********************************************************************/
    fun startListening() {
        speechToTextManager.onStartListening(object : SpeechEngineListener {
            override fun onStartListening(isListening: Boolean) {
                Log.d(TAG, "onStartListening")
                if(isListening) _uiSate.update { it.copy(isListening = true) }
            }

            override fun onBeginningOfSpeech() {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(error: String) {
                _uiSate.update { it.copy(error = error) }
            }

            override fun onResults(results: String) {
                Log.d(TAG, "onResults")
                _uiSate.update { it.copy(isListening = false) }
                if (results.isNotEmpty()) {
                    Log.d(TAG, "results: $results")
                    val actionValue = handleVoice(results)
                    when (actionValue.lowercase()) {
                        "bật" -> if (!uiState.value.isLightOn) updateLight(true)
                        "tắt" -> if (uiState.value.isLightOn) updateLight(false)
                        "ngủ" -> changeColor(color = ColorLed.YELLOW)
                        "khách" -> changeColor(color = ColorLed.RED)
                        "ăn" -> changeColor(color = ColorLed.GREEN)
                        "bếp" -> changeColor(color = ColorLed.GREEN)
                        "tắm" -> changeColor(color = ColorLed.BLUE)
                        "nothing" -> Log.d(TAG, "nothing")
                    }
                } else {
                    Log.d(TAG, "Chưa nghe thấy")
                }
            }

            override fun onReadyForSpeech() {
            }

        })
    }

    fun stopListening() {
        Log.d(TAG, "stopListening")
        _uiSate.update { it.copy(isListening = false) }
        speechToTextManager.onStopListening()
    }
    private fun changeColor(color: ColorLed) {
        _uiSate.update { it.copy(colorChange = color) }
    }

    private fun getStateLight() {
        viewModelScope.launch {
            database.getReference("control").child("led").get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        Log.d(TAG, "get state light success light is: ${it.value}")
                        _uiSate.update { uiState ->
                            uiState.copy(
                                isLightOn = if (it.value.toString() == "ON") true else false
                            )
                        }
                    } else {
                        Log.d(TAG, "get state light success failure: null")
                        _uiSate.update { uiState ->
                            uiState.copy(isLightOn = false)
                        }
                    }
                }
        }
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun startReadData() {
        Log.i(TAG, "startReadData")
        stopReadData()
        Log.d(TAG, "tickerJob : $tickerJob")
        tickerJob = viewModelScope.launch {
            ticker(delayMillis = 3000).consumeAsFlow().collect {
                Log.i(TAG, "startReadData: ")
                readData()
            }
        }
    }

    private fun stopReadData() {
        tickerJob?.cancel()
        tickerJob = null
    }

    @SuppressLint("NewApi")
    private fun readData() {
        Log.i(TAG, "readData:")
        _uiSate.update { it.copy(isLoading = true) }
        database.getReference("sensorData").child("light").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    _uiSate.update { uiState ->
                        uiState.copy(
                            isLoading = false,
                            light = it.value.toString().toFloat()
                        )
                    }
                    viewModelScope.launch {
                        Log.d(TAG, "insert light: ${it.value}")
                        lightRepository.insertLight(
                            LightEntity(
                                timestamp = getTimeStamp(),
                                light = it.value.toString().toFloat(),
                                type = when(uiState.value.colorChange) {
                                    ColorLed.YELLOW -> "Đèn phòng ngủ"
                                    ColorLed.RED -> "Đèn phòng khách"
                                    ColorLed.GREEN -> "Đèn phòng bếp"
                                    ColorLed.BLUE -> "Đèn nhà tắm"
                                    else -> "Đèn phòng ngủ"
                                }
                            )
                        )
                    }
                    Log.d(TAG, "light: ${it.value}")
                } else {
                    Log.d(TAG, "light: null")
                    _uiSate.update { uiState ->
                        uiState.copy(isLoading = false, light = 0.0F)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "get light: fail")
                _uiSate.update { uiState ->
                    uiState.copy(isLoading = false, error = it.message.toString())
                }
            }
    }

    /* **********************************************************************
     * Public Function
     ***********************************************************************/
    private fun getDynamicTheme() {
        _isDynamic.value = themeRepository.getDynamicTheme()
    }

    fun setDynamicTheme(isDynamic: Boolean) {
        themeRepository.setDynamicTheme(isDynamic)
        _isDynamic.value = isDynamic
    }

    fun updateLight(stateLight: Boolean) {
        val lightAction = if (stateLight) "ON" else "OFF"
        _uiSate.update { it.copy(isLoading = true, isLightOn = stateLight) }
        database.getReference("control").child("led").setValue(lightAction)
            .addOnSuccessListener {
                _uiSate.update { uiState ->
                    uiState.copy(isLoading = false)
                }
                Log.d(TAG, "update light: success")
                viewModelScope.launch {
                    actionRepository.insertHistory(
                        ActionEntity(
                            timestamp = getTimeStamp(),
                            action = lightAction,
                            state = ActionState.SUCCESS.name
                        )
                    )
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "update light: fail")
                _uiSate.update { uiState ->
                    uiState.copy(
                        isLoading = false,
                        isLightOn = !stateLight,
                        error = it.message.toString()
                    )
                }
                viewModelScope.launch {
                    actionRepository.insertHistory(
                        ActionEntity(
                            timestamp = getTimeStamp(),
                            action = lightAction,
                            state = ActionState.FAIL.name
                        )
                    )
                }

            }
    }

    fun updateAutoMode(isAuto: Boolean) {
        _uiSate.update { it.copy(isAutoMode = isAuto) }
//        historyRepository.insertHistory(HistoryEntity(0, "", "", HistoryAction.AUTO_ON.name))
        Log.d(TAG, "update auto mode: $isAuto")
    }

    fun autoMode() {
        uiState.value.light.let { light ->
            uiState.value.isLightOn.let { isLightOn ->
                if (light < brightnessRepository.getMinLux() && !isLightOn) {
                    updateLight(true)
                    Log.d(TAG, "auto mode:  Light ON with light: $light")
                    Log.d(TAG, "auto mode:  minLux: ${brightnessRepository.getMinLux()}")
                }
                if (light > brightnessRepository.getMaxLux() && isLightOn) {
                    updateLight(false)
                    Log.d(TAG, "auto mode: Light OFF with light: $light")
                    Log.d(TAG, "auto mode: maxLux: ${brightnessRepository.getMaxLux()}")
                }
            }
        }
    }


    data class UiSate(
        val colorChange: ColorLed = ColorLed.YELLOW,
        val light: Float = 0.0F,
        val isLightOn: Boolean = false,
        val isAutoMode: Boolean = false,
        val color: String = "",
        val error: String = "",
        val isLoading: Boolean = false,
        val isListening: Boolean = false,
    )
}

interface LightColorHandle {
    data class onColorChanged(val color: Color)
}