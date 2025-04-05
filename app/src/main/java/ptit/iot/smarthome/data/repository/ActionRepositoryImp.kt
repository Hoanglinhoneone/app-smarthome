package ptit.iot.smarthome.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ptit.iot.smarthome.data.database.dao.ActionDao
import ptit.iot.smarthome.data.entity.ActionEntity
import javax.inject.Inject

class ActionRepositoryImp @Inject constructor(private val actionDao: ActionDao) : ActionRepository {

    override suspend fun getActions(): List<ActionEntity> {
        return actionDao.getHistory()

    }

    override suspend fun insertHistory(action: ActionEntity) {
        withContext(Dispatchers.IO) {
            actionDao.insertHistory(action)
        }
    }

    override suspend fun deleteAllAction() = actionDao.deleteAllHistory()
}