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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ptit.iot.smarthome.R
import ptit.iot.smarthome.utils.Action
import ptit.iot.smarthome.data.entity.ActionEntity
import ptit.iot.smarthome.utils.ActionState
import ptit.iot.smarthome.utils.helper.convertToDateTime

@Composable
fun ActionItem(
    action: ActionEntity,
    modifier: Modifier = Modifier
) {
    val iconState = when(action.state) {
        ActionState.SUCCESS.name -> R.drawable.ic_state_success
        else -> R.drawable.ic_state_fail
    }
    val textTitle = when(action.action) {
        Action.AUTO_ON.name -> R.string.auto_on_action
        Action.AUTO_OFF.name -> R.string.auto_off_action
        Action.LIGHT_ON.name -> R.string.light_on_action
        Action.LIGHT_OFF.name -> R.string.light_off_action
        Action.CHANGE_COLOR_BLUE.name -> R.string.change_color_blue_action
        Action.CHANGE_COLOR_RED.name -> R.string.change_color_red_action
        Action.CHANGE_COLOR_GREEN.name -> R.string.change_color_green_action
        Action.CHANGE_COLOR_YELLOW.name -> R.string.change_color_yellow_action
        else -> { R.string.unknown_action }
    }
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
                painter = painterResource(id = R.drawable.item_auto),
                contentDescription = stringResource(R.string.light_sensor),
                modifier = Modifier.size(40.dp)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(textTitle),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.sp
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_action),
                        contentDescription = stringResource(R.string.lux),
                    )
                    Text(
                        "Bật đèn",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp
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
                        text = action.timestamp.convertToDateTime(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp
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
                        painter = painterResource(iconState) ,
                        contentDescription = stringResource(R.string.location),
                    )
                    Text(
                        text = action.state,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionItemPreview(modifier: Modifier = Modifier) {
    MaterialTheme {
        ActionItem(
            action = ActionEntity(
                timestamp = 0,
                action = Action.AUTO_ON.name,
                state = "Success"
            )
        )
    }
}