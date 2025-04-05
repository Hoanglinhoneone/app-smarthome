package ptit.iot.smarthome.ui.screen.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ptit.iot.smarthome.ui.component.LineChart
import ptit.iot.smarthome.ui.component.QuadLineChart

@SuppressLint("NewApi")
@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    viewModel: StatsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val data: MutableList<Pair<Int, Double>> = mutableListOf(
        Pair(6, 111.45),
        Pair(7, 111.0),
        Pair(8, 113.45),
        Pair(9, 112.25),
        Pair(9, 112.25),
        Pair(9, 112.25),
        Pair(10, 116.45),
        Pair(11, 113.35),
        Pair(12, 118.65),
        Pair(12, 118.65),
        Pair(13, 110.15),
        Pair(14, 113.05),
        Pair(15, 114.25),
        Pair(15, 114.25),
        Pair(16, 116.35),
        Pair(16, 116.35),
        Pair(17, 117.45),
        Pair(17, 117.45),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(18, 112.65),
        Pair(19, 115.45),
        Pair(20, 111.85)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LineChart(
            data = uiState.dataLightsDay,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Divider()
        Spacer(modifier = Modifier.height(40.dp))
        QuadLineChart(
            data = uiState.data,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(CenterHorizontally)
        )
    }
}