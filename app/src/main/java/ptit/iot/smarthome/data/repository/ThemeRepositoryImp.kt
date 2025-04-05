package ptit.iot.smarthome.data.repository

import ptit.iot.smarthome.data.database.AppPreferences
import javax.inject.Inject

class ThemeRepositoryImp @Inject constructor(
    private val appPreferences: AppPreferences
) : ThemeRepository {

    override fun getDynamicTheme(): Boolean = appPreferences.getDynamicMode()

    override fun setDynamicTheme(isDynamic: Boolean) = appPreferences.setDynamicMode(isDynamic)

}