package ptit.iot.smarthome.utils.helper.stt

interface SpeechEngineListener {

    fun onStartListening(isListening: Boolean)

    fun onBeginningOfSpeech()

    fun onEndOfSpeech()

    fun onError(error: String)

    fun onResults(results: String)

    fun onReadyForSpeech()
}