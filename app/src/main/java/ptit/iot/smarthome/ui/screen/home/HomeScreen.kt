package ptit.iot.smarthome.ui.screen.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ptit.iot.smarthome.R
import ptit.iot.smarthome.ui.MainViewModel
import ptit.iot.smarthome.ui.component.ColorList
import timber.log.Timber


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MainViewModel = hiltViewModel()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiStateHome by homeViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.light) {
        if (uiState.isAutoMode) {
            viewModel.autoMode()
        }
    }
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope: CoroutineScope = rememberCoroutineScope()
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                Timber.i("Permission granted = $isGranted")
                if (isGranted) {
                    viewModel.startListening()
                }
            })
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
        ) {
            Column(
                modifier = Modifier.align(Alignment.End)
            ) {
                Switch(
                    checked = if (uiState.isAutoMode) true else false,
                    onCheckedChange = { viewModel.updateAutoMode(!uiState.isAutoMode) },
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                        uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )
                Text(
                    text = "Auto Mode",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 10.dp)
            ) {
                Image(
                    painter = painterResource(if (uiState.isLightOn) R.drawable.ic_light_yellow else R.drawable.ic_light_gray),
                    contentDescription = null,
                )
                Text(
                    text = "Độ sáng: ${uiState.light}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 20.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(if (uiState.isListening) R.drawable.ic_mic_on else R.drawable.ic_mic_off),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable {
                                if (!uiState.isAutoMode) {
                                    Timber.i("Permission handle")
                                    when {
                                        ContextCompat.checkSelfPermission(
                                            context, Manifest.permission.RECORD_AUDIO
                                        ) == PackageManager.PERMISSION_GRANTED -> {
                                            Timber.i("Permission already granted")
                                            viewModel.startListening()
                                        }

                                        ActivityCompat.shouldShowRequestPermissionRationale(
                                            context as Activity, Manifest.permission.RECORD_AUDIO
                                        ) -> {
                                            Timber.i("Should show a permission rationale")
                                            scope.launch {
                                                snackBarHostState.showSnackbar("Vui lòng mở cài đặt và cấp quyền micro")
                                            }
                                        }

                                        else -> {
                                            Timber.i("Request record audio permission")
                                            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                                        }
                                    }
                                }
                            }
                            .padding(bottom = 10.dp)
                    )
                    Text(
                        text = if (uiState.isListening) stringResource(R.string.on) else stringResource(
                            R.string.off
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
                Column(
                    modifier = Modifier
//                    .align(Alignment.CenterVertically),
                ) {
                    Switch(
                        checked = if (uiState.isLightOn) true else false,
                        onCheckedChange = { viewModel.updateLight(!uiState.isLightOn) },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        enabled = !uiState.isAutoMode,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.onSurface,
                            uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
                            uncheckedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                    Text(
                        text = if (uiState.isLightOn) stringResource(R.string.on) else stringResource(
                            R.string.off
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(16.dp)
                        ),
//                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ColorList(
                        onCLickItem = {
                            homeViewModel.updateColor(it)
                        },
                        isSelectedLed = uiStateHome.colorLed
                    )
                    Text(
                        text = "Chọn màu",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    AppTheme {
        HomeScreen()
    }
}