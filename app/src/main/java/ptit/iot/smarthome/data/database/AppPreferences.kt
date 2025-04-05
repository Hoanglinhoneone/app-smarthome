package ptit.iot.smarthome.data.database

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        const val Min_LUX_KEY = "min_lux"
        const val Max_LUX_KEY = "max_lux"
        const val DYNAMIC_MODE_KEY = "dynamic_mode"
    }

    fun setDynamicMode(isDynamicTheme: Boolean) {
        sharedPreferences.edit().putBoolean(DYNAMIC_MODE_KEY, isDynamicTheme).apply()
    }

    fun getDynamicMode(): Boolean = sharedPreferences.getBoolean(DYNAMIC_MODE_KEY, false)

    fun setMinLux(value: Float) {
        sharedPreferences.edit().putFloat(Min_LUX_KEY, value).apply()
    }

    fun getMinLux(): Float = sharedPreferences.getFloat(Min_LUX_KEY, 50F)

    fun setMaxLux(value: Float) {
        sharedPreferences.edit().putFloat(Max_LUX_KEY, value).apply()
    }

    fun getMaxLux(): Float = sharedPreferences.getFloat(Max_LUX_KEY, 100F)

}