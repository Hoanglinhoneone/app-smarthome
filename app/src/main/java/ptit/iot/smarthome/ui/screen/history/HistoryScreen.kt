package ptit.iot.smarthome.ui.screen.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var lightPageSelected by remember { mutableStateOf(true) }
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
             horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 32.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))

        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        lightPageSelected = true
                    }
                    .background(
                        if(lightPageSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        , shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chỉ số",
                    textAlign = TextAlign.Center,
                    color =  MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        lightPageSelected = false
                    }
                    .background(
                        if(!lightPageSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                        , shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "hành động",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        when(lightPageSelected) {
            true -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.lights.reversed()) { light ->
                        LightItem(light = light)
                    }
                }
            }
            false -> {
                LazyColumn(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.actions) { action ->
                        ActionItem(action = action)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HistoryPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        HistoryScreen()
    }
}