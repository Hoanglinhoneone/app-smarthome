package ptit.iot.smarthome.ui.navigation

import androidx.annotation.StringRes
import ptit.iot.smarthome.R

//  Phân tree cho màn hình
enum class TypeGraph(val route: String) {
    Auth("auth"),
    Home("home"),

    // navGraph for detail course
    Course("Course"),
}

enum class Screen(@StringRes val title: Int, val route: String) {
    Home(R.string.home, "Home"),
    Stats(R.string.stats, "Stats"),
    Setting(R.string.setting, "Setting"),
    History(R.string.history, "History"),
    Login(R.string.login, "Login"),
}