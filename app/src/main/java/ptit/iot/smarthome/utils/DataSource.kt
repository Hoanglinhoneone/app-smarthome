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
    CHANGE_COLOR_YELLOW(R.string.change_color_yellow_action),
    CHANGE_COLOR_RED(R.string.change_color_red_action),
    CHANGE_COLOR_GREEN(R.string.change_color_green_action),
    CHANGE_COLOR_BLUE(R.string.change_color_blue_action),
    AUTO_ON(R.string.auto_on_action),
    AUTO_OFF(R.string.auto_off_action),
    UNKNOWN(R.string.unknown_action)
}

enum class ActionState(val title: Int) {
    SUCCESS(R.string.success),
    FAIL(R.string.fail)
}