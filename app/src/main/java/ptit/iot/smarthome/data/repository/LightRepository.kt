package ptit.iot.smarthome.data.repository

import kotlinx.coroutines.flow.Flow
import ptit.iot.smarthome.data.entity.LightEntity

interface LightRepository {
    fun getLightsStats(): Flow<List<LightEntity>>
    fun getDayLightsStats(): Flow<List<LightEntity>>
    fun getLights(): Flow<List<LightEntity>>
    suspend fun insertLight(light: LightEntity)
    suspend fun deleteAllLights()
}