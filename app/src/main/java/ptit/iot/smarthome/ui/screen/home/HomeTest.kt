package ptit.iot.smarthome.ui.screen.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ptit.iot.smarthome.R
import ptit.iot.smarthome.ui.MainViewModel
import ptit.iot.smarthome.utils.model.ColorLed
import timber.log.Timber

@Composable
fun HomeScreenTest(
     viewModel: MainViewModel = hiltViewModel()
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiStateHome by homeViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val isBedRoomOn = if(uiStateHome.colorLed == ColorLed.YELLOW && uiState.isLightOn) true else false
    val isLivingRoomOn = if(uiStateHome.colorLed == ColorLed.RED && uiState.isLightOn) true else false
    val isDiningRoomOn = if(uiStateHome.colorLed == ColorLed.GREEN && uiState.isLightOn) true else false
    val isLaundryOn = if(uiStateHome.colorLed == ColorLed.BLUE && uiState.isLightOn) true else false

    LaunchedEffect (uiState.colorChange){
        homeViewModel.updateColor(uiState.colorChange)
    }
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
                    Timber.i("Permission granted")
                    Log.i("HomeTest", "startListening")
                    viewModel.startListening()
                }
            })

    Box(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(Color(0xFFF6F7FB))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            // Top Bar
//            TopBar()

            Spacer(modifier = Modifier.height(16.dp))

            // Weather Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFBC6FF1),
                                Color(0xFFDA85CF)
                            )
                        )
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "My Location",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Montreal",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_info_details),
                                contentDescription = "Weather",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Partly Cloudy",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${uiState.light.toInt()} Lux",
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "H:2° L:-12°",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tabs
            RoomDevicesTabs()

            // Room cards grid
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RoomCard(
                        roomName = "Master Bedroom",
                        deviceCount = 4,
                        isOn = isBedRoomOn,
                        imageRes = R.drawable.img_phong_ngu,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            homeViewModel.updateColor(ColorLed.YELLOW)
                            if(!uiState.isAutoMode)  {
                                if(!uiState.isLightOn) viewModel.updateLight(true)
                                else viewModel.updateLight(false)
                            }
                        }
                    )

                    RoomCard(
                        roomName = "Living Room",
                        deviceCount = 15,
                        isOn = isLivingRoomOn,
                        imageRes = R.drawable.img_phong_khach,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            homeViewModel.updateColor(ColorLed.RED)
                            if(!uiState.isAutoMode)  {
                                if(!uiState.isLightOn) viewModel.updateLight(true)
                                else viewModel.updateLight(false)
                            }
                        }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    RoomCard(
                        roomName = "Dining Room",
                        deviceCount = 8,
                        isOn = isDiningRoomOn,
                        imageRes = R.drawable.img_phong_bep,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            homeViewModel.updateColor(ColorLed.GREEN)
                            if(!uiState.isAutoMode)  {
                                if(!uiState.isLightOn) viewModel.updateLight(true)
                                else viewModel.updateLight(false)
                            }
                        }
                    )

                    RoomCard(
                        roomName = "Laundry",
                        deviceCount = 3,
                        isOn = isLaundryOn,
                        imageRes = R.drawable.img_ngoai_san,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            homeViewModel.updateColor(ColorLed.BLUE)
                            if(!uiState.isAutoMode)  {
                                if(!uiState.isLightOn) viewModel.updateLight(true)
                                else viewModel.updateLight(false)
                            }
                        }
                    )
                }
            }
        }
        Box(
            modifier = Modifier.align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp)
                .background(
                    color = Color(0xFF464C63),
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
//                        .padding(top = 8.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFFF89B4),
                                    Color(0xFFFF5A87)
                                )
                            )
                        )
                        .clickable {
                            if (!uiState.isAutoMode && !uiState.isListening) {
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
                            } else if (uiState.isListening) viewModel.stopListening()
                        }
                    ,
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_btn_speak_now),
                        contentDescription = "Microphone",
                        tint = if(uiState.isListening) Color.Red else Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column(
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(end = 16.dp, bottom = 90.dp)
//                            .clip(RoundedCornerShape(16.dp))
//                            .background(color = Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 32.dp).align(Alignment.CenterEnd).padding(end = 16.dp)

            ) {
                Switch(
                    checked = if(uiState.isAutoMode) true else false,
                    onCheckedChange = {
                        viewModel.updateAutoMode(!uiState.isAutoMode)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = if (true) Color(0xFFFF5A87) else Color.Gray,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.LightGray
                    ),
                )
                Text(
                    text = "Auto Mode",
                    color = Color.White.copy(alpha = if(uiState.isAutoMode) 1f else 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenTestPreview() {
    HomeScreenTest()
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Home",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Family Members",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Family member avatars
            Row {
                for (i in 0..3) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = if (i < 3) 4.dp else 0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Settings icon
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEEEEF0))
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun WeatherCard() {

}

@Composable
fun RoomDevicesTabs() {
    var selectedTab by remember { mutableStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEBECF0)),
        containerColor = Color.Transparent,
        contentColor = Color.Black,
        indicator = { },
        divider = { }
    ) {
        Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (selectedTab == 0) Color.White else Color.Transparent)
        ) {
            Text(
                text = "Room",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = if (selectedTab == 0) Color.Black else Color.Gray
            )
        }

        Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (selectedTab == 1) Color.White else Color.Transparent)
        ) {
            Text(
                text = "Devices",
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = if (selectedTab == 1) Color.Black else Color.Gray
            )
        }
    }
}

@Composable
fun RoomCardsGrid() {

}

@Composable
fun RoomCard(
    roomName: String,
    deviceCount: Int,
    isOn: Boolean,
    imageRes: Int,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Room image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = roomName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Add button
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.7f))
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_add),
                        contentDescription = "Add",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Room info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = roomName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black

                )

                Text(
                    text = "$deviceCount devices",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isOn) "ON" else "OFF",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    Switch(
                        checked = isOn,
                        onCheckedChange = { onClick() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = if (isOn) Color(0xFFFF5A87) else Color.Gray,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.LightGray
                        ),
                        modifier = Modifier.scale(1f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RoomCardPreview() {
    RoomCard(
        roomName = "Living Room",
        deviceCount = 5,
        isOn = true,
        imageRes = R.drawable.img_phong_khach,
        onClick = {},
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
    )
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {

}

// Helper extension function for Switch scaling
fun Modifier.scale(scale: Float) = this.then(
    Modifier.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val width = (placeable.width * scale).toInt()
        val height = (placeable.height * scale).toInt()

        layout(width, height) {
            placeable.placeRelative(0, 0)
        }
    }
)