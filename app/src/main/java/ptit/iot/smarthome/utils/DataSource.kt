package ptit.iot.smarthome.utils

import ptit.iot.smarthome.R
import ptit.iot.smarthome.utils.model.ColorLed

object DataSource {
    val colorList: List<ColorLed> = listOf(
        ColorLed.YELLOW,
        ColorLed.RED,
        ColorLed.BLUE,
        ColorLed.GREEN,
    )
}

enum class Action(val title: Int) {
    LIGHT_ON(R.string.light_on_action),
    LIGHT_OFF(R.string.light_off_action),
    AUTO_ON(R.string.auto_on_action),
    AUTO_OFF(R.string.auto_off_action)
}

enum class ActionState(val title: Int) {
    SUCCESS(R.string.success),
    FAIL(R.string.fail)
}