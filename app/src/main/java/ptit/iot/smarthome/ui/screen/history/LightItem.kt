package ptit.iot.smarthome.ui.screen.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ptit.iot.smarthome.R
import ptit.iot.smarthome.data.entity.LightEntity
import ptit.iot.smarthome.utils.helper.convertToDateTime

@Composable
fun LightItem(
    light: LightEntity,
    modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.item_light),
                contentDescription = stringResource(R.string.light_sensor)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = light.type,
                    style = MaterialTheme.typography.titleLarge,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_lux),
                        contentDescription = stringResource(R.string.lux),
                    )
                    Text(
                        "${light.light.toInt()} Lux",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier.size(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = stringResource(R.string.time),
                        )
                    }
                    Text(
                        text = light.timestamp.convertToDateTime(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                }
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = stringResource(R.string.location),
                    )
                    Text(
                        text = "Nhà"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LightItemPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        LightItem(
            light = LightEntity(
                timestamp = 0,
                light = 1000f
            )
        )
    }
}