package ptit.iot.smarthome.utils.helper.stt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import timber.log.Timber
import javax.inject.Inject

open class SpeechToTextManager @Inject constructor(private val context: Context) : SpeechService {
    /* **********************************************************************
     * Variable
     ***********************************************************************/
    private val TAG = "LINH_SPEECH"
    private var recognizer: SpeechRecognizer? = null
    private var listener: SpeechEngineListener? = null
    private var isListening = false
    private var language: String = "vi-VN"

    /* **********************************************************************
     * Variable
     ***********************************************************************/
    override fun initSpeechRecognizer(): Boolean {
        return if (SpeechRecognizer.isRecognitionAvailable(context)) {
            Log.i(TAG, "Speech recognizer is supported")
            if (recognizer != null) {
                Log.i(TAG, "Reset speech recognizer service")
                recognizer?.stopListening()
                recognizer?.destroy()
                recognizer = null
            }
            recognizer = SpeechRecognizer.createSpeechRecognizer(context)
            recognizer?.setRecognitionListener(this)
            true
        } else {
            Log.i(TAG, "Speech recognizer is not supported")
            false
        }
    }

    override fun onStartListening(listener: SpeechEngineListener) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            listener.onError("Recognition is not available")
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)

        }
        recognizer?.setRecognitionListener(this)
        recognizer?.startListening(intent)
        isListening = true
        this.listener = listener
        listener.onStartListening(true)

        Timber.i("Đang lắng nghe bạn nói!")
    }

    override fun onStopListening() {
        if (!isListening) {
            return
        }
        recognizer?.stopListening()
        isListening = false
    }

    override fun setLanguage(language: String) {
        this.language = language
    }

    override fun shutdown() {
        recognizer?.destroy()
    }

    override fun onReadyForSpeech(params: Bundle?) {
        listener?.onReadyForSpeech()
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(rmsdB: Float) = Unit

    override fun onBufferReceived(buffer: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        isListening = false
        listener?.onStartListening(false)
    }

    override fun onError(error: Int) {
        val message = error.getSpeechToTextErrorMessage()
        Log.e(TAG, message)
        listener?.onError(message)
    }

    override fun onResults(results: Bundle?) {
        results
            ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let {
                listener?.onResults(it)
            }
        listener?.onStartListening(false)
        initSpeechRecognizer()
    }

    override fun onPartialResults(partialResults: Bundle?) = Unit

    override fun onEvent(eventType: Int, params: Bundle?) = Unit

}