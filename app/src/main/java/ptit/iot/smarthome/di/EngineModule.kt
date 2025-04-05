package ptit.iot.smarthome.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ptit.iot.smarthome.utils.helper.stt.SpeechToTextManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EngineModule {
    @Provides
    @Singleton
    fun provideSpeechToTextManager(@ApplicationContext context: Context): SpeechToTextManager {
        return SpeechToTextManager(context)
    }

//    @Provides
//    @Singleton
//    fun provideTextToSpeechManager(@ApplicationContext context: Context): TextToSpeechManager {
//        return TextToSpeechManager(context)
//    }
}