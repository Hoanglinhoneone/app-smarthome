package ptit.iot.smarthome.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ptit.iot.smarthome.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    // Ví dụ về một cây log tùy chỉnh cho chế độ release
    class ReleaseTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            // Xử lý log trong chế độ release, ví dụ gửi log đến server
        }
    }
}