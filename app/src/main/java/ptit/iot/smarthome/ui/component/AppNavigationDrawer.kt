package ptit.iot.smarthome.ui.component

//noinspection UsingMaterialAndMaterial3Libraries
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ptit.iot.smarthome.ui.MainViewModel
import ptit.iot.smarthome.ui.navigation.AppNavHost
import ptit.iot.smarthome.ui.navigation.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigationDrawer(
    viewModel: MainViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState, scope)
        }
    ) {
        AppNavHost(navController, scope, drawerState, viewModel)
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState, scope: CoroutineScope) {
    val items = listOf(Screen.Home, Screen.Stats, Screen.History, Screen.Setting)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            "Xin chào Linh!",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.outline
        )

        items.forEach { item ->
            Text(
                text = stringResource(item.title),
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(item.name)
                        scope.launch { drawerState.close() }
                    }
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun AppNavigationDrawerPreview() {
//    AppTheme {
//        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//        val scope = rememberCoroutineScope()
//        val navController = rememberNavController()
//
//        ModalNavigationDrawer(
//            drawerState = drawerState,
//            drawerContent = {
//                DrawerContent(navController, drawerState, scope)
//            }
//        ) {
//            AppNavHost(navController, scope, drawerState, MainViewModel())
//        }
//    }
//}


