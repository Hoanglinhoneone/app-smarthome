package ptit.iot.smarthome.ui.component

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun LineChart(
    data: List<Pair<Int, Double>> = emptyList(),
    modifier: Modifier = Modifier
) {
    val isDarkTheme = isSystemInDarkTheme()
    val spacing = 100f
    val graphColor = Color.Cyan
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    var upperValue by remember {
        mutableIntStateOf((data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0)
    }
    upperValue = data.maxOfOrNull { it.second }?.plus(1)?.roundToInt() ?: 0
    var lowerValue by remember { mutableStateOf((data.minOfOrNull { it.second }?.toInt() ?: 0)) }
    lowerValue = data.minOfOrNull { it.second }?.toInt() ?: 0
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = if (isDarkTheme) android.graphics.Color.WHITE else android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices step 2).forEach { i ->
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing + i * spacePerHour,
                    size.height,
                    textPaint
                )
            }
        }

        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    30f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (ratio * height).toFloat()

                if (i == 0) {
                    moveTo(x1, y1)
                }
                lineTo(x1, y1)
            }
        }

        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LineChartPreview() {
    val data = listOf(
        Pair(6, 111.45),
        Pair(7, 111.0),
        Pair(8, 113.45),
        Pair(9, 112.25),
        Pair(10, 116.45),
        Pair(11, 113.35),
        Pair(12, 118.65),
        Pair(13, 110.15),
        Pair(14, 113.05),
        Pair(15, 114.25),
        Pair(16, 116.35),
        Pair(17, 117.45),
        Pair(18, 112.65),
        Pair(19, 115.45),
        Pair(20, 111.85)
    )
    AppTheme {
        LineChart(
            data = data,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}