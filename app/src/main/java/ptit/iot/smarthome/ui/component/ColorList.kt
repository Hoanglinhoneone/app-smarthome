package ptit.iot.smarthome.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import ptit.iot.smarthome.utils.DataSource
import ptit.iot.smarthome.utils.model.ColorLed

@Composable
fun ColorList(
    isSelectedLed: ColorLed,
    onCLickItem: (ColorLed) -> Unit = {},
    listColor: List<ColorLed> = DataSource.colorList
) {
    LazyRow(
        modifier = Modifier.padding(5.dp)
    ) {
        items(listColor) { item ->
            if (isSelectedLed.name == item.name) {
                ColorItem(
                    onClickItem = { onCLickItem(item) },
                    isSelected = true,
                    color = item
                )
            } else {
                ColorItem(
                    onClickItem = { onCLickItem(item) },
                    color = item
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    onClickItem: () -> Unit = {},
    isSelected: Boolean = false,
    color: ColorLed,
    modifier: Modifier = Modifier
) {
    val isDarkMode = isSystemInDarkTheme()
    val colorText = if (isDarkMode) Color.White else Color.Black
    val colorValue = when (color) {
        ColorLed.YELLOW -> Color.Yellow
        ColorLed.RED -> androidx.compose.ui.graphics.Color.Red
        ColorLed.BLUE -> androidx.compose.ui.graphics.Color.Blue
        ColorLed.GREEN -> androidx.compose.ui.graphics.Color.Green
    }
    Column(
        modifier = modifier
            .padding(5.dp)
    ) {
        Box(
            modifier = modifier
                .clip(CircleShape)
                .size(30.dp)
                .background(color = colorValue)
                .shadow(10.dp, CircleShape)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClickItem()
                }
                .then(
                    if (isSelected) {
                        Modifier.border(
                            width = 2.dp,
                            color = if (isDarkMode) Color.White else Color.Black,
                            shape = CircleShape
                        )
                    } else {
                        Modifier
                    }
                )
        )
        Text(
            text = stringResource(color.title),
            color = if (isSelected) colorText else MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorListPreview() {
    AppTheme {
        ColorList(
            isSelectedLed = ColorLed.GREEN
        )
    }
}

