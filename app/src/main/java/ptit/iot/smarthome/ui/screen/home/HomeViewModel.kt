package ptit.iot.smarthome.ui.screen.home

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ptit.iot.smarthome.utils.model.ColorLed
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val database: FirebaseDatabase
) : ViewModel() {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val _uiSate = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiSate.asStateFlow()

    /* **********************************************************************
     * Init
     ***********************************************************************/
    init {
        getColorLed()
    }

    /* **********************************************************************
     * Private Function
     ***********************************************************************/
    private fun getColorLed() {
        database.getReference("control").child("colorLed").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    when (it.value.toString()) {
                        ColorLed.YELLOW.rgb -> updateColor(ColorLed.YELLOW)
                        ColorLed.RED.rgb -> updateColor(ColorLed.RED)
                        ColorLed.GREEN.rgb -> updateColor(ColorLed.GREEN)
                        ColorLed.BLUE.rgb -> updateColor(ColorLed.BLUE)
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
                _uiSate.value = _uiSate.value.copy(error = it.message.toString())
            }
    }

    /* **********************************************************************
     * Public Function
     ***********************************************************************/
    fun updateColor(colorLed: ColorLed) {

        _uiSate.value = _uiSate.value.copy(Loading = true, colorLed = colorLed)
        database.getReference("control").child("led_rgb").setValue(colorLed.rgb)
            .addOnSuccessListener {
                _uiSate.value = _uiSate.value.copy(Success = true, Loading = false)
            }
            .addOnFailureListener {
                it.printStackTrace()
                _uiSate.value = _uiSate.value.copy(error = it.message.toString(), Loading = false)
            }
    }


}

data class UiState(
    val colorLed: ColorLed = ColorLed.YELLOW,
    val error: String = "",
    val Loading: Boolean = false,
    val Success: Boolean = false
)