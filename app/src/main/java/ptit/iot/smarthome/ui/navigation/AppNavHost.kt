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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ptit.iot.smarthome.ui.MainViewModel
import ptit.iot.smarthome.ui.screen.auth.LoginSignupScreen
import ptit.iot.smarthome.ui.screen.history.HistoryScreen
import ptit.iot.smarthome.ui.screen.home.HomeScreenTest
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
            if(getCurrentRoute(navController) != Screen.Login.route) {
                TopAppBar(
                    title = { Text("Đèn thông minh") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController,
            startDestination = TypeGraph.Auth.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            navigation(
                route = TypeGraph.Home.route,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) { HomeScreenTest(viewModel) }
                composable(Screen.Stats.route) { StatsScreen() }
                composable(Screen.Setting.route) {
                    SettingsScreen(
                        onClickLogout = {
                            navController.navigate(Screen.Login.name)
                        },
                        checked = isDynamic,
                        onCheckedChange = { viewModel.setDynamicTheme(it) }
                    ) }
                composable(Screen.History.route) { HistoryScreen() }
//            composable(
//                Screen.Login.route,
//                arguments = listOf(navArgument("data") { type = NavType.StringType })
//            ) { backStackEntry ->
//                val data = backStackEntry.arguments?.getString("data")
//                LoginSignupScreen(data)
//            }
            }
            navigation(
                route = TypeGraph.Auth.route,
                startDestination = Screen.Login.route
            ) {
                composable(
                    route = Screen.Login.route,
//                    arguments = listOf(navArgument("data") { type = NavType.StringType })
                ) { backStackEntry ->
//                    val data = backStackEntry.arguments?.getString("data")
                    LoginSignupScreen(navController)
                }
            }
        }
    }
}
@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}
