package ptit.iot.smarthome.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ptit.iot.smarthome.ui.MainViewModel
import ptit.iot.smarthome.ui.screen.history.HistoryScreen
import ptit.iot.smarthome.ui.screen.home.HomeScreen
import ptit.iot.smarthome.ui.screen.setting.SettingsScreen
import ptit.iot.smarthome.ui.screen.stats.StatsScreen


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewModel: MainViewModel
) {
    val isDynamic by viewModel.isDynamic.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nhà thông minh") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = Screen.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.name) { HomeScreen() }
            composable(Screen.Stats.name) { StatsScreen() }
            composable(Screen.Setting.name) {
                SettingsScreen(
                    checked = isDynamic,
                    onCheckedChange = { viewModel.setDynamicTheme(it) }
                ) }
            composable(Screen.History.name) { HistoryScreen() }
        }
    }
}
