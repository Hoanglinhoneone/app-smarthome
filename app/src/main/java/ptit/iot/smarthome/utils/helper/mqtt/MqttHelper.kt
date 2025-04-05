package ptit.iot.smarthome.utils.helper.mqtt

import android.content.Context
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import ptit.iot.smarthome.utils.Constants
import javax.inject.Inject

class MqttHelper @Inject constructor(private val context: Context) {

    private var mqttClient: MqttAndroidClient? = null
    private val serverUri = Constants.MQTT_CLOUD
    private val clientId = Constants.CLIENT_ID
    private val username = Constants.MQTT_USERNAME
    private val password = Constants.MQTT_PASS
    private val port = Constants.PORT
    fun connect() {
        try {
            mqttClient = MqttAndroidClient(context, serverUri, clientId)
            Log.d("Linh_HN", "setup mqttClient $mqttClient")
            val options = MqttConnectOptions()
            options.userName = username
            options.password = password.toCharArray()
            options.isCleanSession = true
            options.apply {
                isAutomaticReconnect = true
                keepAliveInterval = 30
                connectionTimeout = 60
            }
            mqttClient?.connect(options, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("Linh_HN", "Connected successfully!")
                    subscribeToTopic("esp32/light")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.e("Linh_HN", "Connection failed: ${exception?.message}")
                    exception?.printStackTrace()
                    if (exception is MqttException) {
                        val mqttException = exception as MqttException
                        Log.e("Linh_HN", "MqttException: ${mqttException.message}")
                        mqttException.printStackTrace()
                    } else {
                        exception?.printStackTrace()
                    }
                }
            })

            mqttClient?.setCallback(object : MqttCallback {
                override fun messageArrived(topic: String, message: MqttMessage) {
                    Log.d("Linh_HN", "Message received: $message")
                    println("Message received: $message")
                    if (topic == Constants.TOPIC_LIGHT) {
                        Log.i("Linh_HN", "Message received: $message")
                    }
                }

                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    Log.d("Linh_HN", "Delivery complete")
                    println("Delivery complete")
                }

                override fun connectionLost(cause: Throwable) {
                    Log.d("Linh_HN", "Connection lost: ${cause.message}")
                    println("Connection lost: ${cause.message}")
                    cause.printStackTrace()
                }
            })
        } catch (e: Exception) {
            Log.e("Linh_HN", "Error connecting to MQTT: ${e.message}")
            e.printStackTrace()
        }
    }

    fun subscribeToTopic(topic: String) {
        try {
            Log.i("Linh_HN", "subscribeToTopic: $topic")
            mqttClient?.subscribe(topic, 1)
        } catch (e: Exception) {
            Log.e("Linh_HN", "Error subscribing to topic: ${e.message}")
            e.printStackTrace()
        }
    }

    fun publishMessage(topic: String, message: String) {
        try {
            val mqttMessage = MqttMessage(message.toByteArray())
            mqttClient?.publish(topic, mqttMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            Log.i("Linh_HN", "disconnect")
            mqttClient?.disconnect()
        } catch (e: Exception) {
            Log.e("Linh_HN", "Error disconnecting from MQTT: ${e.message}")
            e.printStackTrace()
        }
    }
}
