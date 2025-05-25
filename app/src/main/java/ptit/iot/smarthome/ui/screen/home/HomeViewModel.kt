package ptit.iot.smarthome.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.data.repository.ActionRepository
import ptit.iot.smarthome.utils.Action
import ptit.iot.smarthome.utils.ActionState
import ptit.iot.smarthome.utils.helper.getTimeStamp
import ptit.iot.smarthome.utils.model.ColorLed
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: FirebaseDatabase,
    private val actionRepository: ActionRepository
) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val TAG = "HomeViewModel"
    private val _uiSate = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiSate.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {
        Log.i(TAG, "init: $TAG")
        getColorLed()
    }

    /* **********************************************************************
     * Private Function
     ***********************************************************************/
    private fun getColorLed() {
        Log.i(TAG, "getColorLed")
        database.getReference("control").child("led_rgb").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    Log.i(TAG, "getColorLed: ${it.value.toString()}")
                    _uiSate.update { value ->
                        value.copy(colorLed = when (it.value.toString()) {
                        ColorLed.YELLOW.rgb -> ColorLed.YELLOW
                        ColorLed.RED.rgb -> ColorLed.RED
                        ColorLed.GREEN.rgb -> ColorLed.GREEN
                        ColorLed.BLUE.rgb -> ColorLed.BLUE
                            else -> {ColorLed.YELLOW}
                        }) }
                }
            }
            .addOnFailureListener {
                Log.i(TAG, "getColorLed: ${it.message}")
                it.printStackTrace()
                _uiSate.value = _uiSate.value.copy(error = it.message.toString())
            }
    }

    /* **********************************************************************
     * Public Function
     ***********************************************************************/
    fun updateColor(colorLed: ColorLed) {
        val actionColor = when (colorLed) {
            ColorLed.YELLOW -> Action.CHANGE_COLOR_YELLOW
            ColorLed.RED -> Action.CHANGE_COLOR_RED
            ColorLed.GREEN -> Action.CHANGE_COLOR_GREEN
            ColorLed.BLUE -> Action.CHANGE_COLOR_BLUE
        }
        Log.i(TAG, "updateColor: $colorLed")
        _uiSate.value = _uiSate.value.copy(loading = true, colorLed = colorLed)
        database.getReference("control").child("led_rgb").setValue(colorLed.rgb)
            .addOnSuccessListener {
                _uiSate.value = _uiSate.value.copy(success = true, loading = false)
                viewModelScope.launch {
                    actionRepository.insertHistory(
                        ActionEntity(
                            timestamp = getTimeStamp(),
                            action = actionColor.name,
                            state = ActionState.SUCCESS.name
                        )
                    )
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                _uiSate.value = _uiSate.value.copy(error = it.message.toString(), loading = false)
            }
    }
}

data class UiState(
    val colorLed: ColorLed = ColorLed.YELLOW,
    val error: String = "",
    val loading: Boolean = false,
    val success: Boolean = false
)