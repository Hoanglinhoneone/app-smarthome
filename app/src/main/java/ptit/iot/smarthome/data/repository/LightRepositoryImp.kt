package ptit.iot.smarthome.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ptit.iot.smarthome.data.database.dao.LightDao
import ptit.iot.smarthome.data.entity.LightEntity
import ptit.iot.smarthome.utils.helper.convertToEndOfDay
import ptit.iot.smarthome.utils.helper.convertToStartOfDay
import ptit.iot.smarthome.utils.helper.getCurrentDate
import ptit.iot.smarthome.utils.helper.getTimeStamp
import javax.inject.Inject

class LightRepositoryImp @Inject constructor(private val lightDao: LightDao) : LightRepository {

    override fun getLightsStats(): Flow<List<LightEntity>> = lightDao.getLightsStats()

    override fun getDayLightsStats(): Flow<List<LightEntity>> {
        return lightDao.getLights()
    }

    override fun getLights(): Flow<List<LightEntity>> {
        return lightDao.getLights()
    }

    override suspend fun insertLight(light: LightEntity) {
        withContext(Dispatchers.IO) {
            lightDao.insertLight(light)
        }
    }

    override suspend fun deleteAllLights() = lightDao.deleteAllLight()

    override suspend fun getLightsInDay(): List<LightEntity> {
        val timestamp = getTimeStamp()
        val startOfDay = timestamp.convertToStartOfDay()
        val endOfDay = timestamp.convertToEndOfDay()
        return lightDao.getLightsInDay(startOfDay, endOfDay)
    }
}