package ptit.iot.smarthome.utils.model

import ptit.iot.smarthome.R

enum class ColorLed(val title: Int, val rgb: String) {
    YELLOW(R.string.yellow, "0,255,255"),
    RED(R.string.red, "0,0,255"),
    BLUE(R.string.blue, "255,255,0"),
    GREEN(R.string.green, "0,255,0"),
}
