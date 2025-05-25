//package ptit.iot.smarthome.ui.screen.home
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import ptit.iot.smarthome.R
//import androidx.compose.ui.unit.sp
//
//// Nên thay thế bằng dữ liệu thực tế của bạn
//data class FamilyMember(val name: String, val avatarResId: Int)
//data class RoomDevice(
//    val name: String,
//    val imageResId: Int,
//    val deviceCount: Int,
//    val isOn: Boolean
//)
//
//@Composable
//fun HomeScreen() {
//    val familyMembers = listOf(
//        FamilyMember("User1", R.drawable.ic_launcher), // Thay bằng ảnh avatar thực tế
//        FamilyMember("User2", R.drawable.ic_launcher),
//        FamilyMember("User3", R.drawable.ic_launcher),
//    )
//
//    val roomDevices = listOf(
//        RoomDevice("Master Bedroom", R.drawable.ic_launcher, 4, true),
//        RoomDevice("Living Room", R.drawable.ic_launcher, 15, false),
//        RoomDevice("Dining Room", R.drawable.ic_launcher, 2, true),
//        RoomDevice("Bathroom", R.drawable.ic_launcher, 1, false),
//    )
//
//    var selectedTab by remember { mutableStateOf(0) }
//
//    Scaffold(
//        bottomBar = {
//            BottomNavigationBar()
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//                .padding(16.dp)
//        ) {
//            // Thanh tiêu đề và cài đặt
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Home",
//                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
//                )
//                IconButton(onClick = { /* TODO: Xử lý sự kiện click cài đặt */ }) {
//                    Icon(Icons.Filled.Settings, contentDescription = "Settings")
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Thành viên gia đình
//            Text(
//                text = "Family Members",
//                style = MaterialTheme.typography.titleMedium
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row {
//                familyMembers.forEach { member ->
//                    Image(
//                        painter = painterResource(id = member.avatarResId),
//                        contentDescription = member.name,
//                        modifier = Modifier
//                            .size(30.dp)
//                            .clip(CircleShape)
//                            .background(Color.Gray) // Placeholder
//                            .padding(end = 4.dp)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Thẻ vị trí và nhiệt độ
//            LocationWeatherCard()
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Tab chuyển đổi Room/Devices
//            TabRow(
//                selectedTabIndex = selectedTab,
//                containerColor = Color.Transparent,
//                contentColor = MaterialTheme.colorScheme.primary,
//                indicator = { tabPositions ->
//                    // Không hiển thị indicator mặc định
//                }
//            ) {
//                Tab(
//                    selected = selectedTab == 0,
//                    onClick = { selectedTab = 0 },
//                    modifier = Modifier
//                        .padding(horizontal = 4.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(if (selectedTab == 0) MaterialTheme.colorScheme.primaryContainer else Color.LightGray)
//                ) {
//                    Text(
//                        text = "Room",
//                        modifier = Modifier.padding(vertical = 12.dp),
//                        color = if (selectedTab == 0) MaterialTheme.colorScheme.onPrimaryContainer else Color.DarkGray
//                    )
//                }
//                Tab(
//                    selected = selectedTab == 1,
//                    onClick = { selectedTab = 1 },
//                    modifier = Modifier
//                        .padding(horizontal = 4.dp)
//                        .clip(RoundedCornerShape(8.dp))
//                        .background(if (selectedTab == 1) MaterialTheme.colorScheme.primaryContainer else Color.LightGray)
//                ) {
//                    Text(
//                        text = "Devices",
//                        modifier = Modifier.padding(vertical = 12.dp),
//                        color = if (selectedTab == 1) MaterialTheme.colorScheme.onPrimaryContainer else Color.DarkGray
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Lưới hiển thị phòng/thiết bị
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(roomDevices) { device ->
//                    RoomDeviceCard(roomDevice = device)
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun LocationWeatherCard() {
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier.fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.Transparent // Để có thể sử dụng gradient
//        )
//    ) {
//        Box(
//            modifier = Modifier
//                .background(
//                    brush = Brush.linearGradient(
//                        colors = listOf(Color(0xFF8A2BE2), Color(0xFFDA70D6)) // Ví dụ màu gradient
//                    )
//                )
//                .padding(16.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Top
//            ) {
//                Column {
//                    Text(
//                        text = "My Location",
//                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
//                    )
//                    Text(
//                        text = "Montreal",
//                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        // Icon(painterResource(id = R.drawable.ic_cloud), contentDescription = "Weather", tint = Color.White, modifier = Modifier.size(16.dp)) // Thay bằng icon thời tiết
//                        Spacer(modifier = Modifier.width(4.dp))
//                        Text(
//                            text = "Partly Cloudy",
//                            style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
//                        )
//                    }
//                }
//                Text(
//                    text = "-10°",
//                    style = MaterialTheme.typography.displayMedium.copy(color = Color.White, fontWeight = FontWeight.Bold)
//                )
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                horizontalArrangement = Arrangement.End,
//                verticalAlignment = Alignment.Bottom
//            ) {
//                Text(
//                    text = "H:2° L:12°", // Nhiệt độ cao/thấp
//                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun RoomDeviceCard(roomDevice: RoomDevice) {
//    var isChecked by remember { mutableStateOf(roomDevice.isOn) }
//
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column {
//            Image(
//                painter = painterResource(id = roomDevice.imageResId),
//                contentDescription = roomDevice.name,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
//                contentScale = ContentScale.Crop
//            )
//            Column(modifier = Modifier.padding(12.dp)) {
//                Text(
//                    text = roomDevice.name,
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
//                )
//                Text(
//                    text = "${roomDevice.deviceCount} devices",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = if (isChecked) "ON" else "OFF",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = if (isChecked) MaterialTheme.colorScheme.primary else Color.Gray
//                    )
//                    Switch(
//                        checked = isChecked,
//                        onCheckedChange = { isChecked = it },
//                        colors = SwitchDefaults.colors(
//                            checkedThumbColor = MaterialTheme.colorScheme.primary,
//                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
//                            uncheckedThumbColor = Color.LightGray,
//                            uncheckedTrackColor = Color.Gray.copy(alpha = 0.3f)
//                        )
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun BottomNavigationBar() {
//    // Hình dạng cong tùy chỉnh cho BottomAppBar
//    // Bạn có thể cần một thư viện hoặc vẽ tùy chỉnh phức tạp hơn cho hình dạng chính xác
//    // Đây là một ví dụ đơn giản hóa
//    BottomAppBar(
//        containerColor = Color.White, // Hoặc màu nền mong muốn
//        cutoutShape = CircleShape, // Ví dụ đơn giản, có thể cần tùy chỉnh thêm
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { /* TODO: Xử lý điều hướng Home */ }) {
//                Icon(Icons.Filled.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.primary)
//            }
//            // Nút ở giữa (Mic) có thể được tạo bằng FloatingActionButton đặt ở vị trí này
//            // hoặc một Composable tùy chỉnh
//            Box(
//                modifier = Modifier
//                    .size(56.dp) // Kích thước của nút ở giữa
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.primary) // Màu nền của nút Mic
//                    .clickable { /* TODO: Xử lý sự kiện click Mic */ },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    painter = painterResource(id = android.R.drawable.ic_btn_speak_now), // Thay bằng icon Mic thực tế
//                    contentDescription = "Microphone",
//                    tint = Color.White
//                )
//            }
//            IconButton(onClick = { /* TODO: Xử lý điều hướng Profile */ }) {
//                Icon(Icons.Filled.Person, contentDescription = "Profile", tint = Color.Gray)
//            }
//        }
//    }
//}
//
//
//@Preview(showBackground = true )
//@Composable
//fun HomeScreenPreview() {
//    MaterialTheme { // Đảm bảo bạn có một MaterialTheme được định nghĩa trong dự án của mình
//        HomeScreen()
//    }
//}
