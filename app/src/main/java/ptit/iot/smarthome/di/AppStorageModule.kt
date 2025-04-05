package ptit.iot.smarthome.di

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources.Theme
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ptit.iot.smarthome.data.database.AppPreferences
import ptit.iot.smarthome.data.repository.BrightnessRepository
import ptit.iot.smarthome.data.repository.BrightnessRepositoryImp
import ptit.iot.smarthome.data.repository.ThemeRepository
import ptit.iot.smarthome.data.repository.ThemeRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppStorageModule {

    private const val APP_SHARED_PREFS = "app_prefs"

    @Provides
    @Singleton
    fun provideAppSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideBrightnessRepository(sharedPreferences: AppPreferences): BrightnessRepository {
        return BrightnessRepositoryImp(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(sharedPreferences: AppPreferences): ThemeRepository {
        return ThemeRepositoryImp(sharedPreferences)
    }
}