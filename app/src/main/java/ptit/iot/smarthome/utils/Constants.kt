package ptit.iot.smarthome.utils

import ptit.iot.smarthome.BuildConfig

object Constants {

    const val MQTT_CLOUD = "tcp://7a5a54e0da2f40469f0a9b880d172de8.s1.eu.hivemq.cloud:8883"
    const val MQTT_USERNAME = BuildConfig.MQTT_USERNAME
    const val MQTT_PASS = BuildConfig.MQTT_PASS
    const val TOPIC_LIGHT = "esp32/light"
    const val TOPIC_CONTROL = "esp32/led"
    const val CLIENT_ID = "AndroidClient"
    const val PORT = 8883


}

