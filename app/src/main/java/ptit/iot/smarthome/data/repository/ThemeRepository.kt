package ptit.iot.smarthome.data.repository

interface ThemeRepository {
    fun getDynamicTheme() : Boolean
    fun setDynamicTheme(isDynamic: Boolean)
}