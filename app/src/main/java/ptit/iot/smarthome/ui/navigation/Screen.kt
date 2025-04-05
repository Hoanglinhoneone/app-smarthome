package ptit.iot.smarthome.ui.navigation

import androidx.annotation.StringRes
import ptit.iot.smarthome.R

enum class Screen(@StringRes val title: Int) {
    Home(R.string.home),
    Stats(R.string.stats),
    Setting(R.string.setting),
    History(R.string.history)
}