package ptit.iot.smarthome.ui.screen.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ptit.iot.smarthome.R

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    viewModel: SettingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var minLux by remember { mutableStateOf(uiState.minLux.toString()) }
    var maxLux by remember { mutableStateOf(uiState.maxLux.toString()) }
    var enableEdit by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val styleTitleText = MaterialTheme.typography.titleMedium
    val backgroundIcon = MaterialTheme.colorScheme.primary
    val sizeIcon = 42.dp
    val tintIcon = Color.White
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
            .scrollable(
                rememberScrollState(),
                orientation = Orientation.Vertical,
                enabled = true,
                reverseDirection = true,
                flingBehavior = null,
                interactionSource = null
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { expanded = !expanded }
            ) {
                Box(
                    modifier = Modifier
                        .size(sizeIcon)
                        .background(
                            color = backgroundIcon,
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_change_auto),
                        contentDescription = null,
                        tint = tintIcon,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Text(
                    text = stringResource(R.string.auto_mode),
                    style = styleTitleText,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                )
                Icon(
                    painter = painterResource(if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            if (expanded) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    TextField(
                        value = minLux,
                        onValueChange = { minLux = it },
                        modifier = Modifier.weight(1f),
                        enabled = enableEdit,
                        label = {
                            Text(
                                text = "Giới hạn tắt",
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_light_off),
                                contentDescription = "light off",
                                tint = Color(0xFF788F98)
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color(0xFF788F98),
                            focusedIndicatorColor = Color(0xFF788F98),
                        )
                    )
                    TextField(
                        value = maxLux,
                        onValueChange = { maxLux = it },
                        modifier = Modifier.weight(1f),
                        enabled = enableEdit,
                        label = {
                            Text(
                                text = "Giới hạn bật",
                                style = MaterialTheme.typography.titleSmall
                            )
                        },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_light_on),
                                contentDescription = "light on",
                                tint = Color(0xFFFEA908)
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color(0xFF788F98),
                            focusedIndicatorColor = Color(0xFF788F98),
                        )
                    )
                }
                Button(
                    onClick = {
                        if (enableEdit) {
                            viewModel.setMinLux(minLux.toFloat())
                            viewModel.setMaxLux(maxLux.toFloat())
                        }
                        enableEdit = !enableEdit
                    },

                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        text = if (enableEdit) "Lưu" else "Sửa",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(sizeIcon)
                    .background(
                        color = backgroundIcon,
                        shape = CircleShape
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_dynamic_color),
                    contentDescription = null,
                )
            }
            Text(
                text = stringResource(R.string.dynamic),
                style = styleTitleText,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            )

            Switch(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(!checked)
                },
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(sizeIcon)
                    .background(
                        color = backgroundIcon,
                        shape = CircleShape
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = null,
                    tint = tintIcon,
                )
            }
            Text(
                text = "Xóa dữ liệu",
                style = styleTitleText,
                modifier = Modifier.padding(start = 16.dp).weight(1f)
            )
            Button(
                onClick = {
                    viewModel.deleteLightData()
                    viewModel.deleteActionData()
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Xóa"
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(sizeIcon)
                    .background(
                        color = backgroundIcon,
                        shape = CircleShape
                    )
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    contentDescription = null,
                    tint = tintIcon,
                )
            }
            Text(
                text = "Đăng xuất",
                style = styleTitleText,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview(modifier: Modifier = Modifier) {
    SettingsScreen(
        checked = true,
        onCheckedChange = {}
    )
}