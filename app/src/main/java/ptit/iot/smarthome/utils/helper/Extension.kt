package ptit.iot.smarthome.utils.helper

fun handleVoice(voice: String): String {
    val array: List<String> = voice.split(" ")
    return array.firstOrNull { it.lowercase() == "bật" || it.lowercase() == "tắt" } ?: "nothing"
}

fun Float.checkMinLux(maxLux: Float): Boolean = this < maxLux && this > 0

fun Float.checkMaxLux(minLux: Float): Boolean = this > minLux && this <= Float.MAX_VALUE