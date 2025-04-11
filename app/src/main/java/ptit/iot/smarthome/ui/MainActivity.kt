package ptit.iot.smarthome.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import ptit.iot.smarthome.ui.component.AppNavigationDrawer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = "LinhHN_MainActivity"
    private val viewModel: MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Linh_HN", "onCreate OK")
        super.onCreate(savedInstanceState)
        setContent {
            val isDynamic by viewModel.isDynamic.collectAsState()
            AppTheme(
                dynamicColor = isDynamic
            ) {
                AppNavigationDrawer(
                    viewModel = viewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}