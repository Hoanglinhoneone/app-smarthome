package ptit.iot.smarthome.utils.helper.stt

import android.speech.RecognitionListener

interface SpeechService : RecognitionListener {

    fun initSpeechRecognizer() : Boolean

    fun onStartListening(listener: SpeechEngineListener)

    fun onStopListening()

    fun setLanguage(language: String)

    fun shutdown()
}