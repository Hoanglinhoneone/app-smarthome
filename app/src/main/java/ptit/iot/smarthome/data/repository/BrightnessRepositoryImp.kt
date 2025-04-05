package ptit.iot.smarthome.data.repository

import ptit.iot.smarthome.data.database.AppPreferences
import javax.inject.Inject

class BrightnessRepositoryImp @Inject constructor(
    private val appPreferences: AppPreferences
) : BrightnessRepository {

    override fun getMinLux(): Float {
        return appPreferences.getMinLux()
    }

    override fun getMaxLux(): Float {
        return appPreferences.getMaxLux()
    }

    override fun setMinLux(value: Float) {
         appPreferences.setMinLux(value)
    }

    override fun setMaxLux(value: Float) {
        appPreferences.setMaxLux(value)
    }

}