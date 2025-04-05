package ptit.iot.smarthome.data.repository

interface BrightnessRepository {
    fun getMinLux(): Float
    fun getMaxLux(): Float
    fun setMinLux(value: Float)
    fun setMaxLux(value: Float)
}